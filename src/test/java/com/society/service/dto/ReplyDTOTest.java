package com.society.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.society.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReplyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReplyDTO.class);
        ReplyDTO replyDTO1 = new ReplyDTO();
        replyDTO1.setId(1L);
        ReplyDTO replyDTO2 = new ReplyDTO();
        assertThat(replyDTO1).isNotEqualTo(replyDTO2);
        replyDTO2.setId(replyDTO1.getId());
        assertThat(replyDTO1).isEqualTo(replyDTO2);
        replyDTO2.setId(2L);
        assertThat(replyDTO1).isNotEqualTo(replyDTO2);
        replyDTO1.setId(null);
        assertThat(replyDTO1).isNotEqualTo(replyDTO2);
    }
}
