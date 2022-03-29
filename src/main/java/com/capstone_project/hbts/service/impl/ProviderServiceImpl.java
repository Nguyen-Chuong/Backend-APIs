package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Actor.ProviderDTO;
import com.capstone_project.hbts.entity.Provider;
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

import java.util.List;
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
    public Page<ProviderDTO> getAllProvider(Pageable pageable) {
        List<Provider> providerList = providerRepository.findAllProvider(pageable).getContent();
        List<ProviderDTO> providerDTOList = providerList.stream().map(item -> modelMapper.map(item, ProviderDTO.class))
                .collect(Collectors.toList());
        return new CustomPageImpl<>(providerDTOList);
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

}
