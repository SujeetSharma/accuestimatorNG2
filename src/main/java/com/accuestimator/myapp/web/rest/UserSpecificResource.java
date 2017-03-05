package com.accuestimator.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.accuestimator.myapp.domain.Estimates;
import com.accuestimator.myapp.domain.FTEstimates;
import com.accuestimator.myapp.domain.FactorsTaskMapping;
import com.accuestimator.myapp.domain.Project;
import com.accuestimator.myapp.domain.ProjectUserMapping;
import com.accuestimator.myapp.domain.Template;
import com.accuestimator.myapp.repository.EstimatesRepository;
import com.accuestimator.myapp.repository.FactorsTaskMappingRepository;
import com.accuestimator.myapp.repository.ProjectRepository;
import com.accuestimator.myapp.repository.ProjectUserMappingRepository;
import com.accuestimator.myapp.repository.TemplateRepository;
import com.accuestimator.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Estimates.
 */
@RestController
@RequestMapping("/api")
public class UserSpecificResource {

    private final Logger log = LoggerFactory.getLogger(UserSpecificResource.class);

    private static final String ENTITY_NAME = "estimates";
        
    private ProjectUserMappingRepository projectUserMappingRepository;
    
    private ProjectRepository projectRepository;
    
    private TemplateRepository templateRepository;
    
    private FactorsTaskMappingRepository factorTaskMappingRepository;
    
    public UserSpecificResource(ProjectUserMappingRepository projectUserMappingRepository,
    		ProjectRepository projectRepository,
    		TemplateRepository templateRepository, 
    		FactorsTaskMappingRepository factorTaskMappingRepository) {
    	
    	this.projectUserMappingRepository = projectUserMappingRepository;
    	this.projectRepository = projectRepository;
    	this.templateRepository = templateRepository;
    	this.factorTaskMappingRepository = factorTaskMappingRepository;
    }

  
  
    /**
     * GET  /estimates/:id : get the "id" estimates.
     *
     * @param id the id of the estimates to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estimates, or with status 404 (Not Found)
     */
    @GetMapping("/userstemplate/{user}")
    @Timed
    public List<String> getEstimates(@PathVariable String user) {
    	
        log.debug("REST request to get Estimates : {}", user);
        
        /*
         * db.project_user_mapping.find({ "userid": "admin"}).forEach( function (result) {
		 	print( "user: " + result.projectid );
		   db.project.find( {"_id" :  ObjectId(result.projectid)}).forEach( function (result1) {
		     print( "template: " + result1.templateId );
		    		 db.template.find( {"_id" :  ObjectId(result1.templateId)}).forEach( function (result2) {
		    			 print( "facot task id: " + result2.factor_task_id );
		    			 	db.factors_task_mapping.find( {"_id" :  ObjectId(result2.factor_task_id)}).forEach( function (result3) {
		    			 		print( "estimate details: " + result3.estimates );
		   				})
		   		})
		   })
		}
		);
         */
        List<String> estimatesMappingList = new ArrayList<String>();
        for (ProjectUserMapping projectUserMapping : projectUserMappingRepository.findOneByUserid(user)) {
        	System.out.println("projectUserMapping"+projectUserMapping.getProjectid());
        	 for (Project project : projectRepository.findByid(projectUserMapping.getProjectid())) {
        		 System.out.println("project "+project.getId());
        		 for(String templateId : project.getTemplateId()) {
        			for (Template template : templateRepository.findByid(templateId)) {
	             		System.out.println("template "+template.getId());
	             		for(String taskId : template.getFactorTaskId()) {
	             			System.out.println("taskId "+taskId);
		                 	for (FactorsTaskMapping factorsTaskMapping : factorTaskMappingRepository.findById(taskId)) {
		                 		System.out.println(factorsTaskMapping.getEstimates());
		                 		estimatesMappingList.add(factorsTaskMapping.getName());
		                 	}
	             		}
	             	}
         		}
     		}
		}
        
        return estimatesMappingList;
    }

   

}
