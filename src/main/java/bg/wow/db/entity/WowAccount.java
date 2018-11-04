package bg.wow.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "trinity_auth", name = "account")
public class WowAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "username")
	private String username;

	@Column(name = "sha_pass_hash")
	private String shaPassHash;

	@Column(name = "email")
	private String email;

	@Column(name = "v")
	private String v;

	@Column(name = "s")
	private String s;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getShaPassHash() {
		return shaPassHash;
	}

	public void setShaPassHash(String shaPassHash) {
		this.shaPassHash = shaPassHash;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}
}
