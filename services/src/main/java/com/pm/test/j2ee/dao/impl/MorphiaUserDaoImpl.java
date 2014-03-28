package com.pm.test.j2ee.dao.impl;

import java.net.UnknownHostException;
import java.util.regex.Pattern;

import javax.ejb.Singleton;

import org.mongodb.morphia.query.Query;

import com.pm.test.j2ee.dao.UserDAO;
import com.pm.test.j2ee.models.User;

//@Singleton
public class MorphiaUserDaoImpl extends MorphiaGenericDaoImpl<User> implements UserDAO {

	public MorphiaUserDaoImpl() throws UnknownHostException {
		super(User.class);
	}

	@Override
	public User getByLogin(String login) {
		Pattern regExp = Pattern.compile(login + ".*", Pattern.CASE_INSENSITIVE);
		Query<User> result = getDatastore().find(getEntityClass()).filter("login", regExp);
		return result.get();
	}
}
