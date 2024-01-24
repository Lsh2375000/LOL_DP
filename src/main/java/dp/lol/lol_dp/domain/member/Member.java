package dp.lol.lol_dp.domain.member;

import dp.lol.lol_dp.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/*
    @NoArgsConstructor
    Lombok에서 제공해주는 어노테이션으로, 매개변수를 갖지 않는 기본 생성자를 생성해주며,
    일반적으로 access = AccessLevel.PROTECTED 옵션을 선언해 무분별한 객체 생성을 방지한다.
*/
@Entity
/*
    @Entity
    JPA는 객체를 통해 데이터베이스와 상호 작용하기 때문에 @Entity가 선언된 객체와 DB의 테이블과 매핑해준다.
*/
@Table(name = "member")
/*
    @Table
    DB상의 실제 테이블명을 명시하여 엔티티와 데이터베이스 테이블을 매핑한다.
*/
public class Member extends BaseTimeEntity{

    @Id //멤버 변수 id를 데이터베이스 테이블의 기본 키(primary key)로 지정한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*
        기본 키(primary key) 생성 전략을 지정한다.
        MariaDB 또는 MySQL과 같이 PK 자동 증가(auto-increment)를 제공해주는
        DB는 strategy = GenerationType.IDENTITY 옵션을 통해 데이터가 새로 생성되는 시점에 id 값을 자등으로 증가시킬 수 있다.
    */
    @Column(name = "id")
    /*
        Entity 객체의 멤버 변수와 DB 테이블의 컬럼을 매핑한다.
        변수명과 컬럼명이 완전히 일치한 경우에는 생략해도 되지만,
        name 속성을 통해 정확한 컬럼명을 명시해주는 게 좋다.
    */
    private Long id;             // 회원 번호 (PK)

    @Column(name = "login_id")
    private String loginId;      // 로그인 ID

    @Column(name = "password")
    private String password;     // 비밀번호

    @Column(name = "name")
    private String name;         // 이름

    @Column(name = "tier")
    private Tier tier;           // 티어

    @Column(name = "birthday")
    private LocalDate birthday;  // 생년월일

    @Column(name = "delete_yn")
    private Boolean deleteYn;    // 삭제 여부

    @Builder
    /*
       Lombok에서 제공해주는 어노테이션으로, 클래스에 빌더 패턴을 자동으로 생성해 준다.
       Bulider 패턴은 생성자 또는 Bean 패턴 보다 객체 생성을 가독성 있고 편리하게 해주는 디자인 패턴 중 하나
    */
    public Member(String loginId, String password, String name, Tier tier, LocalDate birthday, Boolean deleteYn) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.tier = tier;
        this.birthday = birthday;
        this.deleteYn = deleteYn;
    }

}
