package space.murugappan.patientservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.murugappan.patientservice.model.Patient;

import java.util.UUID;
@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email,UUID id);

}
