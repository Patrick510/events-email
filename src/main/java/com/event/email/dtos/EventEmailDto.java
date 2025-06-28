package com.event.email.dtos;

public record EventEmailDto(
                String emailTo,
                String subject,
                String body) {
}
