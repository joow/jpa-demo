package org.jpa.demo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Objects;

/**
 * Static methods to work with entity managers.
 */
public class EntityManagers {
    private EntityManagers() {}

    public static EntityManager create(EntityManagerFactory entityManagerFactory) {
        Objects.requireNonNull(entityManagerFactory, "The entity manager factory couldn't be null.");
        return entityManagerFactory.createEntityManager();
    }

    public static void beginTransaction(EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "The entity manager couldn't be null.");
        entityManager.getTransaction().begin();
    }

    public static void commitTransaction(EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "The entity manager couldn't be null.");
        entityManager.getTransaction().commit();
    }

    public static void close(EntityManager entityManager) {
        if (entityManager != null) {
            entityManager.close();
        }
    }
}
