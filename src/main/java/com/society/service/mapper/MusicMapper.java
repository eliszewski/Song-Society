package com.society.service.mapper;

import com.society.domain.Music;
import com.society.domain.User;
import com.society.service.dto.MusicDTO;
import com.society.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Music} and its DTO {@link MusicDTO}.
 */
@Mapper(componentModel = "spring")
public interface MusicMapper extends EntityMapper<MusicDTO, Music> {
    @Mapping(target = "users", source = "users", qualifiedByName = "userLoginSet")
    MusicDTO toDto(Music s);

    @Mapping(target = "removeUser", ignore = true)
    Music toEntity(MusicDTO musicDTO);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("userLoginSet")
    default Set<UserDTO> toDtoUserLoginSet(Set<User> user) {
        return user.stream().map(this::toDtoUserLogin).collect(Collectors.toSet());
    }
}
