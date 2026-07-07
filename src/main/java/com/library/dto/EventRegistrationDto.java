package com.library.dto;

import java.time.LocalDate;

public record EventRegistrationDto (
        long eventId,
        String eventTitle,
        LocalDate eventDate,
        long memberId,
        String memberName,
        String memberEmail
) {
}
