package rw.ac.auca.shoemarketplace.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JPAUtil {

    private static final EntityManagerFactory FACTORY = buildFactory();

    private static EntityManagerFactory buildFactory() {
        Properties db = loadDbProperties();

        Map<String, String> overrides = new HashMap<>();
        overrides.put("jakarta.persistence.jdbc.url", db.getProperty("db.url"));
        overrides.put("jakarta.persistence.jdbc.user", db.getProperty("db.user"));
        overrides.put("jakarta.persistence.jdbc.password", db.getProperty("db.password"));
        overrides.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");

        return Persistence.createEntityManagerFactory("shoeMarketPU", overrides);
    }

    private static Properties loadDbProperties() {
        Properties props = new Properties();
        try (InputStream in = JPAUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new RuntimeException(
                        "db.properties not found on the classpath. Copy " +
                        "src/main/resources/db.properties.example to " +
                        "src/main/resources/db.properties and fill in your local database credentials."
                );
            }
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load db.properties", e);
        }
        return props;
    }

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}
