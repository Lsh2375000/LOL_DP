package dp.lol.lol_dp.member.security;

import dp.lol.lol_dp.member.dto.MemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Log4j2
@Component
/*
    이 클래스를 자바 빈으로 등록시키라고 알려줄 수 있다. 해당 어노테이션이 명시되어 있으면
    Spring이 자동으로 클래스의 인스턴스를 생성하고 종속성을 포함하여 각종 lifecycle을 관리해준다.
*/
public class CustomAuthenticatorProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName(); // 사용자가 입력한 id
        String password = (String) authentication.getCredentials(); // 사용자가 입력한 password

        // customUserDetailService에서 loadUserByUsername메소드를 호출하여 회원 정보를 가져온다.
        MemberDTO memberDTO = (MemberDTO) customUserDetailsService.loadUserByUsername(username);

        // 사용자가 입력한 비밀번호와 DB의 비밀번호를 비교한다.
        String dbPasswd = memberDTO.getPasswd();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(password, dbPasswd)) {
            log.info("입력한 비밀번호가 일치하지 않습니다.");
            throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        // 인증이 성공하면 UsernamePasswordAuthenticationToken 객체를 반환
        // 해당 객체는 Authentication 객체로 추후 인증이 끝나고 SecurityContextHolder.getContext()에 저장된다.
        return new UsernamePasswordAuthenticationToken(memberDTO, null, memberDTO.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
