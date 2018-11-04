package bg.wow.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.wow.PasswordChangeRequestDto;
import bg.wow.exception.EmailSendingException;
import bg.wow.exception.TooFrequentRequestException;
import bg.wow.service.WowAccountService;
import bg.wow.utils.GmailClient;

@RestController
@RequestMapping("reset-password")
public class PasswordResetController {	
	private static long MILISECONDS_IN_SECOND = 1000;
	
	@Autowired
	private GmailClient gmailClient;
	
	@Autowired
	@Value("${minSecondsBetweenPasswordChangeRequests}")
	private long minSecondsBetweenPasswordChangeRequests;

	@Autowired
	private WowAccountService wowAccountService;
	
	private HashMap<String, Long> ipsAndRequestMiliseconds = new HashMap<>();

	@GetMapping
	public void changePassword(@Valid PasswordChangeRequestDto pwdChangeReqDto, HttpServletRequest request)
			throws Exception {
		String clientIp = getClientIp(request);
		verifyUserCanMakeRequest(clientIp);
		String email = pwdChangeReqDto.getEmail();
		String accountName = pwdChangeReqDto.getAccountName();

		String newUnhashedPassword = wowAccountService.changePassword(accountName, email);

		sendEmailWithNewPassword(email, newUnhashedPassword, accountName);
		
		ipsAndRequestMiliseconds.put(clientIp, System.currentTimeMillis());
	}

	private void sendEmailWithNewPassword(String email, String newPassword, String accountName) {
		String emailBody = buildMessage(newPassword, accountName);

		String[] to = { email };
		String subject = "wow-bg.com password change";

		try {
			gmailClient.sendFromGMail(to, subject, emailBody);
		} catch (MessagingException e) {
			throw new EmailSendingException();
		}
	}

	private String buildMessage(String newPass, String userName) {
		StringBuilder sb = new StringBuilder();

		String newLine = "\n";
		sb.append(String.format("Здравейте, %s,", userName)).append(newLine).append(newLine)
				.append(String.format("Новата Ви парола за достъп е: %s", newPass)).append(newLine).append(newLine)
				.append("Препоръчваме Ви, веднага след като влезете на линия в играта, незабавно да смените паролата, която току-що Ви предоставихме. ")
				.append(newLine).append("За да направите това, трябва да напишете следната команда Ingame:")
				.append(newLine).append(".account password СтараПарола НоваПарола НоваПарола").append(newLine)
				.append(newLine).append("Поздрави,").append(newLine).append("Екипът на WoW-BG.com");

		return sb.toString();
	}

	private String getClientIp(HttpServletRequest request) {

		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}

		return remoteAddr;
	}
	
	private void verifyUserCanMakeRequest(String clientIp) {
		List<String> ipsToRemoveFromBlockedList = new ArrayList<>();
		
		for (Entry<String, Long> ipAndRequestTime : ipsAndRequestMiliseconds.entrySet()) {
			long timePassedSinceLastRequest = System.currentTimeMillis() - ipAndRequestTime.getValue();
			boolean shouldBeAbleToRequestAgain = timePassedSinceLastRequest > (minSecondsBetweenPasswordChangeRequests * MILISECONDS_IN_SECOND);
			if (shouldBeAbleToRequestAgain) {
				ipsToRemoveFromBlockedList.add(ipAndRequestTime.getKey());
			}
		}
		
		for (String ipFromBlockedList : ipsToRemoveFromBlockedList) {
			ipsAndRequestMiliseconds.remove(ipFromBlockedList);
		}
		
		if (ipsAndRequestMiliseconds.containsKey(clientIp)) {
			throw new TooFrequentRequestException();
		} 
	}

}
