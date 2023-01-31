package com.society.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MusicMapperTest {

    private MusicMapper musicMapper;

    @BeforeEach
    public void setUp() {
        musicMapper = new MusicMapperImpl();
    }
}
