package com.spring.boot.rest.mapper.user;

import com.spring.boot.rest.dto.user.UserSignUpDto;
import com.spring.boot.rest.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;
@Mapper
public interface UserMapper {
UserMapper userMapper = Mappers.getMapper(UserMapper.class);
User toUser(UserSignUpDto userSignUpDto);
UserSignUpDto toUserDto(User user);
List<UserSignUpDto> toListOfUserDtos(List<User> users);
List<User> toListOfUser(List<UserSignUpDto> userSignUpDtos);

}
