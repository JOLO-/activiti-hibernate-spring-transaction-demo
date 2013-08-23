package name.krestjaninoff.activiti.hello.core;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Engine {

    private ClassPathXmlApplicationContext applicationContext;
    private SessionFactory hibernateSessionFactory;

    public Engine() {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        hibernateSessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
    }

    public ClassPathXmlApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public SessionFactory getHibernateSessionFactory() {
        return hibernateSessionFactory;
    }

    public Session getHibernateSession() throws HibernateException {
        return hibernateSessionFactory.getCurrentSession();
    }
}
