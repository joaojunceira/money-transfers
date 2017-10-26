package com.challenge.domain.repository;

import java.util.List;

public interface Repository<K, V> {

  K create(V entity);

  V findById(K key);

  List<V> findAll();

  Integer delete(K key);

  Integer update(V entity);
}
