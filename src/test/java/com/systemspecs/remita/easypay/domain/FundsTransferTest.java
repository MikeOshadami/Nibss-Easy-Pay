package com.systemspecs.remita.easypay.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.systemspecs.remita.easypay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FundsTransferTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FundsTransfer.class);
        FundsTransfer fundsTransfer1 = new FundsTransfer();
        fundsTransfer1.setId(1L);
        FundsTransfer fundsTransfer2 = new FundsTransfer();
        fundsTransfer2.setId(fundsTransfer1.getId());
        assertThat(fundsTransfer1).isEqualTo(fundsTransfer2);
        fundsTransfer2.setId(2L);
        assertThat(fundsTransfer1).isNotEqualTo(fundsTransfer2);
        fundsTransfer1.setId(null);
        assertThat(fundsTransfer1).isNotEqualTo(fundsTransfer2);
    }
}
