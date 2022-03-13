package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Actor.ProviderDTO;
import com.capstone_project.hbts.entity.Provider;
import com.capstone_project.hbts.repository.ProviderRepository;
import com.capstone_project.hbts.request.ProviderRequest;
import com.capstone_project.hbts.response.CustomPageImpl;
import com.capstone_project.hbts.service.ProviderService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    private final ModelMapper modelMapper;

    public ProviderServiceImpl(ProviderRepository providerRepository, ModelMapper modelMapper) {
        this.providerRepository = providerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProviderDTO loadProviderByEmail(String email) {
        log.info("Request to load provider by email");
        Provider provider = providerRepository.getProviderByEmail(email);
        if(provider == null){
            return null;
        }else {
            return modelMapper.map(provider, ProviderDTO.class);
        }
    }

    @Override
    public void register(ProviderRequest providerRequest) {
        log.info("Request to register a new provider");

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
        log.info("Request to check duplicate username");
        String usernameFromDB = providerRepository.getUsername(username);
        return usernameFromDB != null;
    }

    @Override
    public boolean isEmailExist(String email) {
        log.info("Request to check duplicate email");
        String usernameFromDB = providerRepository.getEmail(email);
        return usernameFromDB != null;
    }

    @Override
    public ProviderDTO getProviderProfile(String username) {
        log.info("Request to get provider profile");
        Provider provider = providerRepository.getProviderByUsername(username);
        if(provider == null){
            return null;
        }else {
            return modelMapper.map(provider, ProviderDTO.class);
        }
    }

    @Override
    @Transactional
    public void updateProviderProfile(ProviderDTO providerDTO) {
        log.info("Request to update provider profile");
        providerRepository.updateProviderProfile(providerDTO.getProviderName(),
                providerDTO.getPhone(), providerDTO.getAddress(), providerDTO.getId());
    }

    @Override
    @Transactional
    public void changeProviderPassword(String username, String newPass) {
        log.info("Request to change provider's password");
        providerRepository.changePass(username, newPass);
    }

    @Override
    public String getOldPassword(String username) {
        log.info("Request to get provider's old password");
        return providerRepository.getOldPassword(username);
    }

    @Override
    public Page<ProviderDTO> getAllProvider(Pageable pageable) {
        log.info("Request to get all provider for admin");
        List<Provider> providerList = providerRepository.findAllProvider(pageable).getContent();

        List<ProviderDTO> providerDTOList = providerList.stream()
                .map(item -> modelMapper.map(item, ProviderDTO.class))
                .collect(Collectors.toList());

        return new CustomPageImpl<>(providerDTOList);
    }

    @Override
    @Transactional
    public void banProvider(int providerId) {
        log.info("Request to ban provider for admin");
        providerRepository.banProviderById(providerId);
    }

}
