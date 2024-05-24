package com.udea.bookclub.dtos;

import java.util.List;

public record EventRequest(
        String summary,
        String description,
        String startDateTime,
        String endDateTime,
        List<String> attendees) {
}
