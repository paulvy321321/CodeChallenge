package com.oracle.code.challenge.tasker.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.oracle.code.challenge.tasker.core.TaskerEntity;

import io.dropwizard.hibernate.AbstractDAO;

public class TaskerDAO extends AbstractDAO<TaskerEntity> {

	public TaskerDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Long create(TaskerEntity person) {
		return persist(person).getId();
	}

	@SuppressWarnings("unchecked")
	public List<TaskerEntity> findAll() {
		return list(currentSession().createQuery("from TaskerEntity"));
	}

	public TaskerEntity findById(Long id) {
		return get(id);
	}
}
