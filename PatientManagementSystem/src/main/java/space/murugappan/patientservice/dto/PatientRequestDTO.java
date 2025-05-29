package space.murugappan.patientservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import space.murugappan.patientservice.dto.validators.CreatePatientValidationGroup;

public class PatientRequestDTO {
    @NotBlank(message="Name is Required")
    @Size(max = 100 , message= " Name Must Not Exceed 100 Characters")
    private String name;
    @NotBlank(message="Email is Required")
    @Email
    private String email;
    @NotBlank (message="Address is Required")
    private String address;
    @NotBlank(message="DateOfBirth is Required")
    private String dateOfBirth;
    @NotBlank(groups= CreatePatientValidationGroup.class
            ,message="RegisteredDate is Required")

    private String registeredDate;
    public @NotBlank(message = "Name is Required") @Size(max = 100, message = " Name Must Not Exceed 100 Characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is Required") @Size(max = 100, message = " Name Must Not Exceed 100 Characters") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Email is Required") @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is Required") @Email String email) {
        this.email = email;
    }

    public @NotBlank(message = "Address is Required") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "Address is Required") String address) {
        this.address = address;
    }

    public @NotBlank(message = "DateOfBirth is Required") String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotBlank(message = "DateOfBirth is Required") String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }
}
