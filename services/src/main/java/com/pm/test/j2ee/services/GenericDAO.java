package com.pm.test.j2ee.services;

import java.util.Collection;
import java.util.Map;

public interface GenericDAO<T> {

	public static final String DB_HOST = "localhost";
	public static final int DB_PORT = 27017;
	public static final String DB_NAME = "test";

	T create(T entity);

	T update(T entity);

	void remove(T entity);

	long count();

	long count(Map<String, String> filters);

	T get(String key);

	Collection<T> get();

	Collection<T> get(Map<String, String> filters, Integer offset, Integer limit, String orderBy, OrderType orderType);

	public enum OrderType {
		ASC,
		DESC;
	}
}