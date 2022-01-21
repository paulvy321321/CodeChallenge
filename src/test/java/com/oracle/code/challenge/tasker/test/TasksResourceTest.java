package com.oracle.code.challenge.tasker.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.oracle.code.challenge.tasker.core.TaskerEntity;
import com.oracle.code.challenge.tasker.core.TaskerResponse;
import com.oracle.code.challenge.tasker.dao.TaskerDAO;
import com.oracle.code.challenge.tasker.resources.TaskerResource;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TasksResourceTest {
	
	 private static final TaskerDAO DAO = mock(TaskerDAO.class);
	 private static final ResourceExtension EXT = ResourceExtension.builder()
	            .addResource(new TaskerResource(DAO))
	            .build();
	 private TaskerResponse taskerResponse;
 
    
    @BeforeEach
    public void setup() {
        taskerResponse = new TaskerResponse();
        taskerResponse.setId(1L);
    }
    
    @AfterEach
   public void tearDown() {
        reset(DAO);
    }
    
    
    @Test
   public void fetchTaskTest() {
		when(DAO.findById(1L)).thenReturn(taskerResponse);
        TaskerEntity found = EXT.target("/tasks/1").request().get(TaskerEntity.class);
        assertThat(found.getId()).isEqualTo(taskerResponse.getId());
        verify(DAO).findById(1L);
    }
    @Test
    public void fetchAllTasksTest() {
    	List<TaskerResponse> hh=new ArrayList<>();
 		when(DAO.findAll()).thenReturn(hh);
         TaskerEntity found = EXT.target("/tasks/all").request().get(TaskerEntity.class);
         assertThat(found.getId()).isEqualTo(taskerResponse.getId());
         verify(DAO).findById(1L);
     }
    @Test
    public void saveTaskTest() {
 		when(DAO.findById(1L)).thenReturn(taskerResponse);
         TaskerEntity found = EXT.target("/tasks/save").request().get(TaskerEntity.class);
         assertThat(found.getId()).isEqualTo(taskerResponse.getId());
         verify(DAO).findById(1L);
     }
}