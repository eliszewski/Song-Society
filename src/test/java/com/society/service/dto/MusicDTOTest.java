package com.society.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.society.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MusicDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MusicDTO.class);
        MusicDTO musicDTO1 = new MusicDTO();
        musicDTO1.setId(1L);
        MusicDTO musicDTO2 = new MusicDTO();
        assertThat(musicDTO1).isNotEqualTo(musicDTO2);
        musicDTO2.setId(musicDTO1.getId());
        assertThat(musicDTO1).isEqualTo(musicDTO2);
        musicDTO2.setId(2L);
        assertThat(musicDTO1).isNotEqualTo(musicDTO2);
        musicDTO1.setId(null);
        assertThat(musicDTO1).isNotEqualTo(musicDTO2);
    }
}
