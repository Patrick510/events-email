package com.event.email.dtos;

import java.util.Date;

public record EventEmailDto(
                String emailTo,
                String eventTitle,
                Date eventDate) {
}
