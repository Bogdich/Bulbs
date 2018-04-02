package com.bogdevich.bulbs.service;

import java.util.List;
import java.util.Optional;

public interface IBaseService<T, V> {
    Optional<T> create(final T t);
    List<T> findAll();
    Optional<T> findOne(final V id);
    Optional<T> update(final T t, final V id);
    Optional<T> delete(final V id);
}
