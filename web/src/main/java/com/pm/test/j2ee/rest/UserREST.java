package com.pm.test.j2ee.rest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;

import com.pm.test.j2ee.dao.UserDAO;
import com.pm.test.j2ee.models.User;
import com.pm.test.j2ee.services.GenericDAO.OrderType;

@Path("/user")
@ApplicationScoped
public class UserREST {

	@Inject
	private UserDAO userDAO;

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User create(User user) {
		return userDAO.create(user);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User update(User user) {
		return userDAO.update(user);
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void delete(User user) {
		userDAO.remove(user);
	}

	@DELETE
	@Path("/{uid}")
	public void delete(@PathParam("uid") String uid) {
		User user = userDAO.get(uid);
		userDAO.remove(user);
	}

	@GET
	@Path("/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public User get(@PathParam("uid") String uid) {
		return userDAO.get(uid);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> get(@QueryParam("login") String login, @QueryParam("email") String email,
			@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName,
			@QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit,
			@QueryParam("orderBy") String orderBy, @QueryParam("orderType") OrderType orderType) {

		Map<String, String> filters = createFilters(login, email, firstName, lastName);
		return userDAO.get(filters, offset, limit, orderBy, orderType);
	}

	@OPTIONS
	@Produces(MediaType.TEXT_PLAIN)
	public long count(@QueryParam("login") String login, @QueryParam("email") String email,
			@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName) {

		Map<String, String> filters = createFilters(login, email, firstName, lastName);
		return userDAO.count(filters);
	}

	private Map<String, String> createFilters(String login, String email, String firstName, String lastName) {
		Map<String, String> filters = new HashMap<>();
		if (StringUtils.isNotBlank(login))
			filters.put("login", login);
		if (StringUtils.isNotBlank(email))
			filters.put("email", email);
		if (StringUtils.isNotBlank(firstName))
			filters.put("firstName", firstName);
		if (StringUtils.isNotBlank(lastName))
			filters.put("lastName", lastName);
		return filters;
	}
}
