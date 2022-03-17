package com.techvg.inventory.management.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.inventory.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransferDetailsApprovalsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferDetailsApprovals.class);
        TransferDetailsApprovals transferDetailsApprovals1 = new TransferDetailsApprovals();
        transferDetailsApprovals1.setId(1L);
        TransferDetailsApprovals transferDetailsApprovals2 = new TransferDetailsApprovals();
        transferDetailsApprovals2.setId(transferDetailsApprovals1.getId());
        assertThat(transferDetailsApprovals1).isEqualTo(transferDetailsApprovals2);
        transferDetailsApprovals2.setId(2L);
        assertThat(transferDetailsApprovals1).isNotEqualTo(transferDetailsApprovals2);
        transferDetailsApprovals1.setId(null);
        assertThat(transferDetailsApprovals1).isNotEqualTo(transferDetailsApprovals2);
    }
}
