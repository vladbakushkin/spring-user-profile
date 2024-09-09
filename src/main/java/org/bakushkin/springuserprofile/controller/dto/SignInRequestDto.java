package org.bakushkin.springuserprofile.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Запрос на аутентификацию")
public class SignInRequestDto {

    @Schema(description = "Имя пользователя", example = "petr_love_java")
    @Size(min = 2, max = 255, message = "Имя пользователя должно содержать от 2 до 255 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Schema(description = "Пароль", example = "password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;
}
