package com.comeontoeic.authentication.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class TermsOfService {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    private String name;
    private String content;
    private Boolean needAgreement;
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "terms_of_service_agreement_id")
    List<TermsOfServiceAgreement> termsOfServiceAgreements;
}
