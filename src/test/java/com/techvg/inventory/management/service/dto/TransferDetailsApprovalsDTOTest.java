package com.techvg.inventory.management.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransferDetailsApprovalsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferDetailsApprovalsDTO.class);
        TransferDetailsApprovalsDTO transferDetailsApprovalsDTO1 = new TransferDetailsApprovalsDTO();
        transferDetailsApprovalsDTO1.setId(1L);
        TransferDetailsApprovalsDTO transferDetailsApprovalsDTO2 = new TransferDetailsApprovalsDTO();
        assertThat(transferDetailsApprovalsDTO1).isNotEqualTo(transferDetailsApprovalsDTO2);
        transferDetailsApprovalsDTO2.setId(transferDetailsApprovalsDTO1.getId());
        assertThat(transferDetailsApprovalsDTO1).isEqualTo(transferDetailsApprovalsDTO2);
        transferDetailsApprovalsDTO2.setId(2L);
        assertThat(transferDetailsApprovalsDTO1).isNotEqualTo(transferDetailsApprovalsDTO2);
        transferDetailsApprovalsDTO1.setId(null);
        assertThat(transferDetailsApprovalsDTO1).isNotEqualTo(transferDetailsApprovalsDTO2);
    }
}
