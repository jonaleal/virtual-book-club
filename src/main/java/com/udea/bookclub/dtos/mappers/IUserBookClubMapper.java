package com.udea.bookclub.dtos.mappers;

import com.udea.bookclub.dtos.UserBookClubDTO;
import com.udea.bookclub.models.UserBookClub;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserBookClubMapper {

    @Mapping(target = "bookClubId", source = "bookClub.bookClubId")
    @Mapping(target = "userId", source = "user.userId")
    UserBookClubDTO toUserBookClubDTO(UserBookClub userBookClub);

    @Mapping(target = "bookClub.bookClubId", source = "bookClubId")
    @Mapping(target = "user.userId", source = "userId")
    UserBookClub toUserBookClub(UserBookClubDTO userBookClubDTO);

    List<UserBookClubDTO> toUserBookClubsDTO(List<UserBookClub> userBookClubs);
}
