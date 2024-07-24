package com.example.focus_api.controllers;

import com.example.focus_api.domain.reports.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ReportController.class)
public class ReportControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportRepository reportRepository;

    private Reports report;

    @BeforeEach
    void setUp() {
        report = new Reports();
        report.setId("1");
        report.setTitle("Test Report");
        report.setAuthorName("Author");
        report.setContent("Content of the report");
        report.setStatus(ReportStatus.OPEN);
    }

    @Test
    void testGetAllReports() throws Exception {
        Mockito.when(reportRepository.findAll()).thenReturn(Collections.singletonList(report));

        mockMvc.perform(get("/focus/report"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(report.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(report.getTitle()));
    }

    @Test
    void testGetReportById() throws Exception {
        Mockito.when(reportRepository.findById(report.getId())).thenReturn(Optional.of(report));

        mockMvc.perform(get("/focus/report/{id}", report.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(report.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(report.getTitle()));
    }

    @Test
    void testCreateReport() throws Exception {
        RequestReport requestReport = new RequestReport("New Report", "New Author", "New Content");

        mockMvc.perform(post("/focus/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Report\", \"authorName\": \"New Author\", \"content\": \"New Content\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Report created"));
    }

    @Test
    void testUpdateStatusReport() throws Exception {
        PatchRequestReportStatus patchRequestReportStatus = new PatchRequestReportStatus(ReportStatus.WORKING);
        Mockito.when(reportRepository.findById(report.getId())).thenReturn(Optional.of(report));

        mockMvc.perform(patch("/focus/report/{id}", report.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"WORKING\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Report status updated"));
    }

    @Test
    void testDeleteReport() throws Exception {
        Mockito.when(reportRepository.findById(report.getId())).thenReturn(Optional.of(report));

        mockMvc.perform(delete("/focus/report/{id}", report.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Report deleted"));
    }
}
