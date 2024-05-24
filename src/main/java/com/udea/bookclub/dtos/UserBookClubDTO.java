package com.udea.bookclub.dtos;

import jakarta.validation.constraints.Null;

public record UserBookClubDTO(
        @Null
        Long userBookClubId,
        Long userId,
        Long bookClubId) {
}
