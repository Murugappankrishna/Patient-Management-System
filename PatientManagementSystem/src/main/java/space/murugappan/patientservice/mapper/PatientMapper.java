package space.murugappan.patientservice.mapper;

import space.murugappan.patientservice.dto.PatientRequestDTO;
import space.murugappan.patientservice.dto.PatientResponseDTO;
import space.murugappan.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientResponseDto = new PatientResponseDTO();
        patientResponseDto.setAddress(patient.getAddress());
        patientResponseDto.setEmail(patient.getEmail());
        patientResponseDto.setId(patient.getId().toString());
        patientResponseDto.setDateOfBirth(patient.getDateOfBirth().toString());
        patientResponseDto.setName(patient.getName());
        return patientResponseDto;

    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        return patient;
    }

}
