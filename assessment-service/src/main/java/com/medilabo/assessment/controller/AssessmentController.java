package com.medilabo.assessment.controller;

import com.medilabo.assessment.model.AssessmentResult;
import com.medilabo.assessment.service.AssessmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<AssessmentResult> getPatientAssessment(@PathVariable Integer patientId) {
        return ResponseEntity.ok(assessmentService.assessPatientRisk(patientId));
    }
}