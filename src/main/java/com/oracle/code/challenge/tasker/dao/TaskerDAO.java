package com.oracle.code.challenge.tasker.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.SessionFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.code.challenge.tasker.core.TaskerEntity;
import com.oracle.code.challenge.tasker.core.TaskerResponse;

import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskerDAO extends AbstractDAO<TaskerEntity> {

	public TaskerDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public String create(TaskerEntity person) {
		Long responseId = persist(person).getId();
		return Objects.nonNull(responseId) ? "SUCCESS" : "FAILED";
	}

	@SuppressWarnings("unchecked")
	public List<TaskerResponse> findAll() {
		List<TaskerEntity> tasksList = list(currentSession().createQuery("from TaskerEntity"));
		List<TaskerResponse> responseList = new ArrayList<>();
		if (!tasksList.isEmpty()) {
			TaskerResponse taskerResponse = new TaskerResponse();
			tasksList.stream().forEach(tasks -> {
				responseList.add(mapResponse(tasks, taskerResponse));
			});
		} else
			return null;
		return responseList;
	}

	public TaskerResponse findById(Long id) {
		TaskerEntity response = get(id);
		TaskerResponse taskerResponse = new TaskerResponse();
		return Objects.nonNull(response) ? mapResponse(response, taskerResponse) : taskerResponse;
	}

	public TaskerResponse mapResponse(TaskerEntity response, TaskerResponse taskerResponse) {
		taskerResponse.setDate(response.getDate());
		taskerResponse.setDescription(response.getDescription());
		taskerResponse.setId(response.getId());
		return taskerResponse;
	}

}
