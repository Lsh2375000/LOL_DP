package dp.lol.lol_dp.member.service;

import dp.lol.lol_dp.member.domain.MemberEntity;
import dp.lol.lol_dp.member.dto.MemberDTO;
import dp.lol.lol_dp.member.repository.MemberRepository;
import dp.lol.lol_dp.member.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public void register(MemberDTO memberDTO) { // 회원 가입
        log.info("회원가입 서비스 메소드");
        log.info("입력한 회원 정보 : " + memberDTO);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));
        // 입력한 비밀번호 암호화
        MemberEntity member = memberDTO.dtoToEntity();
        memberRepository.save(member);
    }

    @Override
    public MemberDTO getByMemberId(String memberId) {
        log.info("회원정보 조회 서비스 메소드");
        log.info("입력한 회원 아이디 : " + memberId);
        Optional<MemberEntity> member = memberRepository.findByMemberId(memberId);
        return member.map(MemberEntity::entityToDTO).orElse(null);
        // MemberEntity entity의 entityToDTO로 값을 MemberDTO로 변환해서 반환, 값이 비어있다면 null값 반환
    }

    @Override
    public void modifyMemberInfo(MemberDTO memberDTO) {
        log.info("회원 정보 수정 서비스 메소드 / 회원 아이디 : " + memberDTO.getMemberId());
        Optional<MemberEntity> member = memberRepository.findByMemberId(memberDTO.getMemberId());
        // 해당 아이디의 데이터를 DB에서 가져와서 Entity객체에 넣어준다.

        MemberDTO modifyMemberDTO = member.map(MemberEntity::entityToDTO).orElse(null);
        // 값을 담은 Entity객체를 DTO로 변환한다.

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        if (modifyMemberDTO != null) {
            if (!memberDTO.getPasswd().equals(modifyMemberDTO.getPasswd())) {
                // 입력한 비밀번호와 DB에 저장된 비밀번호가 다를경우 비밀번호 수정
                modifyMemberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));
                // 입력한 비밀번호 암호화
            }
            modifyMemberDTO.setNickname(memberDTO.getNickname());
            modifyMemberDTO.setRiotId(memberDTO.getRiotId());

            MemberEntity modifyMember = modifyMemberDTO.dtoToEntity();
            // 변경된 DTO를 다시 Entity로 변환해서 저장
            log.info("수정된 회원 정보 : " + modifyMember);
            memberRepository.save(modifyMember);
            sessionUpdate(modifyMemberDTO); // 세션 자동 업데이트
        }

    }

    private void sessionUpdate(MemberDTO memberDTO) {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberDTO.getMemberId());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
