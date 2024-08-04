package com.neftali.passgenerator.dto;

import com.neftali.passgenerator.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "email", target = "email")
    UserDTO userToUserDTO(User user);

    @Mapping(source = "email", target = "email")
    User userDTOToUser(UserDTO userDTO);

}
