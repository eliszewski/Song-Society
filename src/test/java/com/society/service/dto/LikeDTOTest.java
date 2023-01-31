package com.society.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.society.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LikeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LikeDTO.class);
        LikeDTO likeDTO1 = new LikeDTO();
        likeDTO1.setId(1L);
        LikeDTO likeDTO2 = new LikeDTO();
        assertThat(likeDTO1).isNotEqualTo(likeDTO2);
        likeDTO2.setId(likeDTO1.getId());
        assertThat(likeDTO1).isEqualTo(likeDTO2);
        likeDTO2.setId(2L);
        assertThat(likeDTO1).isNotEqualTo(likeDTO2);
        likeDTO1.setId(null);
        assertThat(likeDTO1).isNotEqualTo(likeDTO2);
    }
}
