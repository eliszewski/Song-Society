package com.society.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.society.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReplyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reply.class);
        Reply reply1 = new Reply();
        reply1.setId(1L);
        Reply reply2 = new Reply();
        reply2.setId(reply1.getId());
        assertThat(reply1).isEqualTo(reply2);
        reply2.setId(2L);
        assertThat(reply1).isNotEqualTo(reply2);
        reply1.setId(null);
        assertThat(reply1).isNotEqualTo(reply2);
    }
}
