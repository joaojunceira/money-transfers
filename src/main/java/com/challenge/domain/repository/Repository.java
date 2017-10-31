package com.challenge.domain.repository;


import java.util.Optional;

public interface Repository<K, V> {

  K create(V entity);

  Optional<V> findById(K key);

  Optional<Iterable<V>> findAll();

  Integer delete(K key);

  Integer update(V entity);
}
