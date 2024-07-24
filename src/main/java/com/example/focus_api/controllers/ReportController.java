package com.example.focus_api.controllers;

import com.example.focus_api.domain.reports.PatchRequestReportStatus;
import com.example.focus_api.domain.reports.ReportRepository;
import com.example.focus_api.domain.reports.Reports;
import com.example.focus_api.domain.reports.RequestReport;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/focus/report")
public class ReportController {
    private final ReportRepository reportRepository;

    public ReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @GetMapping
    public ResponseEntity getAllReports() {
        var allReports = reportRepository.findAll();
        return ResponseEntity.ok(allReports);
    }

    @GetMapping("/{id}")
    public ResponseEntity getReportById(@PathVariable("id") String id) {
        return reportRepository.findById(id)
                .map(report -> ResponseEntity.ok(report))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity createReport(@RequestBody @Valid RequestReport data) {
        Reports reports = new Reports(data);
        reportRepository.save(reports);
        return ResponseEntity.ok("Report created");
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateStatusReport(@PathVariable("id") String id, @RequestBody @Valid PatchRequestReportStatus data) {
        return reportRepository.findById(id)
                .map(report -> {
                    report.setStatus(data.status());
                    reportRepository.save(report);
                    return ResponseEntity.ok("Report status updated");
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReport(@PathVariable("id") String id) {
        return reportRepository.findById(id)
                .map(report -> {
                    reportRepository.delete(report);
                    return ResponseEntity.ok("Report deleted");
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
