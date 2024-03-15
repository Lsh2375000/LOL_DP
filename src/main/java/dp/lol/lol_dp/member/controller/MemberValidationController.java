package dp.lol.lol_dp.member.controller;


import dp.lol.lol_dp.member.service.MailSenderService;
import dp.lol.lol_dp.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberValidationController {
    private final MemberService memberService;
    private final MailSenderService mailSenderService;

    @PostMapping("/exEmail/{email}") // 이메일 중복검사
    public Boolean isEmail(@PathVariable("email") String email, HttpSession session) {
        log.info("idCheck......");
        log.info(email);

        if (memberService.getByMemberId(email) != null) {
            log.info("null.....");
            session.setAttribute("email", email);
            return false;
        }
        return true;
    }


    @GetMapping("/sendConfirmMail/{mailTo}") // 이메일 인증
    public Boolean sendConfirmMail(@PathVariable("mailTo") String mailTo, HttpSession session) throws Exception {
        if (mailSenderService.sendMailByAddMember(mailTo)) {
            String confirmKey = mailSenderService.getConfirmKey(); // 인증문자를 변수에 저장
            session.setAttribute("confirmKey", confirmKey); // 변수를 세션에 저장
            session.setAttribute("inputEmail", mailTo); // 입력한 이메일을 세션에 저장
            log.info("입력한 이메일 : "+ mailTo);
            log.info("인증문자 :" + confirmKey); // 변수를 로그에 출력
            return true;
        }
        else {
            return false;
        }
    }

    @PostMapping("/matchConfirmKey/{confirmKey}") // 입력한 인증문자 일치 여부 확인
    public Boolean  matchConfirmKey(@PathVariable("confirmKey") String confirmKey, HttpSession session) throws Exception {
        log.info("matchConfirmKey......");
        String matchConfirmKey =  (String) session.getAttribute("confirmKey"); // 변수에 세션값을 저장
        log.info(matchConfirmKey);
        log.info(confirmKey);
        if (confirmKey.equals(matchConfirmKey)) {
            session.setAttribute("isEmail", true); // 맞는 인증문자를 입력했다면 true를 세션에 담아준다.
            return true;
        } else {
            return false;
        }
    }

}
