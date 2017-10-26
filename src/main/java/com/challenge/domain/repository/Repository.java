package com.challenge.domain.repository;

public interface Repository<K,V> {
  K create(V entity);
  V findById(K key);
  Integer delete(K key);
  Integer update(V entity);
}
