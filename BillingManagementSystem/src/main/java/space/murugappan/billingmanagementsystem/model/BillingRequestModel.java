package space.murugappan.billingmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import space.murugappan.billingmanagementsystem.enums.PaymentStatus;

import java.util.UUID;

@Entity
public class BillingRequestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountId;
    @NotBlank
    private String patientName;
    @NotBlank
    @Email
    @Column(unique = true)
    private String patientEmail;
    private UUID patientId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public BillingRequestModel() {
        this.paymentStatus = PaymentStatus.NEW;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public @NotNull String getPatientName() {
        return patientName;
    }

    public void setPatientName(@NotNull String patinetName) {
        this.patientName = patinetName;
    }

    public @NotNull @Email String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(@NotNull @Email String patienEmail) {
        this.patientEmail = patienEmail;
    }


    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }
}


