package dp.lol.lol_dp.member.security.handler;

import dp.lol.lol_dp.member.dto.MemberDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("-------------------");
        log.info("CustomSocialLoginSuccessHandler onAuthenticationSuccess==================");
        log.info(authentication.getPrincipal());

        MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();

        String encodePasswd = memberDTO.getPasswd();

        // 소셜 로그인하고 비밀번호가 1111 일때 실행
        if (memberDTO.getSocial()
                && (memberDTO.getPasswd().equals("1111"))
                || passwordEncoder.matches("1111", encodePasswd)) {
            log.info("Should Change Password");

            log.info("Redirect to Member Modify");
            response.sendRedirect("/member/modify");

            return;
        } else {
            response.sendRedirect("/");
        }


    }
}
