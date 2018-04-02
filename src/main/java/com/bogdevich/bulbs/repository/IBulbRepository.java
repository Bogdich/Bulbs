package com.bogdevich.bulbs.repository;

import com.bogdevich.bulbs.model.Bulb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBulbRepository extends JpaRepository<Bulb, Long> {
}
