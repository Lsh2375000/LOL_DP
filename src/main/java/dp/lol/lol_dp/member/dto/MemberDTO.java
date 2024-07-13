package dp.lol.lol_dp.member.dto;

import dp.lol.lol_dp.member.domain.MemberEntity;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Log4j2
public class MemberDTO implements UserDetails, OAuth2User {

    private Long memberNo;
    private String memberId;
    private String passwd;
    private String summonerName;
    private String tagLine;
    private Boolean isDel;
    private String role;
    private Boolean social;

    private Map<String, Object> props; //  소셜 로그인 정보
    private List<SimpleGrantedAuthority> authorities;

    public MemberDTO(Long memberNo, String memberId, String passwd, String summonerName, String tagLine, Boolean isDel,
                     String role, Boolean social, List<SimpleGrantedAuthority> authorities) {
        // 로그인시 DB에서 회원 정보 가져와서 세션에 값 넣어주기위한 메소드
        log.info("로그인 정보 가져오기");
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.passwd = passwd;
        this.summonerName = summonerName;
        this.tagLine = tagLine;
        this.isDel = isDel;
        this.role = role;
        this.social = social;
        this.authorities = authorities;
    }


    public MemberEntity dtoToEntity() { // 회원가입시 입력받은 정보를 entity로 변환
        MemberEntity member = MemberEntity.builder()
                .memberNo(this.memberNo)
                .memberId(this.memberId)
                .passwd(this.passwd)
                .summonerName(this.summonerName)
                .tagLine(this.tagLine)
                .isDel(false)
                .role(this.role)
                .social(this.social)
                .build();
        return member;
    }


    @Override // 비밀번호 담기
    public String getPassword() {
        return passwd;
    }

    @Override // 아이디 담기
    public String getUsername() {
        return memberId;
    }

    @Override // 계정이 만료되지 않았는지 담아두기 위해 (true : 만료안됨)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정이 잠겨있는지 아닌지 담아두기 위해 (true : 안잠김)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override // 계정의 비밀번호가 만료되지 않았는지 담아두기 위해 (true : 만료안됨)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 계정이 활성화 되어 있는지 담아두기 위해 (true : 활성화됨)
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.getProps();
    }

    @Override
    public String getName() {
        return this.memberId;
    }
}
