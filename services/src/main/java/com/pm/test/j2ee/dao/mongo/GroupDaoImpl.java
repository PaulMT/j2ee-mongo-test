package com.pm.test.j2ee.dao.mongo;

import java.net.UnknownHostException;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.pm.test.j2ee.dao.GroupDAO;
import com.pm.test.j2ee.models.Group;

public class GroupDaoImpl extends GenericDaoImpl<Group> implements GroupDAO {

	public GroupDaoImpl() throws UnknownHostException {
		super(Group.class, "groups");
	}

	@Override
	public long count(String name) {
		return table.count(createNameQuery(name));
	}

	@Override
	public Collection<Group> getByName(String name) {
		return getList(table.find(createNameQuery(name)));
	}

	@Override
	public Collection<Group> getByName(String name, Integer offset, Integer limit, String orderBy,
			com.pm.test.j2ee.dao.GenericDAO.OrderType orderType) {
		DBCursor cursor = table.find(createNameQuery(name));
		return getList(cursor, offset, limit, orderBy, orderType);
	}

	@Override
	protected DBObject convertToDBO(Group group) {
		BasicDBObjectBuilder dboBuilder = new BasicDBObjectBuilder();
		dboBuilder.add("name", group.getName());

		if (StringUtils.isNotBlank(group.getUid()))
			dboBuilder.add("_id", group.getKey());

		return dboBuilder.get();
	}

	@Override
	protected Group convertFromDBO(DBObject dbo) {
		Group group = new Group();
		group.setUid(dbo.get("_id").toString());
		group.setName((String) dbo.get("name"));
		return group;
	}

	private DBObject createNameQuery(String name) {
		return new BasicDBObject("name", new BasicDBObject("$regex", name));
	}

}
