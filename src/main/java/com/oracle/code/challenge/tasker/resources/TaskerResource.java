package com.oracle.code.challenge.tasker.resources;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.codahale.metrics.annotation.Timed;

import com.oracle.code.challenge.tasker.core.TaskerResponse;
import com.oracle.code.challenge.tasker.core.db.TaskerEntity;
import com.oracle.code.challenge.tasker.dao.TaskerDAO;

import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class TaskerResource {

	private final TaskerDAO taskerDAO;

	public TaskerResource(TaskerDAO peopleDAO) {
		this.taskerDAO = peopleDAO;
	}

	@GET
	@Path("/all")
	@UnitOfWork
	public Response fetchAllTasks() {
		log.info("Resorce method fetchAllTasks has invoked");
		try {
			List<TaskerResponse> tasksList = taskerDAO.findAll();
			if (Objects.nonNull(tasksList)&&!tasksList.isEmpty()) {
				return Response.ok(tasksList).build();
			} else
				throw new WebApplicationException("No Tasks Found", Status.NO_CONTENT);

		} catch (Exception e) {
			log.error("Exception occured while fetching ALL Tasks due to {}", e.getMessage());
			throw new WebApplicationException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
		}

	}

	@GET
	@Path("/{id}")
	@Timed
	@UnitOfWork
	public Response fetchTask(@PathParam("id") Long id) {
		log.info("Resource method fetchTask has invoked id=" + id);
		try {
			TaskerResponse response = taskerDAO.findById(id);
			if (Objects.nonNull(response)) {
				return Response.ok(response).build();

			} else
				throw new WebApplicationException("No Task Found", Status.NO_CONTENT);
		} catch (Exception e) {
			log.error("Exception occured while fetch TASK due to {}", e.getMessage());
			throw new WebApplicationException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
		}
	}

	@POST
	@Path("/save")
	@Timed
	@UnitOfWork
	public Response saveTasks(TaskerEntity request) {
		log.info("Resource method saveTasks has invoked id= {},desc={}" + request.getId(), request.getDescription());
		try {
			TaskerResponse reqStatus = taskerDAO.saveTask(request);
			if (Objects.nonNull(reqStatus)) {
				return Response.ok(reqStatus).build();
			} else
				log.error("FAILED to save task desc:{} ,date:{}", request.getDescription(), request.getDate());
			return Response.ok(reqStatus).build();
		} catch (Exception e) {
			log.error("Exception occured while saving Tasks due to {}", e.getMessage());
			throw new WebApplicationException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
		}
	}

	@POST
	@Path("/update")
	@Timed
	@UnitOfWork
	public Response updateTasks(TaskerEntity request) {
		log.info("Resource method updateTasks has invoked id= {},status={}" + request.getId(), request.getComplete());
		try {
			TaskerResponse reqStatus = taskerDAO.updateStatus(request);
			if (Objects.nonNull(reqStatus)) {
				return Response.ok(reqStatus).build();
			} else
				log.error("FAILED to update task status:{} ,id:{}", request.getComplete(), request.getId());
			return Response.ok(reqStatus).build();
		} catch (Exception e) {
			log.error("Exception occured while saving Tasks due to {}", e.getMessage());
			throw new WebApplicationException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
		}
	}
	
}
