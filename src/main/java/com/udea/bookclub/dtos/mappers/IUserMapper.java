package com.udea.bookclub.dtos.mappers;

import com.udea.bookclub.dtos.UserDTO;
import com.udea.bookclub.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    //    @Mapping(target = "userId", source = "userDTO.userId")
//    @Mapping(target = "username", source = "userDTO.username")
//    @Mapping(target = "pictureUrl", source = "userDTO.pictureUrl")
//    @Mapping(target = "email", source = "userDTO.email")
//    @Mapping(target = "password", source = "userDTO.password")
    User toUser(UserDTO userDTO);

    //    @Mapping(target = "userId", source = "user.userId")
//    @Mapping(target = "username", source = "user.username")
//    @Mapping(target = "pictureUrl", source = "user.pictureUrl")
//    @Mapping(target = "email", source = "user.email")
//    @Mapping(target = "password", source = "user.password")
    UserDTO toUserDTO(User user);

    //    @Mapping(target = "userId", source = "user.userId")
//    @Mapping(target = "username", source = "user.username")
//    @Mapping(target = "pictureUrl", source = "user.pictureUrl")
//    @Mapping(target = "email", source = "user.email")
//    @Mapping(target = "password", source = "user.password")
    List<UserDTO> toUsersDTO(List<User> users);
}
