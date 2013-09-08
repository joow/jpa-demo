package org.jpa.demo;

import org.jpa.demo.domain.Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.concurrent.ThreadLocalRandom;

public class InsertThread implements Runnable {
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private final EntityManager entityManager;

    private final int nbEntitiesToCreate;

    public InsertThread(EntityManagerFactory entityManagerFactory, int nbEntitiesToCreate) {
        this.entityManager = EntityManagers.create(entityManagerFactory);
        this.nbEntitiesToCreate = nbEntitiesToCreate;
    }

    @Override
    public void run() {
        EntityManagers.beginTransaction(entityManager);

        for (int i = 0; i < nbEntitiesToCreate; i++) {
            final Entity entity = new Entity(RANDOM.nextInt());
            entityManager.persist(entity);
        }

        EntityManagers.commitTransaction(entityManager);
        EntityManagers.close(entityManager);
    }
}
