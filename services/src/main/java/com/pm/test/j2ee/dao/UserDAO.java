package com.pm.test.j2ee.dao;

import java.util.Collection;
import java.util.Map;

import javax.ejb.Local;

import com.pm.test.j2ee.models.User;

@Local
public interface UserDAO extends GenericDAO<User> {

	long count(Map<String, String> filters);

	User getByLogin(String login);

	Collection<User> get(Map<String, String> filters);

	Collection<User> get(Map<String, String> filters, Integer offset, Integer limit, String orderBy, OrderType orderType);

}
