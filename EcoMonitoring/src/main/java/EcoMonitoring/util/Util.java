package EcoMonitoring.util;

import EcoMonitoring.models.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Util {
    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            org.hibernate.cfg.Configuration configuration = new Configuration()
                    .addAnnotatedClass(Substances.class)
                    .addAnnotatedClass(SubstanceHistory.class)
                    .addAnnotatedClass(Objects.class)
                    .addAnnotatedClass(TaxesHistory.class)
                    .addAnnotatedClass(Taxes.class)
                    .addAnnotatedClass(HealthRisk.class)
                    .addAnnotatedClass(HealthRiskHistory.class)
                    .addAnnotatedClass(Compensations.class)
                    .addAnnotatedClass(CompensationsHistory.class)
                    .addAnnotatedClass(CrisisDamageHistory.class);
            configuration.configure("hibernate.cfg.xml");
            System.out.println("Hibernate Configuration loaded");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate serviceRegistry created");

            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }

}
