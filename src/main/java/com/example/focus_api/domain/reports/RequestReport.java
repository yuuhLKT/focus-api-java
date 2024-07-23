package com.example.focus_api.domain.reports;

import jakarta.validation.constraints.NotBlank;

public record RequestReport(@NotBlank String title,@NotBlank String authorName,@NotBlank String content) {
}
