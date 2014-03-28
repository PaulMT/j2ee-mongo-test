package com.pm.test.j2ee.dao.morphia;

import java.net.UnknownHostException;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;
import com.pm.test.j2ee.dao.GenericDAO;
import com.pm.test.j2ee.models.MongoEntity;

public class GenericDaoImpl<T extends MongoEntity> extends BasicDAO<T, String> implements GenericDAO<T> {

	public GenericDaoImpl(Class<T> entityClass) throws UnknownHostException {
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
	public Collection<T> get(Integer offset, Integer limit, String orderBy, OrderType orderType) {
		Query<T> query = createQuery(offset, limit, orderBy, orderType);
		return query.asList();
	}

	protected Query<T> createQuery(Integer offset, Integer limit, String orderBy, OrderType orderType) {
		Query<T> query = createQuery();

		if (offset != null)
			query.offset(offset);
		if (limit != null)
			query.limit(limit);

		if (StringUtils.isNotBlank(orderBy))
			query.order(OrderType.DESC.equals(orderType) ? "-" + orderBy : orderBy);

		return query;
	}
}
