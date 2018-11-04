package bg.wow;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import bg.wow.utils.GmailClient;

@Configuration
public class AppConfig {		
	
/*	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("admin");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/trinity_auth?useSSL=false");
		
		return dataSource;
	}*/
	
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	@Value("${gmail.username}")
	private String gmailUsername;
	
	@Autowired 
	@Value("${gmail.password}")
	private String gmailPassword;

	@Bean
	@Primary
	public SessionFactory getSessionFactory() {
	    if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
	        throw new NullPointerException("factory is not a hibernate factory");
	    }
	    return entityManagerFactory.unwrap(SessionFactory.class);
	}
	
	@Bean
	public GmailClient getGmailClient() {
		GmailClient gmailClient = new GmailClient(gmailUsername, gmailPassword);
		return gmailClient;
	}

}
