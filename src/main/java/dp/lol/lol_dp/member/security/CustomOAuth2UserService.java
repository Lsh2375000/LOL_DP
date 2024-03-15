package dp.lol.lol_dp.member.security;


import dp.lol.lol_dp.member.domain.MemberEntity;
import dp.lol.lol_dp.member.dto.MemberDTO;
import dp.lol.lol_dp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("소셜 로그인 진입");
        log.info("userRequest : " + userRequest);

        log.info("OAuth2 User....");

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        log.info("Name : " + clientName);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();


        String email = null;
        switch (clientName) {
            case "kakao":
                email = getKakaoEmail(paramMap);
                break;
            case "google":
                email = getGoogleEmail(paramMap);
                break;
            case "naver":
                email = getNaverEamil(paramMap);
                break;
        }
        log.info("=============");
        log.info(email);
        log.info("=============");


        paramMap.forEach((k, v) ->{
            log.info("=============");
            log.info(k + " : " + v);
        });


        return generateDTO(email, paramMap);
    }

    private String getKakaoEmail(Map<String, Object> paramMap) {
        log.info("KAKAO LOGIN=============");

        Object value = paramMap.get("kakao_account");
        log.info(value);

        LinkedHashMap accountMap = (LinkedHashMap) value;
        String email = (String) accountMap.get("email");

        log.info("email : " + email);
        return email;
    }

    private String getGoogleEmail(Map<String, Object> paramMap) {
        log.info("GOOGLE LOGIN=============");

        String email = (String) paramMap.get("email");

        log.info("email : " + email);
        return email;
    }

    private String getNaverEamil(Map<String, Object> paramMap) {
        log.info("NAVER LOGIN=============");

        Map<String, Object> response = (Map<String, Object>) paramMap.get("response");

        String email = (String) response.get("email");

        log.info("email : " + email);

        return email;
    }


    private MemberDTO generateDTO(String email, Map<String, Object> params) {
        Optional<MemberEntity> result = memberRepository.findByMemberId(email);

        // DB에 해당 이메일의 사용자가 없다면
        if (result.isEmpty()) {
            log.info("Social Member");
            // 회원 추가 memberId는 이메일주소 / 패스워드는 1111 / 소환사 닉네임 ""
            MemberEntity member = MemberEntity.builder()
                    .memberId(email)
                    .passwd(passwordEncoder.encode("1111"))
                    .summonerName("")
                    .role("USER")
                    .isDel(false)
                    .social(true)
                    .build();
            memberRepository.save(member);
            List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("USER"));

            // MemberDTO 구성 및 반환
            MemberDTO memberDTO = new MemberDTO(
                    member.getMemberNo(),
                    member.getMemberId(),
                    member.getPasswd(),
                    member.getSummonerName(),
                    member.getIsDel(),
                    member.getRole(),
                    member.getSocial(),
                    authorities);
            memberDTO.setProps(params);
            return memberDTO;

        } else {
            log.info("Social Login");
            MemberEntity member = result.get();
            List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("USER"));

            MemberDTO memberDTO = new MemberDTO(
                    member.getMemberNo(),
                    member.getMemberId(),
                    member.getPasswd(),
                    member.getSummonerName(),
                    member.getIsDel(),
                    member.getRole(),
                    member.getSocial(),
                    authorities);
            return memberDTO;
        }

    }


}
