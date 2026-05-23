package com.medilabo.assessment.service;

import com.medilabo.assessment.model.AssessmentResult;
import com.medilabo.assessment.model.Note;
import com.medilabo.assessment.model.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Locale;

@Service
public class AssessmentService {

    private static final List<String> TRIGGER_TERMS = List.of(
            "hemoglobine a1c",
            "microalbumine",
            "taille",
            "poids",
            "fumeur",
            "fumeuse",
            "fumer",
            "anormal",
            "cholesterol",
            "vertige",
            "vertiges",
            "rechute",
            "reaction",
            "anticorps"
    );

    private final RestClient restClient;

    public AssessmentService(@Value("${gateway.base-url}") String gatewayBaseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(gatewayBaseUrl)
                .build();
    }

    public AssessmentResult assessPatientRisk(Integer patientId) {
        Patient patient = getPatient(patientId);
        List<Note> notes = getNotes(patientId);

        int age = calculateAge(patient.getBirthDate());
        int triggerCount = countTriggerTerms(notes);
        String riskLevel = determineRiskLevel(patient.getGender(), age, triggerCount);

        return new AssessmentResult(patientId, riskLevel);
    }

    private Patient getPatient(Integer patientId) {
        return restClient.get()
                .uri("/api/patients/{id}", patientId)
                .retrieve()
                .body(Patient.class);
    }

    private List<Note> getNotes(Integer patientId) {
        return restClient.get()
                .uri("/api/notes/patient/{id}", patientId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private int countTriggerTerms(List<Note> notes) {
        String allNotes = notes.stream()
                .map(Note::getNote)
                .reduce("", (note1, note2) -> note1 + " " + note2);

        String normalizedNotes = normalizeText(allNotes);

        int count = 0;

        for (String triggerTerm : TRIGGER_TERMS) {
            if (normalizedNotes.contains(triggerTerm)) {
                count++;
            }
        }

        return count;
    }

    private String determineRiskLevel(String gender, int age, int triggerCount) {
        if (triggerCount == 0) {
            return "None";
        }

        if (age > 30 && triggerCount >= 2 && triggerCount <= 5) {
            return "Borderline";
        }

        if (age < 30 && "M".equalsIgnoreCase(gender) && triggerCount >= 5) {
            return "Early onset";
        }

        if (age < 30 && "F".equalsIgnoreCase(gender) && triggerCount >= 7) {
            return "Early onset";
        }

        if (age > 30 && triggerCount >= 8) {
            return "Early onset";
        }

        if (age < 30 && "M".equalsIgnoreCase(gender) && triggerCount >= 3) {
            return "In Danger";
        }

        if (age < 30 && "F".equalsIgnoreCase(gender) && triggerCount >= 4) {
            return "In Danger";
        }

        if (age > 30 && triggerCount >= 6) {
            return "In Danger";
        }

        return "None";
    }

    private String normalizeText(String text) {
        if (text == null) {
            return "";
        }

        String lowerCaseText = text.toLowerCase(Locale.ROOT);

        String normalizedText = Normalizer.normalize(lowerCaseText, Normalizer.Form.NFD);

        return normalizedText.replaceAll("\\p{M}", "");
    }
}