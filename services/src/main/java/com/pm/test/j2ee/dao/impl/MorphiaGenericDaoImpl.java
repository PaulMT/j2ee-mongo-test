package com.pm.test.j2ee.dao.impl;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;
import com.pm.test.j2ee.models.MongoEntity;
import com.pm.test.j2ee.services.GenericDAO;

public class MorphiaGenericDaoImpl<T extends MongoEntity> extends BasicDAO<T, String> implements GenericDAO<T> {

	public MorphiaGenericDaoImpl(Class<T> entityClass) throws UnknownHostException {
		super(entityClass, new MongoClient(DB_HOST, DB_PORT), new Morphia(), DB_NAME);
	}

	@Override
	public T create(T entity) {
		save(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
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
	public Collection<T> get(Map<String, String> filters, Integer offset, Integer limit, String orderBy,
			OrderType orderType) {
		Query<T> query = createQuery(filters);

		if (limit != null)
			query.limit(limit);
		if (offset != null)
			query.offset(offset);

		if (StringUtils.isNotBlank(orderBy))
			query.order(OrderType.DESC.equals(orderType) ? "-" + orderBy : orderBy);

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
