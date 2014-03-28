package com.pm.test.j2ee.dao.morphia;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.ejb.Singleton;

import org.mongodb.morphia.query.Query;

import com.pm.test.j2ee.dao.UserDAO;
import com.pm.test.j2ee.models.User;

@Singleton
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDAO {

	public UserDaoImpl() throws UnknownHostException {
		super(User.class);
	}

	@Override
	public long count(Map<String, String> filters) {
		Query<User> query = createQuery();
		addFilters(query, filters);
		return query.countAll();
	}

	@Override
	public User getByLogin(String login) {
		Pattern regExp = Pattern.compile(login + ".*", Pattern.CASE_INSENSITIVE);
		Query<User> result = getDatastore().find(getEntityClass()).filter("login", regExp);
		return result.get();
	}

	@Override
	public Collection<User> get(Map<String, String> filters) {
		Query<User> query = createQuery();
		addFilters(query, filters);
		return query.asList();
	}

	@Override
	public Collection<User> get(Map<String, String> filters, Integer offset, Integer limit, String orderBy,
			OrderType orderType) {
		Query<User> query = createQuery(offset, limit, orderBy, orderType);
		addFilters(query, filters);
		return query.asList();
	}

	private Query<User> addFilters(Query<User> query, Map<String, String> filters) {
		if (filters != null && !filters.isEmpty())
			for (Entry<String, String> filter : filters.entrySet())
				query.and(query.criteria(filter.getKey()).containsIgnoreCase(filter.getValue()));

		return query;
	}
}
