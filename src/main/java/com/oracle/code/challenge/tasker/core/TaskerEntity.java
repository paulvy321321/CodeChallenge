package com.oracle.code.challenge.tasker.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TASKS")
@Data
public class TaskerEntity implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TASK_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String date;

	private String description;
}
