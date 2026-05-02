package com.medilabo.ui.controller;

import com.medilabo.ui.model.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientUiController {

    private final RestClient restClient;

    public PatientUiController(@Value("${gateway.base-url}") String gatewayBaseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(gatewayBaseUrl)
                .build();
    }

    @GetMapping
    public String getPatients(Model model) {
        List<Patient> patients = restClient.get()
                .uri("/api/patients")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        model.addAttribute("patients", patients);
        return "patients";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("formAction", "/patients/add");
        model.addAttribute("pageTitle", "Ajouter un patient");
        return "patient-form";
    }

    @PostMapping("/add")
    public String addPatient(@ModelAttribute Patient patient) {
        restClient.post()
                .uri("/api/patients")
                .body(patient)
                .retrieve()
                .toBodilessEntity();
        return "redirect:/patients";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        Patient patient = restClient.get()
                .uri("/api/patients/{id}", id)
                .retrieve()
                .body(Patient.class);

        model.addAttribute("patient", patient);
        model.addAttribute("formAction", "/patients/update/" + id);
        model.addAttribute("pageTitle", "Modifier un patient");
        return "patient-form";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable Integer id, @ModelAttribute Patient patient) {
        restClient.put()
                .uri("/api/patients/{id}", id)
                .body(patient)
                .retrieve()
                .toBodilessEntity();
        return "redirect:/patients";
    }
}