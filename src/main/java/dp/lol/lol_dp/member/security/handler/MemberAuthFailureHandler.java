package dp.lol.lol_dp.member.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class MemberAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        // 실패 메시지를 담기 위한 세션 선언

        session.setAttribute("errorMsg", exception.getMessage());
        // 세션에 실패 메세지 담기

        setDefaultFailureUrl("/member/login?error");
        // 로그인 실패시 이동할 페이지로 이동

        super.onAuthenticationFailure(request, response, exception);
    }
}
