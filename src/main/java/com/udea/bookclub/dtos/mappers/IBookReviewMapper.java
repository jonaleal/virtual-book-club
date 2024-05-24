package com.udea.bookclub.dtos.mappers;

import com.udea.bookclub.dtos.BookReviewDTO;
import com.udea.bookclub.models.BookReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBookReviewMapper {
    @Mapping(target = "bookClub.bookClubId", source = "bookClubId")
    @Mapping(target = "user.userId", source = "userId")
    BookReview toBookReview(BookReviewDTO bookReviewDTO);

    @Mapping(target = "bookClubId", source = "bookClub.bookClubId")
    @Mapping(target = "userId", source = "user.userId")
    BookReviewDTO toBookReviewDTO(BookReview bookReview);

    List<BookReviewDTO> toBookReviewsDTO(List<BookReview> bookReviews);

}
