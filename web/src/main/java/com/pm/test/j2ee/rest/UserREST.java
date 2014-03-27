package com.pm.test.j2ee.rest;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.pm.test.j2ee.dao.UserDAO;
import com.pm.test.j2ee.models.User;

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
		userDAO.delete(user);
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") Long id) {
		User user = userDAO.getById(id);
		userDAO.delete(user);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getById(@PathParam("id") Long id) {
		return userDAO.getById(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAll() {
		return userDAO.getAll();
	}

}
