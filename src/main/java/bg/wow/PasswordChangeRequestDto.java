package bg.wow;

import javax.validation.constraints.NotBlank;

public class PasswordChangeRequestDto {
	@NotBlank
	private String accountName;
	
	@NotBlank
	private String email;
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
