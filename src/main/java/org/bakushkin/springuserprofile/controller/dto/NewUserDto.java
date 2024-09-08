package org.bakushkin.springuserprofile.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Регистрация пользователя")
public class NewUserDto {

    @Schema(description = "Фамилия", example = "Petrov")
    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 255, message = "Фамилия должна содержать от 2 до 255 символов")
    private String lastName;

    @Schema(description = "Имя", example = "Petr")
    @NotBlank(message = "Имя не может быть пустыми")
    @Size(min = 2, max = 255, message = "Имя должно содержать от 2 до 255 символов")
    private String firstName;

    @Schema(description = "Отчество", example = "Petrovich")
    @Size(min = 2, max = 255)
    private String middleName;

    @Schema(description = "Дата рождения", example = "1960-01-01")
    @Past
    private LocalDate birthDate;

    @Schema(description = "Адрес электронной почты", example = "petrov@gmail.com")
    @Size(min = 6, max = 255, message = "Адрес электронной почты должен содержать от 6 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Номер телефона", example = "79111230000")
    @NotBlank(message = "Телефон не может быть пустыми")
    @Size(min = 10, max = 15, message = "Телефон должен содержать от 10 до 15 символов")
    private String phone;

    @Schema(description = "Имя пользователя", example = "petr_love_java")
    @Size(min = 2, max = 255, message = "Имя пользователя должно содержать от 2 до 255 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Schema(description = "Пароль", example = "password")
    @Size(min = 5, max = 255, message = "Длина пароля должна быть не более 255 символов")
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}
