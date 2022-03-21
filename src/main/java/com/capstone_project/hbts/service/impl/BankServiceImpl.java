package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.BankDTO;
import com.capstone_project.hbts.repository.BankRepository;
import com.capstone_project.hbts.service.BankService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    private final ModelMapper modelMapper;

    public BankServiceImpl(BankRepository bankRepository, ModelMapper modelMapper) {
        this.bankRepository = bankRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BankDTO> getAllBanks() {
        log.info("Request to get all bank");
        return bankRepository.findAll()
                .stream()
                .map(item -> modelMapper.map(item, BankDTO.class))
                .collect(Collectors.toList());
    }

}
