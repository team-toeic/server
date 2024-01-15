package com.comeontoeic.authentication.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole = MemberRole.ROLE_USER;

    @OneToMany
    @JoinColumn(name = "terms_of_service_agreement_id")
    List<TermsOfServiceAgreement> termsOfServiceAgreements;

    public Member() {}

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
