package bg.wow.service;

import java.security.SecureRandom;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bg.wow.db.entity.WowAccount;
import bg.wow.db.finder.WowAccountFinder;

@Component
public class WowAccountService {
	@Autowired
	private WowAccountFinder wowAccountFinder;

	@Transactional
	public String changePassword(String accountName, String email) {
		WowAccount wowAccount = wowAccountFinder.findAccountByEmailAndAccountName(email, accountName);
		String newPassword = getAlphaNumeric(8);
		String passwordConcat = accountName + ":" + newPassword;
		passwordConcat = passwordConcat.toUpperCase();
		String sha1Pass = DigestUtils.sha1Hex(passwordConcat);
		wowAccount.setShaPassHash(sha1Pass);
		wowAccount.setS("0");
		wowAccount.setV("0");
		
		return newPassword;
	}
	
	public String getAlphaNumeric(int len) {

	    char[] ch = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	    char[] c = new char[len];
	    SecureRandom random = new SecureRandom();
	    for (int i = 0; i < len; i++) {
	      c[i] = ch[random.nextInt(ch.length)];
	    }

	    return new String(c);
	  }
}
