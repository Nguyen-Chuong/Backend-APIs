package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.ChartDTO;
import com.capstone_project.hbts.dto.actor.ProviderDTO;
import com.capstone_project.hbts.entity.Hotel;
import com.capstone_project.hbts.entity.Provider;
import com.capstone_project.hbts.entity.UserBooking;
import com.capstone_project.hbts.entity.UserBookingDetail;
import com.capstone_project.hbts.repository.ProviderRepository;
import com.capstone_project.hbts.request.ProviderRequest;
import com.capstone_project.hbts.response.CustomPageImpl;
import com.capstone_project.hbts.service.ProviderService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    private final ModelMapper modelMapper;

    public ProviderServiceImpl(ProviderRepository providerRepository, ModelMapper modelMapper) {
        this.providerRepository = providerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProviderDTO loadProviderByEmail(String email) {
        Provider provider = providerRepository.getProviderByEmail(email);
        if(provider == null){
            return null;
        }else {
            return modelMapper.map(provider, ProviderDTO.class);
        }
    }

    @Override
    public void register(ProviderRequest providerRequest) {
        // name prefix for provider table
        providerRequest.setUsername("p-" + providerRequest.getUsername());
        // set active for new provider
        providerRequest.setStatus(1);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Provider newProvider = modelMapper.map(providerRequest, Provider.class);
        newProvider.setPassword(bCryptPasswordEncoder.encode(newProvider.getPassword()));
        providerRepository.save(newProvider);
    }

    @Override
    public boolean isUsernameExist(String username) {
        String usernameFromDB = providerRepository.getUsername(username);
        return usernameFromDB != null;
    }

    @Override
    public boolean isEmailExist(String email) {
        String usernameFromDB = providerRepository.getEmail(email);
        return usernameFromDB != null;
    }

    @Override
    public ProviderDTO getProviderProfile(String username) {
        Provider provider = providerRepository.getProviderByUsername(username);
        if(provider == null){
            return null;
        }else {
            return modelMapper.map(provider, ProviderDTO.class);
        }
    }

    @Transactional
    @Override
    public void updateProviderProfile(ProviderDTO providerDTO) {
        providerRepository.updateProviderProfile(providerDTO.getProviderName(), providerDTO.getPhone(),
                providerDTO.getAddress(), providerDTO.getId());
    }

    @Transactional
    @Override
    public void changeProviderPassword(String username, String newPass) {
        providerRepository.changePass(username, newPass);
    }

    @Override
    public String getOldPassword(String username) {
        return providerRepository.getOldPassword(username);
    }

    @Override
    public Page<ProviderDTO> getAllProvider(int status, Pageable pageable) {
        // status 1 active, 0 banned
        List<Provider> providerList = providerRepository.findAllProvider(status, pageable).getContent();
        // convert to dto
        List<ProviderDTO> providerDTOList = providerList.stream().map(item -> modelMapper.map(item, ProviderDTO.class))
                .collect(Collectors.toList());
        return new CustomPageImpl<>(providerDTOList);
    }

    @Override
    public int getNumberProviderNoPaging(int status) {
        return providerRepository.getNumberOfProvider(status);
    }

    @Transactional
    @Override
    public void banProvider(int providerId) {
        providerRepository.banProviderById(providerId);
    }

    @Transactional
    @Override
    public void changeForgotPassword(String email, String newPass) {
        providerRepository.changeProviderForgotPassword(email, newPass);
    }

    public BigDecimal countTotalPaidForABooking(UserBooking userBooking){
        // get % discount VIP travesily
        int discount = userBooking.getUsers().getVip().getDiscount();
        // get number day
        long numberDayBooking = ChronoUnit.DAYS.between(userBooking.getCheckIn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                userBooking.getCheckOut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        // get list user booking detail
        Set<UserBookingDetail> userBookingDetails = userBooking.getListUserBookingDetail();
        BigDecimal totalPaid = BigDecimal.valueOf(0);
        for(UserBookingDetail item : userBookingDetails){
            totalPaid = totalPaid.add(item.getPaid().multiply(BigDecimal.valueOf(item.getQuantity()))
                    .multiply(BigDecimal.valueOf(numberDayBooking)));
        }
        // count total paid discounted by travesily VIP
        BigDecimal totalPaidDiscountedVIP = totalPaid.multiply(BigDecimal.valueOf(1 - (double) discount/100));
        // get tax
        double tax = (double) userBooking.getHotel().getTaxPercentage() / 100;
        return totalPaidDiscountedVIP.multiply(BigDecimal.valueOf(1 + tax));
    }

    @Override
    public ChartDTO getChartData(LocalDate fromDate, LocalDate toDate, String providerName) {
        // to get all hotel of this provider that has booking
        Set<Hotel> setHotel = providerRepository.getProviderByUsername(providerName).getListHotel().stream()
                .filter(item -> item.getListUserBooking().size() > 0).collect(Collectors.toSet());
        // to get all booking of this hotel
        List<UserBooking> listBookingOfAllHotel = new ArrayList<>();
        for(Hotel hotel : setHotel){
            listBookingOfAllHotel.addAll(hotel.getListUserBooking());
        }
        // range date in chart
        List<LocalDate> localDate = new ArrayList<>();
        for (LocalDate date = fromDate; date.isBefore(toDate); date = date.plusDays(1)) {
            localDate.add(date);
        }
        // range date to display
        List<String> labels = localDate.stream().map(item -> item.getDayOfMonth() + "/" + item.getMonth()).collect(Collectors.toList());
        // data (number of booking) in that date
        List<Integer> dataBooking = new ArrayList<>();
        for(LocalDate date : localDate){
            // get number of booking
            int number = (int) listBookingOfAllHotel.stream().filter(item -> item.getCheckIn().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate().equals(date)).filter(item -> item.getStatus() == 1 || item.getStatus() == 2).count() * 1000;
            dataBooking.add(number);
        }
        // data (amount) int that date
        List<Long> dataAmount = new ArrayList<>();
        for(LocalDate date : localDate){
            // get amount of that date
            List<UserBooking> userBookingListAmount = listBookingOfAllHotel.stream().filter(item -> item.getCheckIn().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate().equals(date)).filter(item -> item.getStatus() == 2)
                    .collect(Collectors.toList());
            long totalAmount = 0L;
            for(UserBooking userBooking : userBookingListAmount){
                totalAmount = totalAmount + countTotalPaidForABooking(userBooking).longValue() / 1000;
            }
            dataAmount.add(totalAmount);
        }
        // data return
        ChartDTO chartDTO = new ChartDTO();
        chartDTO.setLabels(labels);
        chartDTO.setDataBooking(dataBooking);
        chartDTO.setDataAmount(dataAmount);
        return chartDTO;
    }

}
