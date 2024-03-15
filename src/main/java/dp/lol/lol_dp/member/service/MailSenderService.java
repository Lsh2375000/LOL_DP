package dp.lol.lol_dp.member.service;

public interface MailSenderService {
    boolean sendMailByAddMember(String mailTo) throws Exception;

    String getConfirmKey();
}
