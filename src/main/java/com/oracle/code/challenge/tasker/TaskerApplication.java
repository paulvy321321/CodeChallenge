package com.oracle.code.challenge.tasker;


import com.oracle.code.challenge.tasker.core.db.TaskerEntity;
import com.oracle.code.challenge.tasker.dao.TaskerDAO;
import com.oracle.code.challenge.tasker.resources.TaskerResource;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class TaskerApplication extends Application<TaskerConfiguration> {

	public static void main(String[] args) throws Exception {
		new TaskerApplication().run(args);
	}

	private final HibernateBundle<TaskerConfiguration> hibernate = new HibernateBundle<TaskerConfiguration>(
			TaskerEntity.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(TaskerConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public void initialize(Bootstrap<TaskerConfiguration> bootstrap) {
		bootstrap.addBundle(hibernate);
	}

	@Override
	public void run(TaskerConfiguration config, Environment environment) {
		final TaskerDAO dao = new TaskerDAO(hibernate.getSessionFactory());
		environment.jersey().register(new TaskerResource(dao));

	}

}
