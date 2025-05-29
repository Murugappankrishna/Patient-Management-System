package space.murugappan.patientservice.service;

import org.springframework.stereotype.Service;
import space.murugappan.patientservice.dto.PatientRequestDTO;
import space.murugappan.patientservice.dto.PatientResponseDTO;
import space.murugappan.patientservice.exception.EmailAlreadyExistException;
import space.murugappan.patientservice.exception.PatientNotFoundException;
import space.murugappan.patientservice.mapper.PatientMapper;
import space.murugappan.patientservice.model.Patient;
import space.murugappan.patientservice.repository.PatientRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients
                .stream()
                .map(PatientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Patient createPatients(PatientRequestDTO patientResponseDTO) {
        if (patientRepository.existsByEmail(patientResponseDTO.getEmail())) {
            throw new EmailAlreadyExistException("A Patient with this Email already Exist" + patientResponseDTO.getEmail());
        }
        return patientRepository.save(PatientMapper.toModel(patientResponseDTO));
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientResponseDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient Not Found With the ID" + id));
        if (patientRepository.existsByEmailAndIdNot(patientResponseDTO.getEmail(), id)) {
            throw new EmailAlreadyExistException("A Patient with this Email already Exist" + patientResponseDTO.getEmail());
        }
        patient.setName(patientResponseDTO.getName());
        patient.setAddress(patientResponseDTO.getAddress());
        patient.setEmail(patientResponseDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientResponseDTO.getDateOfBirth()));
        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);


    }
    public void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }

}
