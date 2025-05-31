package space.murugappan.billingmanagementsystem.grpc;

import billing.AccountRequest;
import billing.AccountResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import billing.UpdateBillingRequest;
import billing.UpdateBillingResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.validation.ConstraintViolationException;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.murugappan.billingmanagementsystem.exception.EmailAlreadyExistException;
import space.murugappan.billingmanagementsystem.service.BillingService;


@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);
    private final BillingService billingService;
    BillingGrpcService(BillingService billingService) {
        this.billingService = billingService;
    }
    @Override
    public void createBillingAccount(AccountRequest accountRequest,
                                     StreamObserver<AccountResponse> responseObserver) {
        try {
            AccountResponse createdAccount = billingService.createBillingAccount(accountRequest);
            responseObserver.onNext(createdAccount);
            responseObserver.onCompleted();
        } catch (EmailAlreadyExistException ex) {
            responseObserver.onError(Status.ALREADY_EXISTS.withDescription(ex.getMessage()).withCause(ex).asRuntimeException());
        } catch (ConstraintViolationException ex) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(ex.getMessage()).withCause(ex).asRuntimeException());
        } catch (Exception ex) {
            responseObserver.onError(Status.INTERNAL.withDescription(ex.getMessage()).withCause(ex).asRuntimeException());
        }

    }

    @Override
    public void updateBillingStatus(UpdateBillingRequest UpdateBillingRequest,
                                    StreamObserver<UpdateBillingResponse> responseObserver) {
        AccountResponse  billingResponse  = billingService.updateBillingStatus(UpdateBillingRequest);

      //responseObserver.onNext(billingResponse);

        responseObserver.onCompleted();



    }

}
