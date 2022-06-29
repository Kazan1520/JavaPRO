package com.jawapro.jawapro.Controller;

import com.jawapro.jawapro.DTO.Request.ProjectTopicRequest;
import com.jawapro.jawapro.DTO.Response.ProjectTopicResponse;
import com.jawapro.jawapro.Entity.Student;
import com.jawapro.jawapro.Service.ProjectTopicService;
import com.jawapro.jawapro.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;


@RequestMapping("/project_topic/")
@RestController
public class ProjectTopicController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProjectTopicService projectTopicService;

    @GetMapping
    public ResponseEntity<List<ProjectTopicResponse>> getAllProjectTopics(){
        return ResponseEntity.ok(projectTopicService.getAllProjectTopics());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProjectTopicResponse> getProjectTopic(@PathVariable Long id){
        return ResponseEntity.ok(projectTopicService.getProjectTopic(id));
    }

    @PostMapping("/add")
    public ResponseEntity<ProjectTopicResponse> createProjectTopic(@RequestBody ProjectTopicRequest projectTopicRequest){
        return ResponseEntity.ok(projectTopicService.createProjectTopic(projectTopicRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProjectTopicResponse> updateProjectTopic(@PathVariable Long id,@RequestBody ProjectTopicRequest projectTopicRequest){
        return ResponseEntity.ok(projectTopicService.updateProjectTopic(id,projectTopicRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProjectTopic(@PathVariable Long id){
        projectTopicService.deleteProjectTopic(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/delete/{id}")
    public RedirectView deleteProject(@PathVariable Long id){
        projectTopicService.deleteProjectTopic(id);
        return new RedirectView("/projects");
    }

    @RequestMapping("reserveProjectTopic/{id}")
    public RedirectView reserveProjectTopic(@PathVariable Long id, Principal principal){
        String studentName = principal.getName();
        Student student = studentService.getStudentByUsername(studentName);
        projectTopicService.reserveProjectTopic(id,student.getId());
        return new RedirectView("/projects");
    }

}
