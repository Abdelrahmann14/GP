package com.spring.boot.rest.mapper.user;
import com.spring.boot.rest.dto.user.UserDetailsDto;
import com.spring.boot.rest.model.user.UserDetails;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper
public interface UserDetailsMapper {
    UserDetailsDto toUserDetailsDto(UserDetails userDetails);
    UserDetails toUserDetails(UserDetailsDto userDetailsDto);
    List<UserDetailsDto> toListOfUserDetailsDto(List<UserDetails> userDetails);
    List<UserDetails> toListOfUserDetails(List<UserDetailsDto> userDetailsDtos);

}
