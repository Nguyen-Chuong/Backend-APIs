package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Benefit.BenefitDTO;
import com.capstone_project.hbts.dto.Benefit.BenefitResult;
import com.capstone_project.hbts.dto.Benefit.BenefitTypeDTO;
import com.capstone_project.hbts.dto.Benefit.ObjectBenefit;
import com.capstone_project.hbts.dto.Facility.FacilityDTO;
import com.capstone_project.hbts.dto.Facility.FacilityResult;
import com.capstone_project.hbts.dto.Facility.FacilityTypeDTO;
import com.capstone_project.hbts.dto.Facility.ObjectFacility;
import com.capstone_project.hbts.dto.ImageDTO;
import com.capstone_project.hbts.dto.Room.RoomDetailDTO;
import com.capstone_project.hbts.dto.Room.RoomTypeDTO;
import com.capstone_project.hbts.entity.RoomType;
import com.capstone_project.hbts.entity.UserBooking;
import com.capstone_project.hbts.entity.UserBookingDetail;
import com.capstone_project.hbts.repository.BenefitRepository;
import com.capstone_project.hbts.repository.BookingDetailRepository;
import com.capstone_project.hbts.repository.FacilityRepository;
import com.capstone_project.hbts.repository.RoomTypeRepository;
import com.capstone_project.hbts.request.RoomTypeRequest;
import com.capstone_project.hbts.service.RoomTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    private final ModelMapper modelMapper;

    private final FacilityRepository facilityRepository;

    private final BenefitRepository benefitRepository;

    private final BookingDetailRepository bookingDetailRepository;

    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository, ModelMapper modelMapper, FacilityRepository facilityRepository,
                               BenefitRepository benefitRepository, BookingDetailRepository bookingDetailRepository) {
        this.roomTypeRepository = roomTypeRepository;
        this.modelMapper = modelMapper;
        this.facilityRepository = facilityRepository;
        this.benefitRepository = benefitRepository;
        this.bookingDetailRepository = bookingDetailRepository;
    }

    @Transactional
    @Override
    public void createRoomType(RoomTypeRequest roomTypeRequest) {
        roomTypeRepository.addNewRoomType(roomTypeRequest.getAvailableRooms(), roomTypeRequest.getDealExpire(),
                roomTypeRequest.getDealPercentage(), roomTypeRequest.getName(), roomTypeRequest.getNumberOfPeople(),
                roomTypeRequest.getPrice(), roomTypeRequest.getQuantity(), roomTypeRequest.getHotelId());
    }

    @Override
    public Integer getRoomIdJustInsert() {
        return roomTypeRepository.getRoomTypeIdJustInsert();
    }

    @Transactional
    @Override
    public void updateRoomType(RoomTypeDTO roomTypeDTO) {
        // get room to update
        RoomType roomType = roomTypeRepository.getRoomTypeById(roomTypeDTO.getId());
        roomType.setAvailableRooms(roomTypeDTO.getAvailableRooms());
        roomType.setDealExpire(roomTypeDTO.getDealExpire());
        roomType.setDealPercentage(roomTypeDTO.getDealPercentage());
        roomType.setName(roomTypeDTO.getName());
        roomType.setNumberOfPeople(roomTypeDTO.getNumberOfPeople());
        roomType.setPrice(roomTypeDTO.getPrice());
        roomType.setQuantity(roomTypeDTO.getQuantity());
        roomTypeRepository.save(roomType);
    }

    // check if a room is sold out or not
    public int numberOfRoomLeft(RoomType roomType, List<UserBooking> listBookingInThisRoom, Date dateIn, Date dateOut) {
        // get total quantity room available
        int numberOfRoomsTotal = roomType.getQuantity();
        // check if some user cancel booking in this day, plus total quantity 1
        for (UserBooking userBooking : listBookingInThisRoom) {
            // cancelled
            if (userBooking.getStatus() == 3) {
                numberOfRoomsTotal = numberOfRoomsTotal + 1;
            }
        }
        for (UserBooking userBooking : listBookingInThisRoom) {
            // get date user booking checkin
            Date userBookingCheckIn = new Date(userBooking.getCheckIn().getTime());
            // get date user booking check out
            Date userBookingCheckOut = new Date(userBooking.getCheckOut().getTime());
            // cases that date in, date out is not available
            if (dateIn.before(userBookingCheckIn) && dateOut.after(userBookingCheckIn)
                    || dateIn.before(userBookingCheckOut) && dateOut.after(userBookingCheckOut)
                    || dateIn.after(userBookingCheckIn) && dateOut.before(userBookingCheckOut)
                    || dateIn.before(userBookingCheckIn) && dateOut.after(userBookingCheckOut)
                    || dateIn.equals(userBookingCheckIn) && dateOut.equals(userBookingCheckOut)
                    || dateIn.equals(userBookingCheckIn) && dateOut.before(userBookingCheckOut)
                    || dateIn.after(userBookingCheckIn) && dateOut.equals(userBookingCheckOut)) {
                // minus number of room total with number of room booked
                // get list user booking detail
                Set<UserBookingDetail> setBookingDetail = userBooking.getListUserBookingDetail();
                // get room type equal w/ room type user want to book
                UserBookingDetail userBookingDetail = setBookingDetail.stream().filter(item -> item.getRoomType()
                        .equals(roomType)).findFirst().orElse(new UserBookingDetail());
                numberOfRoomsTotal = numberOfRoomsTotal - userBookingDetail.getQuantity();
            }
        }
        return numberOfRoomsTotal;
    }

    @Override
    public List<RoomTypeDTO> loadRoomTypeByHotelIdForSearch(int hotelId, Date dateIn, Date dateOut) {
        List<RoomType> list = roomTypeRepository.findRoomTypeByHotelId(hotelId);
        // get array list roomType ids
        ArrayList<Integer> roomTypeIds = (ArrayList<Integer>) list.stream().map(RoomType::getId).collect(Collectors.toList());
        // get all user booking detail by list room ids
        List<UserBookingDetail> bookingDetailList = bookingDetailRepository.getAllByRoomTypeId(roomTypeIds);
        // create a map to store room type and its user booking
        Map<RoomType, List<UserBooking>> mapRoomAndBooking = new LinkedHashMap<>();
        for (RoomType roomType : list) {
            // filter booking detail has room type equal w/ item in list room type
            List<UserBookingDetail> bookingDetail = bookingDetailList.stream().filter(element -> element.getRoomType()
                    .equals(roomType)).collect(Collectors.toList());
            // get list user booking
            List<UserBooking> userBookings = bookingDetail.stream().map(UserBookingDetail::getUserBooking).collect(Collectors.toList());
            // put them into map
            mapRoomAndBooking.put(roomType, userBookings);
        }
        // for loop through map and set available room = 0 for these rooms that sold out
        for (var entry : mapRoomAndBooking.entrySet()) {
            // check if a room is sold out or not
            int numberRoomLeft = numberOfRoomLeft(entry.getKey(), entry.getValue(), dateIn, dateOut);
            if (numberRoomLeft <= 0) {
                // set available room = 0
                entry.getKey().setAvailableRooms(0);
            } else {
                // set available room left
                entry.getKey().setAvailableRooms(numberRoomLeft);
            }
        }
        return mapRoomAndBooking.keySet().stream().map(item -> modelMapper.map(item, RoomTypeDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void disableRoomType(int roomTypeId) {
        roomTypeRepository.disableRoomTypeById(roomTypeId);
    }

    @Transactional
    @Override
    public void enableRoomType(int roomTypeId) {
        roomTypeRepository.enableRoomTypeById(roomTypeId);
    }

    @Override
    public RoomDetailDTO viewRoomDetail(int roomTypeId) {
        // get room type by id
        RoomType roomType = roomTypeRepository.getRoomTypeById(roomTypeId);
        // get set image from this room type and transfer to DTO
        Set<ImageDTO> imageDTOSet = roomType.getListImage().stream().map(item -> modelMapper.map(item, ImageDTO.class))
                .collect(Collectors.toSet());
        // handle benefit
        List<Integer> listBenefitId = new ArrayList<>();
        // get benefit from this room and add id to list
        roomType.getListRoomBenefit().forEach(item -> listBenefitId.add(item.getBenefit().getId()));
        // get set benefitDTO by list id
        List<BenefitDTO> benefitDTOList = benefitRepository.findAllById(listBenefitId).stream()
                .map(item -> modelMapper.map(item, BenefitDTO.class)).collect(Collectors.toList());
        // to remove duplicate benefit type
        Set<BenefitTypeDTO> setBenefitType = new HashSet<>();
        benefitDTOList.forEach(item -> setBenefitType.add(item.getBenefitType()));
        // list benefit to return
        List<ObjectBenefit> finalResultBenefit = new ArrayList<>();
        // loop through set benefit type
        for (BenefitTypeDTO item : setBenefitType) {
            // filter to add benefitDTOs that has this benefit type to a list
            List<BenefitDTO> listBenefit = benefitDTOList.stream().filter(element -> element.getBenefitType().equals(item))
                    .collect(Collectors.toList());
            // remove BenefitTypeDTO property in list return
            List<BenefitResult> listBenefitResult = listBenefit.stream()
                    .map(element -> modelMapper.map(element, BenefitResult.class)).collect(Collectors.toList());
            // add new object benefit and put to list
            ObjectBenefit obj = new ObjectBenefit(item.getId(), item.getName(), item.getIcon(), listBenefitResult);
            finalResultBenefit.add(obj);
        }

        // handle facility
        List<Integer> listFacilityId = new ArrayList<>();
        // get facility from this room and add id to list
        roomType.getListRoomFacility().forEach(item -> listFacilityId.add(item.getFacility().getId()));
        // get set FacilityDTO by list id
        List<FacilityDTO> facilityDTOList = facilityRepository.findAllById(listFacilityId).stream()
                .map(item -> modelMapper.map(item, FacilityDTO.class)).collect(Collectors.toList());
        // to remove duplicate facility type
        Set<FacilityTypeDTO> setFacilityType = new HashSet<>();
        facilityDTOList.forEach(item -> setFacilityType.add(item.getFacilityType()));
        // list facility to return
        List<ObjectFacility> finalResultFacility = new ArrayList<>();
        // loop through set facility type
        for (FacilityTypeDTO item : setFacilityType) {
            // filter to add facilityDTOs that has this facility type to a list
            List<FacilityDTO> listFacility = facilityDTOList.stream().filter(element -> element.getFacilityType().equals(item))
                    .collect(Collectors.toList());
            // remove FacilityTypeDTO property in list return
            List<FacilityResult> listFacilityResult = listFacility.stream()
                    .map(element -> modelMapper.map(element, FacilityResult.class)).collect(Collectors.toList());
            // add new object facility and put to list
            ObjectFacility obj = new ObjectFacility(item.getId(), item.getName(), item.getIcon(), listFacilityResult);
            finalResultFacility.add(obj);
        }
        // convert to RoomDetailDTO
        return new RoomDetailDTO(roomType.getId(), roomType.getName(), roomType.getPrice(), roomType.getNumberOfPeople(),
                roomType.getQuantity(), roomType.getAvailableRooms(), roomType.getDealPercentage(), roomType.getDealExpire(),
                imageDTOSet, finalResultFacility, finalResultBenefit);
    }

    @Transactional
    @Override
    public void createSQLEventUpdateDealViaDateExpired() {
        roomTypeRepository.createSQLEventUpdateDealViaDateExpired();
    }

    @Override
    public List<RoomTypeDTO> loadRoomTypeByHotelId(int hotelId) {
        List<RoomType> list = roomTypeRepository.findRoomTypeByHotelId(hotelId);
        return list.stream().map(item -> modelMapper.map(item, RoomTypeDTO.class)).collect(Collectors.toList());
    }

    @Override
    public RoomDetailDTO viewRoomDetailWithAvailableQuantity(int roomTypeId, Date dateIn, Date dateOut) {
        // get room type by id
        RoomType roomType = roomTypeRepository.getRoomTypeById(roomTypeId);
        // get list user booking in this room type
        List<UserBooking> userBookingList = roomType.getListUserBookingDetail().stream().map(UserBookingDetail::getUserBooking)
                .collect(Collectors.toList());
        RoomDetailDTO roomDetailDTO = viewRoomDetail(roomTypeId);
        int numberRoomLeft = numberOfRoomLeft(roomType, userBookingList, dateIn, dateOut);
        if (numberRoomLeft <= 0) {
            // set available room = 0
            roomDetailDTO.setAvailableRooms(0);
        } else {
            // set available room left
            roomDetailDTO.setAvailableRooms(numberRoomLeft);
        }
        return roomDetailDTO;
    }

}
