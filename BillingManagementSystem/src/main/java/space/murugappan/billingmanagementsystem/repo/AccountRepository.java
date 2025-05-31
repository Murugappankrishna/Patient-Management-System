package space.murugappan.billingmanagementsystem.repo;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import space.murugappan.billingmanagementsystem.enums.PaymentStatus;
import space.murugappan.billingmanagementsystem.model.Account;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    Boolean existsByPatientEmail(String patientEmail);
    @Query("UPDATE Account A SET A.paymentStatus=:status WHERE A.accountId=:id")
    @Modifying
    @Transactional
    void updateStatusById(UUID id , PaymentStatus status);
}
