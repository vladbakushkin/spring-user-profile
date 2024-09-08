package org.bakushkin.springuserprofile.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Обновление данных пользователя")
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
