package com.jerem.mdd.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.jerem.mdd.dto.UserDto;
import com.jerem.mdd.model.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    User userDtoToEntity(UserDto userDto);



}
