package com.society.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReplyMapperTest {

    private ReplyMapper replyMapper;

    @BeforeEach
    public void setUp() {
        replyMapper = new ReplyMapperImpl();
    }
}
