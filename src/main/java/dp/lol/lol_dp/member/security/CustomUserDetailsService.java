package dp.lol.lol_dp.member.security;

import dp.lol.lol_dp.member.domain.MemberEntity;
import dp.lol.lol_dp.member.dto.MemberDTO;
import dp.lol.lol_dp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("CustomUserDetailsService의 loadUserByUsername().....");
        log.info("입력한 memberId : " + username);

        Optional<MemberEntity> member = memberRepository.findByMemberId(username);
        // 입력한 회원 아이디에 일치하는 데이터를 DB에서 찾아서 MemberEntity에 넣어준다.
        if (member.isEmpty()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        /* Entity에는 없는 authorities 임의로 생성해서 값 넣어서 memberDTO에 저장 */
        // 기본값 USER로 설정
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("USER"));

        // MemberDTO에 값 넣어줌
        MemberEntity resultMember = member.get();
        MemberDTO memberDTO = new MemberDTO(
                resultMember.getMemberNo(),
                resultMember.getMemberId(),
                resultMember.getPasswd(),
                resultMember.getSummonerName(),
                resultMember.getIsDel(),
                resultMember.getRole(),
                resultMember.getSocial(),
                authorities);

        log.info("세션에 저장될 회원 정보 : " + memberDTO);

        return memberDTO;
    }
}
