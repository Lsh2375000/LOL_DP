package dp.lol.lol_dp.service;

import dp.lol.lol_dp.member.dto.MemberDTO;
import dp.lol.lol_dp.member.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
public class MemberServiceTests {

    @Autowired
    MemberService memberService;

    @Test
    public void signUpTest() {
        MemberDTO memberDTO = MemberDTO.builder()
                .memberId("lol0002")
                .passwd("1234")
                .riotId("testRiot1")
                .build();
        log.info("회원 입력 정보 : " + memberDTO);
        memberService.signUp(memberDTO);
    }


    @Test
    public void getMemberInfo() {
       MemberDTO memberDTO = memberService.getByMemberId("lol0005");
       log.info("회원 정보 조회 : " + memberDTO);
    }

    @Test
    public void modifyMember() {
        MemberDTO memberDTO = memberService.getByMemberId("lol0001");
        log.info("수정 전 회원 정보 : " + memberDTO);
        memberDTO.setPasswd("5253");
        log.info("수정 후 회원 정보 : " + memberDTO);
        memberService.modifyMemberInfo(memberDTO);
    }


}
