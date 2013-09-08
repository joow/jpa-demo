package org.jpa.demo;

import org.testng.Assert;
import org.testng.annotations.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bench {
    private static final int NB_ENTITIES_TO_CREATE = 5000;

    private static final int NB_THREADS = 5;

    private EntityManagerFactory entityManagerFactory;

    @BeforeClass
    public void createEntityManager() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa-demo");
    }

    @AfterClass
    private void closeEntityManager() {
        entityManagerFactory.close();
    }

    @Test
    public void bench() {
        final ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < NB_THREADS; i++) {
            executorService.submit(new InsertThread(entityManagerFactory, NB_ENTITIES_TO_CREATE / NB_THREADS));
        }

        executorService.shutdown();
        while (!executorService.isTerminated());

        final EntityManager entityManager = EntityManagers.create(entityManagerFactory);
        EntityManagers.beginTransaction(entityManager);
        final long nbEntities = (long) entityManager.createNativeQuery("SELECT COUNT(*) FROM ENTITY").getSingleResult();
        Assert.assertEquals(nbEntities, NB_ENTITIES_TO_CREATE);
        EntityManagers.commitTransaction(entityManager);
        EntityManagers.close(entityManager);
    }
}
