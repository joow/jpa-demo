package org.jpa.demo.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@javax.persistence.Entity
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    private final int randomValue;

    public Entity() {
        this(0);
    }

    public Entity(int randomValue) {
        this.randomValue = randomValue;
    }
}
