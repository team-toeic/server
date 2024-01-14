package com.comeontoeic.authentication.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@IdClass(TermsOfServiceAgreementId.class)
public class TermsOfServiceAgreement {
    @Id
    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne(targetEntity = TermsOfService.class)
    @JoinColumn(name = "terms_of_service_id")
    private TermsOfService termsOfService;
    Boolean Agreement;

    public TermsOfServiceAgreement(){}

    public TermsOfServiceAgreement(Member member, TermsOfService termsOfService, Boolean agreement) {
        this.member = member;
        this.termsOfService = termsOfService;
        Agreement = agreement;
    }

    public static TermsOfServiceAgreement of (Member member, TermsOfService termsOfService, Boolean agreement) {
        return new TermsOfServiceAgreement(member, termsOfService, agreement);
    }
}
