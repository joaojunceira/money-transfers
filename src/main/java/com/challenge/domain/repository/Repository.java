package com.challenge.domain.repository;


public interface Repository<K, V> {

  K create(V entity);

  V findById(K key);

  Iterable<V> findAll();

  Integer delete(K key);

  Integer update(V entity);
}
