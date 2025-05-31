package space.murugappan.billingmanagementsystem.service;

import billing.AccountResponse;
import billing.AccountRequest;
import billing.UpdateBillingRequest;
import billing.UpdateBillingResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import space.murugappan.billingmanagementsystem.exception.DataNotFoundException;
import space.murugappan.billingmanagementsystem.exception.EmailAlreadyExistException;
import space.murugappan.billingmanagementsystem.enums.PaymentStatus;
import space.murugappan.billingmanagementsystem.mapper.AccountMapper;
import space.murugappan.billingmanagementsystem.model.Account;
import space.murugappan.billingmanagementsystem.repo.AccountRepository;

import java.util.UUID;

@Service
public class BillingService {
    final AccountMapper accountMapper;
    final AccountRepository accountRepository;
    final private Validator validator;

    BillingService(AccountMapper accountMapper, AccountRepository accountRepository, Validator validator) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
        this.validator = validator;
    }

    public AccountResponse createBillingAccount(AccountRequest billingRequest) {
        Account account = accountMapper.gprcModelToModelClass(billingRequest);
        var violations = validator.validate(account);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Validation failed", violations);
        }
        if (accountRepository.existsByPatientEmail(billingRequest.getPatientEmail())) {
            throw new EmailAlreadyExistException("Email Already Exists !" + billingRequest.getPatientEmail());
        }
        Account createdAccount = accountRepository.save(account);
        return accountMapper.modelClassToGrpc(createdAccount);


    }

    public UpdateBillingResponse updateBillingStatus(UpdateBillingRequest updateBillingRequest) {
        accountRepository.updateStatusById(UUID.fromString(updateBillingRequest.getAccountId()), PaymentStatus.valueOf(updateBillingRequest.getPaymentStatus()));
        Account response = accountRepository.findById(UUID.fromString(updateBillingRequest.getAccountId()))
                .orElseThrow(()-> new DataNotFoundException("No Account Found with the Id"+updateBillingRequest.getAccountId()));

        return accountMapper.modelClassToGrpcUpdateBillingResponse(response);
    }
}
