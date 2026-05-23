package com.medilabo.assessment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssessmentResult {

    private Integer patientId;
    private String riskLevel;
}