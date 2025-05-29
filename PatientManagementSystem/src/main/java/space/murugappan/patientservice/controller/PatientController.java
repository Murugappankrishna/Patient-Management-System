package space.murugappan.patientservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.murugappan.patientservice.dto.PatientRequestDTO;
import space.murugappan.patientservice.dto.PatientResponseDTO;
import space.murugappan.patientservice.dto.validators.CreatePatientValidationGroup;
import space.murugappan.patientservice.model.Patient;
import space.murugappan.patientservice.service.PatientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name="Patient", description = "API for managing patients")
public class PatientController {
    private final PatientService patientService;

    PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping()
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        return ResponseEntity.ok().body(patientService.getPatients());
    }

    @PostMapping()
    @Operation(summary = "Create Patients")
    public ResponseEntity<Patient> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        return ResponseEntity.ok().body(patientService.createPatients(patientRequestDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Patients by Id")
    public ResponseEntity<PatientResponseDTO> updatePatient(@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO,
                                                            @PathVariable UUID id) {
        return ResponseEntity.ok().body(patientService.updatePatient(id, patientRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Patients by Id")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {

        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();

    }
}
