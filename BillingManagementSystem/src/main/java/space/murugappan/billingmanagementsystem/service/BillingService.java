package space.murugappan.billingmanagementsystem.service;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.UpdateBillingRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import space.murugappan.billingmanagementsystem.Exception.EmailAlreadyExistException;
import space.murugappan.billingmanagementsystem.enums.PaymentStatus;
import space.murugappan.billingmanagementsystem.mapper.GrpcToJavaMapper;
import space.murugappan.billingmanagementsystem.model.BillingRequestModel;
import space.murugappan.billingmanagementsystem.repo.BillingRequestRepo;

import java.util.UUID;

@Service
public class BillingService {
    final GrpcToJavaMapper grpcToJavaMapper;
    final BillingRequestRepo billingRequestRepo;
    final private Validator validator;

    BillingService(GrpcToJavaMapper grpcToJavaMapper, BillingRequestRepo billingRequestRepo, Validator validator) {
        this.grpcToJavaMapper = grpcToJavaMapper;
        this.billingRequestRepo = billingRequestRepo;
        this.validator = validator;
    }

    public BillingResponse createBillingAccount(BillingRequest billingRequest) {
        BillingRequestModel billingRequestModel = grpcToJavaMapper.billingRequestMapper(billingRequest);
        var violations = validator.validate(billingRequestModel);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Validation failed", violations);
        }
        if (billingRequestRepo.existsByPatientEmail(billingRequest.getPatientEmail())) {
            throw new EmailAlreadyExistException("Email Already Exists !" + billingRequest.getPatientEmail());
        }
        BillingRequestModel savedBillingRequestModel = billingRequestRepo.save(billingRequestModel);
        return grpcToJavaMapper.javaToGrpcMapper(savedBillingRequestModel);


    }

    public BillingResponse updateBillingStatus(UpdateBillingRequest updateBillingRequest) {
        billingRequestRepo.updateStatusById(UUID.fromString(updateBillingRequest.getAccountId()), PaymentStatus.valueOf(updateBillingRequest.getPaymentStatus()));
        BillingRequestModel response = billingRequestRepo.findById(UUID.fromString(updateBillingRequest.getAccountId()))
                .orElseThrow(()-> new RuntimeException("Data Not Found"));

        return grpcToJavaMapper.javaToGrpcMapper(response);
    }
}
