package com.oracle.code.challenge.tasker.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.oracle.code.challenge.tasker.core.TaskerEntity;
import com.oracle.code.challenge.tasker.dao.TaskerDAO;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class TaskerResource {

	private final TaskerDAO peopleDAO;

	public TaskerResource(TaskerDAO peopleDAO) {
		this.peopleDAO = peopleDAO;
	}

	@GET
	@Path("/tasks")
	@UnitOfWork
	public Response getAllTasks() {

		List<TaskerEntity> listss = peopleDAO.findAll();
		return Response.ok(listss).build();

	}

	@GET
	@Path("/{id}")
	@Timed
	@UnitOfWork
	public TaskerEntity findPerson(@PathParam("id") Long id) {
		//return Response.ok(res).build();
		return peopleDAO.findById(id);
	}

	@GET
	@Path("/save")
	@Timed
	@UnitOfWork
	public Long savePerson(TaskerEntity request) {
		//return Response.ok(res).build();
		return peopleDAO.create(request);
	}
}
