package dp.lol.lol_dp.config;


import dp.lol.lol_dp.member.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class CustomSecurityConfig {

    private final DataSource dataSource;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain userFilterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("----------------------------- Configuration MEMBER --------------------------------------");
        httpSecurity.authorizeHttpRequests(requests -> {
            requests.anyRequest().permitAll();
        });
        // 커스텀 로그인 페이지 설정
        httpSecurity.formLogin(login -> login
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
        ).logout(logoutConfig -> { // 로그아웃 설정
            logoutConfig
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(
                            ((request, response, authentication) -> {
                                log.info("로그아웃 성공");
                                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                response.getWriter().println("로그아웃 성공!!");
                                response.sendRedirect("/");
                            })
                    );
        });

        // CRSF 토큰 비활성화
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // 자동 로그인 설정
        httpSecurity.rememberMe(remember -> remember
                .key("12345678")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(customUserDetailsService)
                .tokenValiditySeconds(60 * 60 * 24 * 30)// 유효기간 30일
        );

        return httpSecurity.build();
    }


    @Bean
    public WebSecurityCustomizer webClientCustomizer() {
        log.info("---------------------------Web Configure--------------------");
        // 정적 파일 경로에 시큐리티 적용을 안함.
        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // 자동 로그인을 위한 토큰 생성 주입
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 암호화된 비밀번호 인식하기 위한 암호인코더 주입
        return new BCryptPasswordEncoder();
    }




}
