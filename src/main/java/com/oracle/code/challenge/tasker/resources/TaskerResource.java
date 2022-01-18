package com.oracle.code.challenge.tasker.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	@Produces(MediaType.APPLICATION_JSON)
	public List<TaskerEntity> getAllTasks() {

		List<TaskerEntity> listss = peopleDAO.findAll();
		return listss;

	}

	@GET
	@Path("/{id}")
	@Timed
	@UnitOfWork
	public TaskerEntity findPerson(@PathParam("id") Long id) {
		return peopleDAO.findById(id);
	}

	@GET
	@Path("/save")
	@Timed
	@UnitOfWork
	public Long savePerson(TaskerEntity request) {
		return peopleDAO.create(request);
	}
}
