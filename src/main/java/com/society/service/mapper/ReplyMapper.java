package com.society.service.mapper;

import com.society.domain.Post;
import com.society.domain.Reply;
import com.society.domain.User;
import com.society.service.dto.PostDTO;
import com.society.service.dto.ReplyDTO;
import com.society.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reply} and its DTO {@link ReplyDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReplyMapper extends EntityMapper<ReplyDTO, Reply> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "post", source = "post", qualifiedByName = "postId")
    ReplyDTO toDto(Reply s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("postId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostDTO toDtoPostId(Post post);
}
