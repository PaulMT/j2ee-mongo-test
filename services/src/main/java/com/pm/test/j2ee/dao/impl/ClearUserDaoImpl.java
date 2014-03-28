package com.pm.test.j2ee.dao.impl;

import java.net.UnknownHostException;

import javax.ejb.Singleton;

import org.apache.commons.lang.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.pm.test.j2ee.dao.UserDAO;
import com.pm.test.j2ee.models.User;

@Singleton
public class ClearUserDaoImpl extends ClearGenericDaoImpl<User> implements UserDAO {

	public ClearUserDaoImpl() throws UnknownHostException {
		super(User.class, "users");
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

	@Override
	public User getByLogin(String login) {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("login", login);

		DBCursor cursor = table.find(searchQuery);
		return convertFromDBO(cursor.next());
	}

}
