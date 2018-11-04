package bg.wow.db.finder;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bg.wow.db.GenericGateway;
import bg.wow.db.entity.WowAccount;
import bg.wow.exception.NoSuchAccountException;

@Repository
public class WowAccountFinder {
	@Autowired
	private GenericGateway gateway;

	public WowAccount findAccountByEmailAndAccountName(String email, String accountName) {
		String strQuery = "from WowAccount where email = :email and username = :accountName";
		
		Query<WowAccount> query = gateway.createQuery(strQuery, WowAccount.class);
		query.setParameter("email", email);
		query.setParameter("accountName", accountName);

		WowAccount wowAccount = null;
			
		//TODO figure out why catch isn't working
		try {
			 wowAccount = query.uniqueResult();
		} finally {
			if (wowAccount == null) {
				throw new NoSuchAccountException();
			}
		}
		
		return wowAccount;
	}
}
