package com.pm.test.j2ee.dao;

import java.util.Collection;

import javax.ejb.Local;

import com.pm.test.j2ee.models.Group;

@Local
public interface GroupDAO extends GenericDAO<Group> {

	long count(String name);

	Collection<Group> getByName(String name);

	Collection<Group> getByName(String name, Integer offset, Integer limit, String orderBy, OrderType orderType);

}
