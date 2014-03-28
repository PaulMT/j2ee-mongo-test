package com.pm.test.j2ee.dao.morphia;

import java.net.UnknownHostException;
import java.util.Collection;

import javax.ejb.Singleton;

import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.query.Query;

import com.pm.test.j2ee.dao.GroupDAO;
import com.pm.test.j2ee.models.Group;

@Singleton
public class GroupDaoImpl extends GenericDaoImpl<Group> implements GroupDAO {

	public GroupDaoImpl() throws UnknownHostException {
		super(Group.class);
	}

	@Override
	public long count(String name) {
		Query<Group> query = createQuery();
		if (StringUtils.isNotBlank(name))
			query.field("name").contains(name);
		return query.countAll();
	}

	@Override
	public Collection<Group> getByName(String name) {
		Query<Group> query = createQuery();
		if (StringUtils.isNotBlank(name))
			query.field("name").contains(name);
		return query.asList();
	}

	@Override
	public Collection<Group> getByName(String name, Integer offset, Integer limit, String orderBy,
			com.pm.test.j2ee.dao.GenericDAO.OrderType orderType) {
		Query<Group> query = createQuery(offset, limit, orderBy, orderType);
		if (StringUtils.isNotBlank(name))
			query.field("name").contains(name);
		return query.asList();
	}

}
