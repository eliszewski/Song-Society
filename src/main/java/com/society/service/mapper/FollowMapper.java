package com.society.service.mapper;

import com.society.domain.Follow;
import com.society.domain.User;
import com.society.service.dto.FollowDTO;
import com.society.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Follow} and its DTO {@link FollowDTO}.
 */
@Mapper(componentModel = "spring")
public interface FollowMapper extends EntityMapper<FollowDTO, Follow> {
    @Mapping(target = "follower", source = "follower", qualifiedByName = "userLogin")
    @Mapping(target = "followed", source = "followed", qualifiedByName = "userLogin")
    FollowDTO toDto(Follow s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
