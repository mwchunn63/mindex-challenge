package com.mindex.challenge.service.impl;

import java.text.DecimalFormat;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);

        compensation.setEmployeeId(UUID.randomUUID().toString());
        compensationRepository.insert(compensation);
        
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        //System.out.println(df.format(compensation.getSalary()));

        return compensation;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Retrieving compensation with id [{}]", id);

        Compensation compensation = compensationRepository.findByEmployeeId(id);

        if (compensation == null) {
            throw new RuntimeException("Invalid Compensation for employeeId: " + id);
        }
        
        return compensation;
    }
}

