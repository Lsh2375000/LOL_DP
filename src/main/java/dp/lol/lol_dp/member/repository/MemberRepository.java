package dp.lol.lol_dp.member.repository;

import dp.lol.lol_dp.member.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByMemberId(String memberId);
    /*Optional을 사용하는 이유는 값이 없을 때
    null값 대신 Optional.empty()를 반환해서 오류를 방지하기 위함*/
}
