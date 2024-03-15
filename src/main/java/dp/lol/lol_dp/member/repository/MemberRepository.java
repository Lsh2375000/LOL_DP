package dp.lol.lol_dp.member.repository;

import dp.lol.lol_dp.member.domain.MemberEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByMemberId(String memberId);
    /*Optional을 사용하는 이유는 값이 없을 때
    null값 대신 Optional.empty()를 반환해서 오류를 방지하기 위함*/

    @EntityGraph(attributePaths = "roleSet")
    @Query("SELECT m FROM MemberEntity m  WHERE m.memberId = :memberId AND m.social = FALSE ")
    Optional<MemberEntity> getWithRoles(@Param("memberId")String memberId);


}
