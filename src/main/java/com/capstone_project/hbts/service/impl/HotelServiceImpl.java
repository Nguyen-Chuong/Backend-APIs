package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Hotel.HotelDTO;
import com.capstone_project.hbts.dto.Hotel.HotelDetailDTO;
import com.capstone_project.hbts.dto.RatingDTO;
import com.capstone_project.hbts.dto.Report.ReviewDTO;
import com.capstone_project.hbts.entity.District;
import com.capstone_project.hbts.entity.Hotel;
import com.capstone_project.hbts.entity.Provider;
import com.capstone_project.hbts.entity.Review;
import com.capstone_project.hbts.entity.RoomType;
import com.capstone_project.hbts.entity.UserBooking;
import com.capstone_project.hbts.repository.BookingRepository;
import com.capstone_project.hbts.repository.HotelRepository;
import com.capstone_project.hbts.request.HotelRequest;
import com.capstone_project.hbts.response.CustomPageImpl;
import com.capstone_project.hbts.service.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final ModelMapper modelMapper;

    private final BookingRepository bookingRepository;

    public HotelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper, BookingRepository bookingRepository) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
        this.bookingRepository = bookingRepository;
    }

    // get total room available in a hotel
    public int getTotalRoom(Set<RoomType> roomTypes) {
        int totalRoom = 0;
        for (RoomType roomType : roomTypes) {
            totalRoom = totalRoom + roomType.getQuantity();
        }
        return totalRoom;
    }

    // get total people available in a hotel
    public int getTotalPeople(Set<RoomType> roomTypes) {
        int totalPeople = 0;
        for (RoomType roomType : roomTypes) {
            totalPeople = totalPeople + roomType.getNumberOfPeople() * roomType.getQuantity();
        }
        return totalPeople;
    }

    // get lowest price (included sale) in a hotel
    // check if hotel have no room => return a new empty room :)
    public RoomType getLowestPriceInHotel(Set<RoomType> roomTypes) {
        List<Long> listPrice = new ArrayList<>();
        // add room prices to list to compare
        for (RoomType roomType : roomTypes) {
            listPrice.add(roomType.getPrice() - roomType.getPrice() * roomType.getDealPercentage() / 100);
        }
        // loop list room and return the room that have lowest price
        for (RoomType roomType : roomTypes) {
            if (roomType.getPrice() - roomType.getPrice() * roomType.getDealPercentage() / 100 == Collections.min(listPrice)) {
                return roomType;
            }
        }
        return new RoomType();
    }

    // get rating by hotel
    public RatingDTO getRatingByHotel(Hotel hotel) {
        // if hotel has no booking
        if(hotel.getListUserBooking() == null){
            return new RatingDTO();
        }
        // new rating
        RatingDTO ratingDTO = new RatingDTO();
        // get list user booking reviewed
        Set<UserBooking> userBookingListHasReview = hotel.getListUserBooking().stream()
                .filter(item -> item.getReviewStatus() == 1).collect(Collectors.toSet());
        // get total number booking rated
        int number = userBookingListHasReview.size();
        // if this hotel has no booking that reviewed
        if (number == 0) {
            return new RatingDTO();
        } else {
            // get total score
            float totalService = 0, totalValueForMoney = 0, totalCleanliness = 0, totalLocation = 0, totalFacilities = 0;
            for (UserBooking userBooking : userBookingListHasReview) {
                Review review = userBooking.getListReview().iterator().next();
                totalService = totalService + review.getService();
                totalValueForMoney = totalValueForMoney + review.getValueForMoney();
                totalCleanliness = totalCleanliness + review.getCleanliness();
                totalLocation = totalLocation + review.getLocation();
                totalFacilities = totalFacilities + review.getFacilities();
            }
            // get average score
            float averageService = totalService / number;float averageValueForMoney = totalValueForMoney / number;
            float averageCleanliness = totalCleanliness / number;float averageLocation = totalLocation / number;
            float averageFacilities = totalFacilities / number;
            // set props
            ratingDTO.setTotalReview(number);
            ratingDTO.setAverageService(averageService);
            ratingDTO.setAverageValueForMoney(averageValueForMoney);
            ratingDTO.setAverageCleanliness(averageCleanliness);
            ratingDTO.setAverageLocation(averageLocation);
            ratingDTO.setAverageFacilities(averageFacilities);
            return ratingDTO;
        }
    }

    // count average score review
    public float countTotalScoreReview(Review review){
        float total = 0;
        return total + review.getService() + review.getFacilities() + review.getCleanliness() + review.getLocation()
                + review.getValueForMoney();
    }

    // get top 1 review
    public ReviewDTO getTop1ReviewAboutHotel(Set<UserBooking> userBookings){
        // get set booking has review
        Set<UserBooking> userBookingHasReview = userBookings.stream().filter(item -> item.getReviewStatus() == 1)
                .collect(Collectors.toSet());
        // if has no booking has review, return empty
        if(userBookingHasReview.size() == 0 ){
            return new ReviewDTO();
        }
        // total review
        List<Review> listReview = new ArrayList<>();
        // total score
        List<Float> totalReviewScoreOfBookings = new ArrayList<>();
        for(UserBooking userBooking : userBookingHasReview){
            Review review = userBooking.getListReview().iterator().next();
            listReview.add(review);
            totalReviewScoreOfBookings.add(countTotalScoreReview(review));
        }
        // get max total score
        float maxValue = totalReviewScoreOfBookings.stream().max(Float::compare).get();
        // return review that has max score
        for(Review review : listReview){
            if(countTotalScoreReview(review) == maxValue){
                ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);
                // set username
                reviewDTO.setUsername(review.getUserBooking().getUsers().getUsername().substring(2));
                // set avatar
                reviewDTO.setAvatar(review.getUserBooking().getUsers().getAvatar());
                return reviewDTO;
            }
        }
        return new ReviewDTO();
    }

    @Override
    public int getSizeNoPaging(int districtId, int numberOfPeople, int numberOfRoom){
        // get total hotel can have
        List<Hotel> listHotelNoPaging = hotelRepository.getTotalHotelWithoutPaging(districtId);
        // remove some hotel if it is not eligible
        for (int i = listHotelNoPaging.size() - 1; i >= 0; i--) {
            if (getTotalRoom(listHotelNoPaging.get(i).getListRoomType()) < numberOfRoom
                    || getTotalPeople(listHotelNoPaging.get(i).getListRoomType()) < numberOfPeople) {
                listHotelNoPaging.remove(listHotelNoPaging.get(i));
            }
        }
        // get size no paging
        return listHotelNoPaging.size();
    }

    @Override
    public Page<HotelDTO> searchHotel(int districtId, Date dateIn, Date dateOut, int numberOfPeople, int numberOfRoom, Pageable pageable) {
        // get all hotel in this district
        Page<Hotel> hotelPage = hotelRepository.searchHotelByDistrict(districtId, pageable);
        // convert page to list to process
        List<Hotel> result = new ArrayList<>(hotelPage.getContent());
        // remove some hotel if it is not eligible
        for (int i = result.size() - 1; i >= 0; i--) {
            if (getTotalRoom(result.get(i).getListRoomType()) < numberOfRoom
                    || getTotalPeople(result.get(i).getListRoomType()) < numberOfPeople) {
                result.remove(result.get(i));
            }
        }
        // final result in hotelList DTO
        List<HotelDTO> hotelDTOList = result.stream().map(item -> modelMapper.map(item, HotelDTO.class)).collect(Collectors.toList());
        // set property lowest price and deal percentage
        for (int i = 0; i < hotelDTOList.size(); i++) {
            // set price
            hotelDTOList.get(i).setPrice(getLowestPriceInHotel(result.get(i).getListRoomType()).getPrice());
            // set %deal
            hotelDTOList.get(i).setSalePercent(getLowestPriceInHotel(result.get(i).getListRoomType()).getDealPercentage());
            // set deal expired
            hotelDTOList.get(i).setDealExpired(getLowestPriceInHotel(result.get(i).getListRoomType()).getDealExpire());
            // set rating
            RatingDTO ratingDTO = getRatingByHotel(result.get(i));
            hotelDTOList.get(i).setRating(ratingDTO);
            // set review
            ReviewDTO reviewDTO = getTop1ReviewAboutHotel(result.get(i).getListUserBooking());
            hotelDTOList.get(i).setReview(reviewDTO);
        }
        // hotel with no room have deal and price = 0
        return new CustomPageImpl<>(hotelDTOList);
    }

    @Override
    public Page<HotelDTO> getAllHotels(int status, Pageable pageable) {
        List<HotelDTO> hotelDTOList;
        List<Hotel> resultList;
        if (status == 0) {
            resultList = hotelRepository.findAllHotel(pageable).getContent();
        } else {
            resultList = hotelRepository.findAllByStatus(status, pageable).getContent();
        }
        // convert to DTO list
        hotelDTOList = resultList.stream().map(item -> modelMapper.map(item, HotelDTO.class)).collect(Collectors.toList());
        // set property lowest price and deal percentage
        for (int i = 0; i < hotelDTOList.size(); i++) {
            // set price
            hotelDTOList.get(i).setPrice(getLowestPriceInHotel(resultList.get(i).getListRoomType()).getPrice());
            // set %deal
            hotelDTOList.get(i).setSalePercent(getLowestPriceInHotel(resultList.get(i).getListRoomType()).getDealPercentage());
            // set deal expired
            hotelDTOList.get(i).setDealExpired(getLowestPriceInHotel(resultList.get(i).getListRoomType()).getDealExpire());
        }
        // hotel with no room have deal and price = 0
        return new CustomPageImpl<>(hotelDTOList);
    }

    @Override
    public HotelDetailDTO getDetailHotelById(int hotelId) {
        return modelMapper.map(hotelRepository.getHotelById(hotelId), HotelDetailDTO.class);
    }

    @Transactional
    @Override
    public void banHotelByHotelId(int hotelId) {
        hotelRepository.updateHotelStatus(hotelId, 4);
    }

    @Transactional
    @Override
    public void banHotelByProviderId(int providerId) {
        hotelRepository.banHotelByProviderId(providerId);
    }

    @Override
    public List<HotelDTO> viewListHotelByProviderId(int providerId) {
        List<Hotel> hotelList = hotelRepository.getAllByProviderId(providerId);
        List<HotelDTO> hotelDTOList = hotelList.stream().map(item -> modelMapper.map(item, HotelDTO.class))
                .collect(Collectors.toList());
        // set property lowest price and deal percentage
        for (int i = 0; i < hotelDTOList.size(); i++) {
            // set price
            hotelDTOList.get(i).setPrice(getLowestPriceInHotel(hotelList.get(i).getListRoomType()).getPrice());
            // set %deal
            hotelDTOList.get(i).setSalePercent(getLowestPriceInHotel(hotelList.get(i).getListRoomType()).getDealPercentage());
            // set deal expired
            hotelDTOList.get(i).setDealExpired(getLowestPriceInHotel(hotelList.get(i).getListRoomType()).getDealExpire());
        }
        return hotelDTOList;
    }

    @Transactional
    @Override
    public void disableHotel(int hotelId) {
        hotelRepository.updateHotelStatus(hotelId, 2);
    }

    @Transactional
    @Override
    public void enableHotel(int hotelId) {
        hotelRepository.updateHotelStatus(hotelId, 1);
    }

    @Override
    public HotelDTO getHotelById(int hotelId) {
        Hotel hotel = hotelRepository.getHotelById(hotelId);
        HotelDTO hotelDTO = modelMapper.map(hotel, HotelDTO.class);
        // set lowest price
        hotelDTO.setPrice(getLowestPriceInHotel(hotel.getListRoomType()).getPrice());
        // set % deal
        hotelDTO.setSalePercent(getLowestPriceInHotel(hotel.getListRoomType()).getDealPercentage());
        // set deal expired
        hotelDTO.setDealExpired(getLowestPriceInHotel(hotel.getListRoomType()).getDealExpire());
        // set rating
        RatingDTO ratingDTO = getRatingByHotel(hotel);
        hotelDTO.setRating(ratingDTO);
        // set review
        ReviewDTO reviewDTO = getTop1ReviewAboutHotel(hotel.getListUserBooking());
        hotelDTO.setReview(reviewDTO);
        return hotelDTO;
    }

    @Transactional
    @Override
    public void addHotelByProvider(HotelRequest hotelRequest) {
        // set status pending: 3, if admin approved -> status 1, if admin denied -> status 5
        hotelRequest.setStatus(3);
        // set district
        District district = new District();
        district.setId(hotelRequest.getDistrictId());
        // set provider
        Provider provider = new Provider();
        provider.setId(hotelRequest.getProviderId());
        Hotel hotel = modelMapper.map(hotelRequest, Hotel.class);
        hotel.setDistrict(district);
        hotel.setProvider(provider);

        // add new hotel
        hotelRepository.save(hotel);
    }

    @Override
    public Integer getHotelIdJustInsert() {
        return hotelRepository.getHotelIdJustInsert();
    }

    @Override
    public Integer viewHotelStatus(int hotelId) {
        return hotelRepository.viewHotelStatus(hotelId);
    }

    @Override
    public void updateHotel(HotelDTO hotelDTO) {
        // get hotel need to update from db
        Hotel hotel = hotelRepository.getHotelById(hotelDTO.getId());
        // set props again
        hotel.setAddress(hotelDTO.getAddress());
        hotel.setAvatar(hotelDTO.getAvatar());
        hotel.setDescription(hotelDTO.getDescription());
        hotel.setEmail(hotelDTO.getEmail());
        hotel.setName(hotelDTO.getName());
        hotel.setPhone(hotelDTO.getPhone());
        hotel.setStar(hotelDTO.getStar());
        hotel.setTaxPercentage(hotelDTO.getTaxPercentage());
        // save hotel again
        hotelRepository.save(hotel);
    }

    @Override
    public boolean isHotelHadRoom(int hotelId) {
        Hotel hotel = hotelRepository.getHotelById(hotelId);
        return hotel.getListRoomType().size() != 0;
    }

    @Override
    public RatingDTO getRatingByHotel(int hotelId) {
        // get hotel
        Hotel hotel = hotelRepository.getHotelById(hotelId);
        return getRatingByHotel(hotel);
    }

    @Override
    public List<HotelDTO> getTopHotHotel(int top) {
        // get list hotel id top
        List<Integer> hotelIds = bookingRepository.getTopHotHotelId(top);
        // get list hotel
        List<Hotel> hotelList = hotelRepository.findAllById(hotelIds);
        // transfer to DTO
        List<HotelDTO> hotelDTOList = hotelList.stream().map(item -> modelMapper.map(item, HotelDTO.class)).collect(Collectors.toList());
        // set property lowest price and deal percentage
        for (int i = 0; i < hotelDTOList.size(); i++) {
            // set price
            hotelDTOList.get(i).setPrice(getLowestPriceInHotel(hotelList.get(i).getListRoomType()).getPrice());
            // set %deal
            hotelDTOList.get(i).setSalePercent(getLowestPriceInHotel(hotelList.get(i).getListRoomType()).getDealPercentage());
            // set deal expired
            hotelDTOList.get(i).setDealExpired(getLowestPriceInHotel(hotelList.get(i).getListRoomType()).getDealExpire());
        }
        return hotelDTOList;
    }

}
