package com.pm.test.j2ee.dao.mongo;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.pm.test.j2ee.dao.UserDAO;
import com.pm.test.j2ee.models.User;

public class UserDaoImpl extends GenericDaoImpl<User> implements UserDAO {

	public UserDaoImpl() throws UnknownHostException {
		super(User.class, "users");
	}

	@Override
	public long count(Map<String, String> filters) {
		return table.count(createQuery(filters));
	}

	@Override
	public User getByLogin(String login) {
		Pattern regExp = Pattern.compile(login + ".*", Pattern.CASE_INSENSITIVE);
		DBCursor cursor = table.find(new BasicDBObject("login", regExp));
		return convertFromDBO(cursor.one());
	}

	@Override
	public Collection<User> get(Map<String, String> filters) {
		return getList(table.find(createQuery(filters)));
	}

	@Override
	public Collection<User> get(Map<String, String> filters, Integer offset, Integer limit, String orderBy,
			com.pm.test.j2ee.dao.GenericDAO.OrderType orderType) {
		DBCursor cursor = table.find(createQuery(filters));
		return getList(cursor, offset, limit, orderBy, orderType);
	}

	protected DBObject convertToDBO(User user) {
		BasicDBObjectBuilder dboBuilder = new BasicDBObjectBuilder();
		dboBuilder.add("login", user.getLogin());
		dboBuilder.add("password", user.getPassword());
		dboBuilder.add("email", user.getEmail());
		dboBuilder.add("firstName", user.getFirstName());
		dboBuilder.add("lastName", user.getLastName());

		if (StringUtils.isNotBlank(user.getUid()))
			dboBuilder.add("_id", user.getKey());

		return dboBuilder.get();
	}

	protected User convertFromDBO(DBObject dbo) {
		User user = new User();
		user.setUid(dbo.get("_id").toString());
		user.setLogin((String) dbo.get("login"));
		user.setPassword((String) dbo.get("password"));
		user.setEmail((String) dbo.get("email"));
		user.setFirstName((String) dbo.get("firstName"));
		user.setLastName((String) dbo.get("lastName"));
		return user;
	}

	private DBObject createQuery(Map<String, String> filters) {
		BasicDBObject query = new BasicDBObject();

		if (filters != null && !filters.isEmpty())
			for (Entry<String, String> filter : filters.entrySet())
				query.append(filter.getKey(), new BasicDBObject("$regex", filter.getValue()));

		return query;
	}

}
