package com.comeontoeic.authentication.repository;


import com.comeontoeic.authentication.domain.TermsOfServiceAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsOfServiceAgreementRepository extends JpaRepository<TermsOfServiceAgreement, Long> {
}
