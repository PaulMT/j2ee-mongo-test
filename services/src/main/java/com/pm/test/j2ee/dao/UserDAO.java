package com.pm.test.j2ee.dao;

import javax.ejb.Local;

import com.pm.test.j2ee.models.User;
import com.pm.test.j2ee.services.GenericDAO;

@Local
public interface UserDAO extends GenericDAO<User> {
	User getByLogin(String login);
}
