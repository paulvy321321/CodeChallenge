package com.oracle.code.challenge.tasker.core;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskerResponse {

	public Long id;
	public String description;
	public String date;
	public Boolean completed;
	public String status;
}
