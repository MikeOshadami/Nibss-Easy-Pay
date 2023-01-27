package com.systemspecs.remita.easypay.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FundsTransferMapperTest {

    private FundsTransferMapper fundsTransferMapper;

    @BeforeEach
    public void setUp() {
        fundsTransferMapper = new FundsTransferMapperImpl();
    }
}
