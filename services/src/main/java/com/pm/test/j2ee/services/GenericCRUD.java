package com.pm.test.j2ee.services;

import java.util.Collection;

public interface GenericCRUD<T> {

	T create(T entity);

	T get(String key);

	T update(T entity);

	void deleteEntity(T entity);

	Collection<T> getAll();

}