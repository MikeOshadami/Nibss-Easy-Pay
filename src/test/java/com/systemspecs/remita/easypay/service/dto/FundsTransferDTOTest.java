package com.systemspecs.remita.easypay.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.systemspecs.remita.easypay.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FundsTransferDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FundsTransferDTO.class);
        FundsTransferDTO fundsTransferDTO1 = new FundsTransferDTO();
        fundsTransferDTO1.setId(1L);
        FundsTransferDTO fundsTransferDTO2 = new FundsTransferDTO();
        assertThat(fundsTransferDTO1).isNotEqualTo(fundsTransferDTO2);
        fundsTransferDTO2.setId(fundsTransferDTO1.getId());
        assertThat(fundsTransferDTO1).isEqualTo(fundsTransferDTO2);
        fundsTransferDTO2.setId(2L);
        assertThat(fundsTransferDTO1).isNotEqualTo(fundsTransferDTO2);
        fundsTransferDTO1.setId(null);
        assertThat(fundsTransferDTO1).isNotEqualTo(fundsTransferDTO2);
    }
}
