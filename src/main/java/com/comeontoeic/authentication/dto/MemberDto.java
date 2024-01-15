package com.comeontoeic.authentication.dto;

import com.comeontoeic.authentication.domain.Member;
import com.comeontoeic.authentication.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String username;
    private String password;
    private MemberRole memberRole;
    private TokenDto token;

    public MemberDto(Long id, String username, MemberRole memberRole, TokenDto token) {
        this.id = id;
        this.username = username;
        this.memberRole = memberRole;
        this.token = token;
    }

    public static MemberDto from(Member member, TokenDto token) {
        return new MemberDto(member.getId(), member.getUsername(), member.getMemberRole(), token);
    }
}
