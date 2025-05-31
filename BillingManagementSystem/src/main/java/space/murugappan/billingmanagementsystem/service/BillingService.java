package space.murugappan.billingmanagementsystem.service;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.UpdateBillingRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import space.murugappan.billingmanagementsystem.exception.EmailAlreadyExistException;
import space.murugappan.billingmanagementsystem.enums.PaymentStatus;
import space.murugappan.billingmanagementsystem.mapper.GrpcToJavaMapper;
import space.murugappan.billingmanagementsystem.model.Account;
import space.murugappan.billingmanagementsystem.repo.AccountRepository;

import java.util.UUID;

@Service
public class BillingService {
    final GrpcToJavaMapper grpcToJavaMapper;
    final AccountRepository accountRepository;
    final private Validator validator;

    BillingService(GrpcToJavaMapper grpcToJavaMapper, AccountRepository accountRepository, Validator validator) {
        this.grpcToJavaMapper = grpcToJavaMapper;
        this.accountRepository = accountRepository;
        this.validator = validator;
    }

    public BillingResponse createBillingAccount(BillingRequest billingRequest) {
        Account account = grpcToJavaMapper.gprcModelToModelClass(billingRequest);
        var violations = validator.validate(account);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Validation failed", violations);
        }
        if (accountRepository.existsByPatientEmail(billingRequest.getPatientEmail())) {
            throw new EmailAlreadyExistException("Email Already Exists !" + billingRequest.getPatientEmail());
        }
        Account createdAccount = accountRepository.save(account);
        return grpcToJavaMapper.modelClassToGrpc(createdAccount);


    }

    public BillingResponse updateBillingStatus(UpdateBillingRequest updateBillingRequest) {
        accountRepository.updateStatusById(UUID.fromString(updateBillingRequest.getAccountId()), PaymentStatus.valueOf(updateBillingRequest.getPaymentStatus()));
        Account response = accountRepository.findById(UUID.fromString(updateBillingRequest.getAccountId()))
                .orElseThrow(()-> new RuntimeException("Data Not Found"));

        return grpcToJavaMapper.modelClassToGrpc(response);
    }
}
