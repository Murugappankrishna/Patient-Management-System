package space.murugappan.billingmanagementsystem.mapper;

import billing.AccountRequest;
import billing.AccountResponse;
import billing.UpdateBillingResponse;
import org.springframework.stereotype.Component;
import space.murugappan.billingmanagementsystem.enums.PaymentStatus;
import space.murugappan.billingmanagementsystem.model.Account;

import java.util.UUID;

@Component
public class AccountMapper {

    public Account gprcModelToModelClass(AccountRequest billingRequest) {
        Account account = new Account();
        account.setPatientEmail(billingRequest.getPatientEmail());
        account.setPatientName(billingRequest.getPatientName());
        account.setPatientId(UUID.fromString(billingRequest.getPatientId()));
        return account;
    }

    public AccountResponse modelClassToGrpc(Account account) {
        return AccountResponse.newBuilder()
                .setAccountId(account.getAccountId().toString())
                .setPatientName(account.getPatientName())
                .setPatientEmail(account.getPatientEmail())
                .setPatientId(account.getPatientId().toString())
                .build();

    }

    public UpdateBillingResponse modelClassToGrpcUpdateBillingResponse(Account updatedAccount) {
        return UpdateBillingResponse.newBuilder().
                setPatientName(updatedAccount.getPatientName())
                .setPaymentStatus((updatedAccount.getPaymentStatus()).toString())
                .build();

    }
}
