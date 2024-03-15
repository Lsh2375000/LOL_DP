package dp.lol.lol_dp.repository;

import dp.lol.lol_dp.member.domain.MemberEntity;
import dp.lol.lol_dp.member.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void insertMember() {
        MemberEntity insertData = MemberEntity.builder()
                .memberId("lol001")
                .passwd("1234")
                .summonerName("zaqxsw327")
                .isDel(false)
                .build();
        MemberEntity member = memberRepository.save(insertData);

        Assertions.assertEquals(member.getMemberId(), "lol001");
    }

    @Test
    public void selectAllMember() {
        memberRepository.findAll();
    }

    @Test
    public void selectMember() {
        memberRepository.findById(1L);
    }

    @Test
    public void deleteMember() {
        memberRepository.deleteById(2L);
    }

}

