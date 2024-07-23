package com.example.focus_api.domain.feedbacks;

import jakarta.validation.constraints.NotBlank;

public record RequestFeedback(@NotBlank String title, @NotBlank String authorName, @NotBlank String content) {
}
