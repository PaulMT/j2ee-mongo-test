package com.pm.test.j2ee.dao.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.pm.test.j2ee.models.MongoEntity;
import com.pm.test.j2ee.services.GenericDAO;

public abstract class ClearGenericDaoImpl<T extends MongoEntity> implements GenericDAO<T> {
	protected Mongo mongo;
	protected DBCollection table;

	public ClearGenericDaoImpl(Class<T> entityClass, String tableName) throws UnknownHostException {
		this.mongo = new MongoClient(DB_HOST, DB_PORT);
		this.table = mongo.getDB(DB_NAME).getCollection(tableName);
	}

	@Override
	public T create(T entity) {
		DBObject dbo = convertToDBO(entity);
		table.insert(dbo);
		return convertFromDBO(dbo);
	}

	@Override
	public T update(T entity) {
		DBObject dbo = convertToDBO(entity);
		table.update(createIdQuery(entity.getUid()), dbo);
		return convertFromDBO(dbo);
	}

	@Override
	public void remove(T entity) {
		table.remove(createIdQuery(entity.getUid()));
	}

	@Override
	public long count() {
		return table.count();
	}

	@Override
	public long count(Map<String, String> filters) {
		return table.count(createQuery(filters));
	}

	@Override
	public T get(String key) {
		return convertFromDBO(table.find(createIdQuery(key)).one());
	}

	@Override
	public Collection<T> get() {
		return get(null, null, null, null, null);
	}

	@Override
	public Collection<T> get(Map<String, String> filters, Integer offset, Integer limit, String orderBy,
			OrderType orderType) {
		List<T> result = new ArrayList<>();

		try (DBCursor cursor = table.find(createQuery(filters))) {
			if (offset != null)
				cursor.skip(offset);
			if (limit != null)
				cursor.limit(limit);
			if (StringUtils.isNotBlank(orderBy))
				cursor.sort(new BasicDBObject(orderBy, OrderType.DESC.equals(orderType) ? -1 : 1));

			for (DBObject dbo : cursor)
				result.add(convertFromDBO(dbo));
		}

		return result;
	}

	private DBObject createIdQuery(String key) {
		return new BasicDBObject("_id", new ObjectId(key));
	}

	private DBObject createQuery(Map<String, String> filters) {
		BasicDBObject query = new BasicDBObject();

		if (filters != null && !filters.isEmpty())
			for (Entry<String, String> filter : filters.entrySet())
				query.append(filter.getKey(), new BasicDBObject("$regex", filter.getValue()));

		return query;
	}

	protected abstract DBObject convertToDBO(T entity);

	protected abstract T convertFromDBO(DBObject one);

}
