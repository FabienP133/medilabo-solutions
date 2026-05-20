package com.medilabo.notes.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notes")
@Getter
@Setter
@NoArgsConstructor
public class Note {

    @Id
    private String id;

    @NotNull
    private Integer patientId;

    @NotBlank
    private String note;
}