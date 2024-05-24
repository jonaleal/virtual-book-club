package com.udea.bookclub.dtos.mappers;

import com.udea.bookclub.dtos.BookClubDTO;
import com.udea.bookclub.models.BookClub;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBookClubMapper {
    @Mapping(target = "user.userId", source = "userId")
    BookClub toBookClub(BookClubDTO bookClubDTO);

    @Mapping(target = "userId", source = "user.userId")
    BookClubDTO toBookClubDTO(BookClub bookClub);

    List<BookClubDTO> toBookClubsDTO(List<BookClub> bookClubs);
}
