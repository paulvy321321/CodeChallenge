package com.oracle.code.challenge.tasker.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


import com.oracle.code.challenge.tasker.core.TaskerResponse;
import com.oracle.code.challenge.tasker.core.db.TaskerEntity;

import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskerDAO extends AbstractDAO<TaskerEntity> {

	public TaskerDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
	 * Method to save tasks
	 * 
	 * @param request
	 * @return TaskerResponse
	 */
	public TaskerResponse saveTask(TaskerEntity request) {
		if (Objects.nonNull(request))
			validateRequest(request);
		else
			throw new WebApplicationException("BAD Request", Status.BAD_REQUEST);
		Long responseId = persist(request).getId();
		return Objects.nonNull(responseId) ? mapStatus("SUCCESS") : mapStatus("FAILED");
	}

	/**
	 * Method to update complete status by id
	 * 
	 * @param request
	 * @return TaskerResponse
	 */
	public TaskerResponse updateStatus(TaskerEntity request) {
		if (Objects.nonNull(request))
			validateUpdateRequest(request);
		else
			throw new WebApplicationException("BAD Request", Status.BAD_REQUEST);
		int responseStatus = executeUpdateQuery(request);
		return Objects.nonNull(responseStatus) && !Objects.equals(0, responseStatus) ? mapStatus("SUCCESS")
				: mapStatus("FAILED");
	}


	/**
	 * Method to fetch all tasks
	 * 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<TaskerResponse> findAll() {
		List<TaskerEntity> tasksList = list(currentSession().createQuery("from TaskerEntity"));
		List<TaskerResponse> responseList = new ArrayList<>();
		if (!tasksList.isEmpty()) {

			tasksList.stream().forEach(task -> {
				responseList.add(mapResponse(task));
			});
		} else {
			log.info("No Tasks Found with findAll request ");
			return null;
		}
		return responseList;
	}

	/**
	 * Method to find task by id
	 * 
	 * @param id
	 * @return taskerResponse
	 */
	public TaskerResponse findById(Long id) {
		TaskerEntity response = get(id);
		TaskerResponse taskerResponse = new TaskerResponse();
		return Objects.nonNull(response) ? mapResponse(response) : taskerResponse;
	}

	/**
	 * Method to map response
	 * 
	 * @param response
	 * @param taskerResponse
	 * @return
	 */
	public TaskerResponse mapResponse(TaskerEntity response) {
		TaskerResponse taskerResponse = new TaskerResponse();
		taskerResponse.setDate(Objects.nonNull(response.getDate()) ? response.getDate() : null);
		taskerResponse.setDescription(Objects.nonNull(response.getDescription()) ? response.getDescription() : null);
		taskerResponse.setId(Objects.nonNull(response.getId()) ? response.getId() : null);
		taskerResponse.setCompleted(Objects.nonNull(response.getComplete()) ? response.getComplete() : null);
		return taskerResponse;
	}

	/**
	 * @param person
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private int executeUpdateQuery(TaskerEntity person) {
		String queryString = String.format("update TaskerEntity set complete = :complete where id in (:id)",
				TaskerEntity.class);
		Query query = currentSession().createQuery(queryString);
		query.setParameter("id", person.getId());
		query.setParameter("complete", person.getComplete());
		return query.executeUpdate();
	}
	private TaskerResponse mapStatus(String status) {
		TaskerResponse response=new TaskerResponse();
		response.setStatus(status);
		return response;
	}

	private void validateRequest(TaskerEntity request) {
		if (Objects.isNull(request.getDescription())) {
			throw new WebApplicationException("Description should not be empty", Status.BAD_REQUEST);
		} else if (Objects.isNull(request.getDate()))

		{
			throw new WebApplicationException("Date should not be empty", Status.BAD_REQUEST);
		}

	}

	private void validateUpdateRequest(TaskerEntity request) {
		if (Objects.isNull(request.getId())) {
			throw new WebApplicationException("ID should not be empty", Status.BAD_REQUEST);
		} else if (Objects.isNull(request.getComplete()))

		{
			throw new WebApplicationException("Status should not be empty", Status.BAD_REQUEST);
		}
	}
}
