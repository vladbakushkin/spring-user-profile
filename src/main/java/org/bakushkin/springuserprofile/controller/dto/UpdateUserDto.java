package org.bakushkin.springuserprofile.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDto {

    @Size(min = 2, max = 255)
    private String lastName;

    @Size(min = 2, max = 255)
    private String firstName;

    @Size(min = 2, max = 255)
    private String middleName;

    @Past
    private LocalDate birthDate;

    @Email
    @Size(min = 6, max = 255)
    private String email;

    @Size(min = 10, max = 15)
    private String phone;
}
