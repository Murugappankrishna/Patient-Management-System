package space.murugappan.billingmanagementsystem.Exception;

public class EmailAlreadyExistException extends RuntimeException{
   public EmailAlreadyExistException(String message){
        super(message);
    }
}
