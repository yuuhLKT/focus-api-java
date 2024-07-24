package com.example.focus_api.domain.comments;

import jakarta.validation.constraints.NotBlank;

public record RequestComments(@NotBlank String title,@NotBlank String authorName,@NotBlank String content,@NotBlank String postId)  {
}
