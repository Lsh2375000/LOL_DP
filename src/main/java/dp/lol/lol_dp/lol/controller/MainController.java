package dp.lol.lol_dp.lol.controller;


import dp.lol.lol_dp.member.dto.MemberDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping({"/", "lol"})
public class MainController {

    @GetMapping("")
    public String mainGET(@AuthenticationPrincipal MemberDTO memberDTO, HttpSession session) {
        log.info("MainController mainGET().....");
        session.setAttribute("memberDTO", memberDTO);
        return "lol/main";
    }



}
