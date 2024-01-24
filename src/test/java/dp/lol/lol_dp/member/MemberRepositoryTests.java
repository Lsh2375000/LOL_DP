package dp.lol.lol_dp.member;

import dp.lol.lol_dp.domain.member.Member;
import dp.lol.lol_dp.domain.member.MemberRepository;
import dp.lol.lol_dp.domain.member.Tier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void insertMember() {
        Member insertData = Member.builder()
                .loginId("lol001")
                .password("1234")
                .name("Lee")
                .tier(Tier.GOLD)
                .birthday(LocalDate.of(2000, 1, 1))
                .deleteYn(false)
                .build();
        Member member = memberRepository.save(insertData);

        Assertions.assertEquals(member.getLoginId(), "lol001");
    }

}