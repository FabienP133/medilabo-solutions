package com.medilabo.ui.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssessmentResult {

    private Integer patientId;
    private String riskLevel;
}