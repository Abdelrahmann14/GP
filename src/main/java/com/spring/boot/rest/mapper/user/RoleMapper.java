package com.spring.boot.rest.mapper.user;
import com.spring.boot.rest.dto.user.RoleDto;
import com.spring.boot.rest.model.user.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
@Mapper
public interface RoleMapper {
RoleMapper roleMapper= Mappers.getMapper(RoleMapper.class);
RoleDto toRoleDto(Role role);
Role toRole(RoleDto roleDto);
List<RoleDto> toListOfRoleDtos(List<Role> roles);
List<Role> toListOfROles(List<RoleDto> roleDtos);

}
