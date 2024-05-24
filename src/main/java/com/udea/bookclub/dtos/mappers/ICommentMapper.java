package com.udea.bookclub.dtos.mappers;

import com.udea.bookclub.dtos.CommentDTO;
import com.udea.bookclub.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICommentMapper {

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "discussionId", source = "discussion.discussionId")
    CommentDTO toCommentDTO(Comment comment);

    @Mapping(target = "user.userId", source = "userId")
    @Mapping(target = "discussion.discussionId", source = "discussionId")
    Comment toComment(CommentDTO commentDTO);

    List<CommentDTO> toCommentsDTO(List<Comment> comments);
}
