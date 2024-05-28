package app.config;

import app.domain.contact.Contact;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateUtil {

    private static final Logger LOGGER =
            Logger.getLogger(HibernateUtil.class.getName());

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = getConfiguration();

                configuration.addAnnotatedClass(Contact.class);

                ServiceRegistry serviceRegistry =
                        new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration
                        .buildSessionFactory(serviceRegistry);
            } catch (Exception exception) {
                LOGGER.log(Level.WARNING, exception.getMessage(), exception);
            }
        }
        return sessionFactory;
    }

    private static Configuration getConfiguration() {

        Configuration config = new Configuration();
        Properties props = new Properties();

        try {
            props.load(HibernateUtil.class.getResourceAsStream("/db/db.properties"));
            props.put(Environment.DRIVER, props.getProperty("dbDriver"));
            props.put(Environment.URL, props.getProperty("dbUrl"));
            props.put(Environment.USER, props.getProperty("dbUser"));
            props.put(Environment.PASS, props.getProperty("dbPass"));
            props.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            config.setProperties(props);

            return config;

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
