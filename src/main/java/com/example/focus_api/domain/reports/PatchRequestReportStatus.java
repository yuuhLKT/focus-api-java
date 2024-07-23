package com.example.focus_api.domain.reports;

import jakarta.validation.constraints.NotNull;

public record PatchRequestReportStatus(@NotNull ReportStatus status) {
}
