package com.comeontoeic.authentication.service;

import com.comeontoeic.authentication.domain.Member;
import com.comeontoeic.authentication.domain.TermsOfServiceAgreement;
import com.comeontoeic.authentication.dto.CustomUserDetails;
import com.comeontoeic.authentication.dto.MemberDto;
import com.comeontoeic.authentication.dto.TokenDto;
import com.comeontoeic.authentication.dto.request.LoginRequest;
import com.comeontoeic.authentication.dto.request.SignupRequest;
import com.comeontoeic.authentication.repository.MemberRepository;
import com.comeontoeic.authentication.repository.TermsOfServiceRepository;
import com.comeontoeic.common.jwt.JwtProvider;
import com.comeontoeic.exception.custom.AlreadyRegisteredUserException;
import com.comeontoeic.exception.custom.PasswordNotMatchException;
import com.comeontoeic.exception.custom.TermsOfServiceAgreementNeedException;
import com.comeontoeic.exception.custom.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final TermsOfServiceRepository termsOfServiceRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = getMemberFromRepositoryByUsername(username);
        return new CustomUserDetails(member);
    }

    public void signup(SignupRequest signupRequest) {
        checkDuplicatedUsername(signupRequest.getUsername());
        checkPasswordAndPasswordConfirmMatch(signupRequest);

        Member member = encodePasswordAndConvertToMemberEntity(signupRequest);
        checkTermsOfServiceAgreements(signupRequest.getTermsOfServices(), member);

        memberRepository.save(member);
    }

    public MemberDto login(LoginRequest loginRequest) {
        System.out.println(loginRequest.getUsername());
        Member member = getMemberFromRepositoryByUsername(loginRequest.getUsername());
        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword()))
            throw new PasswordNotMatchException();

        TokenDto tokenDto = new TokenDto(jwtProvider.createToken(member.getUsername(), member.getMemberRole()));

        return MemberDto.from(member, tokenDto);
    }

    private void checkDuplicatedUsername(String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new AlreadyRegisteredUserException();
        }
    }

    private void checkPasswordAndPasswordConfirmMatch(SignupRequest signupRequest) {
        if (!signupRequest.getPassword().equals(signupRequest.getPasswordConfirm())) {
            throw new PasswordNotMatchException();
        }
    }

    private Member encodePasswordAndConvertToMemberEntity(SignupRequest signupRequest) {
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        Member member = signupRequest.toEntity(encodedPassword);
        return member;
    }

    private void checkTermsOfServiceAgreements(List<Boolean> termsOfServiceAgreements, Member member) {
        for (long i = 1; i <= termsOfServiceAgreements.size(); i++) {
            if (termsOfServiceRepository.findById(i).get().getNeedAgreement() && !termsOfServiceAgreements.get((int) (i - 1)))
                throw new TermsOfServiceAgreementNeedException();
        }

        for (long i = 1; i <= termsOfServiceAgreements.size(); i++) {
            TermsOfServiceAgreement.of(member, termsOfServiceRepository.findById(i).get(), termsOfServiceAgreements.get((int)(i - 1)));
        }
    }

    private Member getMemberFromRepositoryByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException());
    }
}