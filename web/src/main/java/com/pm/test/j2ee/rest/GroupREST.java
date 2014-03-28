package com.pm.test.j2ee.rest;

import java.util.Collection;

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

import com.pm.test.j2ee.dao.GenericDAO.OrderType;
import com.pm.test.j2ee.dao.GroupDAO;
import com.pm.test.j2ee.models.Group;

@Path("/group")
public class GroupREST {

	@Inject
	private GroupDAO groupDAO;

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Group create(Group group) {
		return groupDAO.create(group);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Group update(Group group) {
		return groupDAO.update(group);
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void delete(Group group) {
		groupDAO.remove(group);
	}

	@DELETE
	@Path("/{uid}")
	public void delete(@PathParam("uid") String uid) {
		Group group = groupDAO.get(uid);
		groupDAO.remove(group);
	}

	@GET
	@Path("/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Group get(@PathParam("uid") String uid) {
		return groupDAO.get(uid);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Group> get(@QueryParam("name") String name, @QueryParam("offset") Integer offset,
			@QueryParam("limit") Integer limit, @QueryParam("orderBy") String orderBy,
			@QueryParam("orderType") OrderType orderType) {
		return groupDAO.getByName(name, offset, limit, orderBy, orderType);
	}

	@OPTIONS
	@Produces(MediaType.TEXT_PLAIN)
	public long count(@QueryParam("name") String name) {
		return groupDAO.count(name);
	}

}
