package com.pm.test.j2ee.dao.impl;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;
import com.pm.test.j2ee.services.GenericCRUD;

public class GenericDaoImpl<T> extends BasicDAO<T, String> implements GenericCRUD<T> {
	public static final String DB_HOST = "localhost";
	public static final int DB_PORT = 27017;
	public static final String DB_NAME = "test";

	public GenericDaoImpl(Class<T> entityClass) throws UnknownHostException {
		super(entityClass, new MongoClient(DB_HOST, DB_PORT), new Morphia(), DB_NAME);
	}

	@Override
	public T saveOrUpdate(T entity) {
		save(entity);
		return entity;
	}

	@Override
	public void remove(T entity) {
		delete(entity);
	}

	@Override
	public Collection<T> get() {
		return find().asList();
	}

	@Override
	public T get(String key) {
		return getDatastore().get(getEntityClass(), new ObjectId(key));
	}

	@Override
	public Collection<T> get(Map<String, String> filters, Integer offset, Integer limit) {
		Query<T> query = createQuery(filters);

		if (limit != null)
			query.limit(limit);
		if (offset != null)
			query.offset(offset);

		return query.asList();
	}

	@Override
	public long count(Map<String, String> filters) {
		Query<T> query = createQuery(filters);
		return query.countAll();
	}

	private Query<T> createQuery(Map<String, String> filters) {
		Query<T> query = createQuery();

		if (filters != null && !filters.isEmpty())
			for (Entry<String, String> filter : filters.entrySet())
				query.and(query.criteria(filter.getKey()).containsIgnoreCase(filter.getValue()));

		return query;
	}
}
