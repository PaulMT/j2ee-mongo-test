package com.pm.test.j2ee.dao.impl;

import javax.ejb.Singleton;

import com.pm.test.j2ee.dao.UserDAO;
import com.pm.test.j2ee.models.User;

@Singleton
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDAO {

	public UserDaoImpl() {
		super(User.class);
	}
}
