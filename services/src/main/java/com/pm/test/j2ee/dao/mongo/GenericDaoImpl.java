package com.pm.test.j2ee.dao.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.pm.test.j2ee.dao.GenericDAO;
import com.pm.test.j2ee.models.MongoEntity;

public abstract class GenericDaoImpl<T extends MongoEntity> implements GenericDAO<T> {
	protected DBCollection table;

	public GenericDaoImpl(Class<T> entityClass, String tableName) throws UnknownHostException {
		this.table = new MongoClient(DB_HOST, DB_PORT).getDB(DB_NAME).getCollection(tableName);
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
	public T get(String key) {
		return convertFromDBO(table.find(createIdQuery(key)).one());
	}

	@Override
	public Collection<T> get() {
		return getList(table.find());
	}

	@Override
	public Collection<T> get(Integer offset, Integer limit, String orderBy, OrderType orderType) {
		return getList(table.find(), offset, limit, orderBy, orderType);
	}

	protected DBObject createIdQuery(String key) {
		return new BasicDBObject("_id", new ObjectId(key));
	}

	protected List<T> getList(DBCursor cursor, Integer offset, Integer limit, String orderBy, OrderType orderType) {
		if (offset != null)
			cursor.skip(offset);
		if (limit != null)
			cursor.limit(limit);

		if (StringUtils.isNotBlank(orderBy))
			cursor.sort(new BasicDBObject(orderBy, OrderType.DESC.equals(orderType) ? -1 : 1));

		return getList(cursor);
	}

	protected List<T> getList(DBCursor cursor) {
		List<T> result = new ArrayList<>();
		for (DBObject dbo : cursor)
			result.add(convertFromDBO(dbo));

		cursor.close();
		return result;
	}

	protected abstract DBObject convertToDBO(T entity);

	protected abstract T convertFromDBO(DBObject one);

}
