package com.pm.test.j2ee.services;

import java.util.Collection;
import java.util.Map;

public interface GenericCRUD<T> {

	T saveOrUpdate(T entity);

	void remove(T entity);

	long count();

	long count(Map<String, String> filters);

	T get(String key);

	Collection<T> get();

	Collection<T> get(Map<String, String> filters, Integer offset, Integer limit);

}