package dp.lol.lol_dp.member.domain;

import dp.lol.lol_dp.member.common.entity.BaseEntity;
import dp.lol.lol_dp.member.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/*
    @NoArgsConstructor
    Lombok에서 제공해주는 어노테이션으로, 매개변수를 갖지 않는 기본 생성자를 생성해주며,
    일반적으로 access = AccessLevel.PROTECTED 옵션을 선언해 무분별한 객체 생성을 방지한다.
*/
@AllArgsConstructor
/*
    builder 사용을 위해 추가
*/

@Entity
@Table(name = "member")
@ToString
@Builder
@Log4j2
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;        // 회원 번호 (PK)

    @Column(name = "memberId", nullable = false, unique = true)
    private String memberId;      // 로그인 이메일

    @Column(name = "passwd", nullable = false)
    private String passwd;      // 비밀번호

    @Column(name = "summonerName", nullable = false)
    private String summonerName;  // 소환사 닉네임

    @Column(name = "isDel")
    private Boolean isDel;        // 삭제 여부

    @Column(name = "role",columnDefinition = "VARCHAR(50) default 'USER'")
    private String role; // 회원 유형  (기본으로 USER로 지정)

    @Column(name = "social", nullable = false)
    private Boolean social;     // 소셜 로그인 여부

    @Override
    public void prePersist() {
        this.role = this.role == null ? "USER" : this.role; // 회원가입시 기본값으로 USER설정
        super.prePersist();
    }

    public MemberDTO entityToDTO() { // DB에서 정보를 조회해서 담은 entity를 dto로 변환
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberNo(this.memberNo);
        memberDTO.setMemberId(this.memberId);
        memberDTO.setPasswd(this.passwd);
        memberDTO.setSummonerName(this.summonerName);
        memberDTO.setIsDel(this.isDel);
        memberDTO.setRole(this.role);
        memberDTO.setSocial(this.social);

        return memberDTO;
    }

}


