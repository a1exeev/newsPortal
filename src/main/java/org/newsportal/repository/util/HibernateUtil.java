package org.newsportal.repository.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory () {
        synchronized (HibernateUtil.class) {
            if (sessionFactory == null) {
                sessionFactory = buildSessionFactory();
            }
            return sessionFactory;
        }
    }
}
