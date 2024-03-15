package dp.lol.lol_dp.member.controller;


import dp.lol.lol_dp.member.dto.MemberDTO;
import dp.lol.lol_dp.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    /* ====================== Member CRUD ====================== */
    @GetMapping("/login") // 로그인 GET
    public void loginGET(String error, String logout, HttpSession session) {
        log.info("MemberController loginGET()....");
        if (logout != null) {
            log.info(logout);
            log.info("logout");
            String message = "로그아웃 되었습니다.";
            session.setAttribute("logoutMsg", message);
        }
        if (error != null) {
            log.info("로그인 오류");
        }
    }




    @GetMapping("/signUp") // 회원가입 GET
    public void signUpGET() {
        log.info("MemberController signUpGET()....");
    }

    @PostMapping("/signUp") // 회원가입 POST
    public String signUpPOST(MemberDTO memberDTO) {
        log.info("MemberController signUpPOST()....");
        log.info("inputMemberDTO : " + memberDTO);
        memberService.signUp(memberDTO);

        return "redirect:/member/login";
    }

    @GetMapping("/modify")
    public void modifyGET(@AuthenticationPrincipal MemberDTO memberDTO, HttpSession session) {
        log.info("MemberController modifyGET()....");
        log.info("로그인한 아이디 : " + memberDTO.getMemberId());
        if (memberDTO == null) {
            throw new UsernameNotFoundException("잘못된 접근입니다.");
        }
    }

    @PostMapping("/modify")
    public String modifyPOST(MemberDTO memberDTO, @AuthenticationPrincipal MemberDTO securityMemberDTO) {
        log.info("MemberController modifyPOST()....");
        log.info("입력한 회원 수정 정보 : " + memberDTO);
        memberDTO.setMemberId(securityMemberDTO.getMemberId());
        // 회원 수정할 회원 아이디를 담아준다.
        if (!memberDTO.getPasswd().equals(securityMemberDTO.getPasswd())
                && !memberDTO.getPasswd().equals("")) {
            // 입력한 비밀번호가 변경되고 빈값이 아닐때
            memberService.modifyMemberInfo(memberDTO);
            return "redirect:/member/login?logout";
        }
        // 비밀번호는 그대로고 닉네임과 라이엇 아이디만 변경됐을때
        memberDTO.setPasswd(securityMemberDTO.getPasswd());
        memberService.modifyMemberInfo(memberDTO);
        return "redirect:/member/modify";

    }


    /* ====================== Member CRUD End ====================== */


}
