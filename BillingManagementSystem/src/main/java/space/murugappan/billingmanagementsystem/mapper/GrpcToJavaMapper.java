package space.murugappan.billingmanagementsystem.mapper;

import billing.BillingRequest;
import billing.BillingResponse;
import org.springframework.stereotype.Component;
import space.murugappan.billingmanagementsystem.model.Account;

import java.util.UUID;

@Component
public class GrpcToJavaMapper {

    public Account billingRequestMapper(BillingRequest billingRequest) {
        Account account = new Account();
        account.setPatientEmail(billingRequest.getPatientEmail());
        account.setPatientName(billingRequest.getPatientName());
        account.setPatientId(UUID.fromString(billingRequest.getPatientId()));
        return account;
    }

    public BillingResponse javaToGrpcMapper(Account account) {
        return BillingResponse.newBuilder()
                .setAccountId(account.getAccountId().toString())
                .setPatientName(account.getPatientName())
                .setPatientId(account.getPatientId().toString())
                .build();

    }
}
