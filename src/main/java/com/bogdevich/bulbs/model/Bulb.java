package com.bogdevich.bulbs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bulb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean active;

    public Bulb() {
    }

    public Bulb(boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bulb{");
        sb.append("id=").append(id);
        sb.append(", active=").append(active);
        sb.append('}');
        return sb.toString();
    }
}
