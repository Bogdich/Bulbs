package com.bogdevich.bulbs.service.impl;

import com.bogdevich.bulbs.model.Bulb;
import com.bogdevich.bulbs.repository.IBulbRepository;
import com.bogdevich.bulbs.service.IBulbService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BulbService implements IBulbService {

    private final IBulbRepository bulbRepository;

    public BulbService(IBulbRepository bulbRepository) {
        this.bulbRepository = bulbRepository;
    }

    @Override
    public List<Bulb> findAll() {
        return bulbRepository.findAll();
    }

    @Override
    public Optional<Bulb> create(final Bulb bulb) {
        return null;
    }

    @Override
    public Optional<Bulb> findOne(final Long id) {
        return null;
    }

    @Override
    public Optional<Bulb> update(final Bulb bulb, Long id) {
        return null;
    }

    @Override
    public Optional<Bulb> delete(final Long id) {
        return null;
    }
}
