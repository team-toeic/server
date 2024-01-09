package com.comeontoeic.authentication.dto.request;

import com.comeontoeic.authentication.domain.MemberRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class SignUpRequest {
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @Length
    private String username;

    @Pattern(regexp = "[a-zA-Z1-9!@#$%^&*()]{8,16}",
    message = "비밀번호는 영어, 숫자, 특수문자(!@#$%^&*())를 포함한 8~16자리로 입력해주세요.")
    String password;

    @NotBlank
    String passwordConfirm;

    MemberRole memberRole = MemberRole.ROLE_USER;
}
