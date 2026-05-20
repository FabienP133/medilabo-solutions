package com.medilabo.ui.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Note {

    private String id;
    private Integer patientId;
    private String note;
}