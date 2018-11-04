package bg.wow.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GenericGateway {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void save(Object object) {
		sessionFactory.getCurrentSession().save(object);
	}

	public <T> Query<T> createQuery(String queryString, Class<T> clazz) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery(queryString, clazz);
	}
}
