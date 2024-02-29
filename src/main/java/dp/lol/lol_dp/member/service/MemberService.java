package dp.lol.lol_dp.member.service;


import dp.lol.lol_dp.member.dto.MemberDTO;

public interface MemberService {

    void signUp(MemberDTO memberDTO);
    MemberDTO getByMemberId(String memberId);
    void modifyMemberInfo(MemberDTO memberDTO);


}
