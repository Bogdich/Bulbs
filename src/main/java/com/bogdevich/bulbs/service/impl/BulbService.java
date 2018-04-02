package com.bogdevich.bulbs.service.impl;

import com.bogdevich.bulbs.model.Bulb;
import com.bogdevich.bulbs.repository.IBulbRepository;
import com.bogdevich.bulbs.service.IBulbService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
        Assert.notNull(id, "Id is empty");
        return bulbRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<Bulb> update(final Bulb bulb, Long id) {
        Assert.notNull(bulb, "Bulb object is empty");
        Assert.notNull(id, "Id is empty");
        Assert.isTrue(id.equals(bulb.getId()), "Request attribute id value is not equal to bulb id");
        return bulbRepository
                .findById(id)
                .map(bulb1 -> {
                    bulb1.setActive(bulb.isActive());
                    return bulb1;
                });
    }

    @Override
    public Optional<Bulb> delete(final Long id) {
        return null;
    }
}
