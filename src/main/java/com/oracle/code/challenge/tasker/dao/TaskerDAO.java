package com.oracle.code.challenge.tasker.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.oracle.code.challenge.tasker.core.TaskerEntity;
import com.oracle.code.challenge.tasker.core.TaskerResponse;

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
	 * @return String
	 */
	public String saveTask(TaskerEntity request) {
		Long responseId = persist(request).getId();
		return Objects.nonNull(responseId) ? "SUCCESS" : "FAILED";
	}

	/**
	 * Method to update complete status by id
	 * 
	 * @param person
	 * @return String
	 */
	public String updateStatus(TaskerEntity person) {
		int responseStatus = executeUpdateQuery(person);
		return Objects.nonNull(responseStatus) && !Objects.equals(0, responseStatus) ? "SUCCESS" : "FAILED";
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
			TaskerResponse taskerResponse = new TaskerResponse();
			tasksList.stream().forEach(tasks -> {
				responseList.add(mapResponse(tasks, taskerResponse));
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
		return Objects.nonNull(response) ? mapResponse(response, taskerResponse) : taskerResponse;
	}

	/**
	 * Method to map response
	 * 
	 * @param response
	 * @param taskerResponse
	 * @return
	 */
	public TaskerResponse mapResponse(TaskerEntity response, TaskerResponse taskerResponse) {
		taskerResponse.setDate(response.getDate());
		taskerResponse.setDescription(response.getDescription());
		taskerResponse.setId(response.getId());
		taskerResponse.setCompleted(response.getComplete());
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

}
