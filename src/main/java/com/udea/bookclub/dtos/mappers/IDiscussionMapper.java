package com.udea.bookclub.dtos.mappers;

import com.udea.bookclub.dtos.DiscussionDTO;
import com.udea.bookclub.models.Discussion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDiscussionMapper {
    @Mapping(target = "bookClub.bookClubId", source = "bookClubId")
    @Mapping(target = "user.userId", source = "userId")
    Discussion toDiscussion(DiscussionDTO discussionDTO);

    @Mapping(target = "bookClubId", source = "bookClub.bookClubId")
    @Mapping(target = "userId", source = "user.userId")
    DiscussionDTO toDiscussionDTO(Discussion discussion);

    List<DiscussionDTO> toDiscussionsDTO(List<Discussion> discussions);
}
