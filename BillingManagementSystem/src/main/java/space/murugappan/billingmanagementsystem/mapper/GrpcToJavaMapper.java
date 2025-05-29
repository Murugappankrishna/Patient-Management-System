package space.murugappan.billingmanagementsystem.mapper;

import billing.BillingRequest;
import billing.BillingResponse;
import org.springframework.stereotype.Component;
import space.murugappan.billingmanagementsystem.model.BillingRequestModel;

import java.util.UUID;

@Component
public class GrpcToJavaMapper {

    public BillingRequestModel billingRequestMapper(BillingRequest billingRequest) {
        BillingRequestModel billingRequestModel = new BillingRequestModel();
        billingRequestModel.setPatientEmail(billingRequest.getPatientEmail());
        billingRequestModel.setPatientName(billingRequest.getPatientName());
        billingRequestModel.setPatientId(UUID.fromString(billingRequest.getPatientId()));
        return billingRequestModel;
    }

    public BillingResponse javaToGrpcMapper(BillingRequestModel billingRequestModel) {
        return BillingResponse.newBuilder()
                .setAccountId(billingRequestModel.getAccountId().toString())
                .setPatientName(billingRequestModel.getPatientName())
                .setPatientId(billingRequestModel.getPatientId().toString())
                .build();

    }
}
