package com.jawapro.jawapro.Service;

import com.jawapro.jawapro.DTO.Request.ProjectTopicRequest;
import com.jawapro.jawapro.DTO.Request.TakenProjectRequest;
import com.jawapro.jawapro.DTO.Response.ProjectTopicResponse;
import com.jawapro.jawapro.Entity.ProjectTopic;
import com.jawapro.jawapro.Entity.Student;
import com.jawapro.jawapro.Repository.ProjectTopicRepository;
import com.jawapro.jawapro.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
@Service
public class ProjectTopicService {

    @Autowired
    private ProjectTopicRepository projectTopicRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TakenProjectService takenProjectService;

    public ProjectTopicResponse createProjectTopic(ProjectTopicRequest projectTopicRequest){
        ProjectTopic projectTopic = ProjectTopic.builder()
                .name(projectTopicRequest.getName())
                .description(projectTopicRequest.getDescription())
                .multipleMaxCount(projectTopicRequest.getMultipleMaxCount())
                .deadline(projectTopicRequest.getDeadline())
                .build();
        return new ProjectTopicResponse(projectTopicRepository.save(projectTopic));
    }

    public ProjectTopicResponse getProjectTopic(Long id){
        if (projectTopicRepository.findById(id).isPresent()) {
            return new ProjectTopicResponse(projectTopicRepository.findById(id).get());
        } else {
            return null;
        }
    }

    public ProjectTopicResponse updateProjectTopic(Long id, ProjectTopicRequest projectTopicRequest){
        if (projectTopicRepository.findById(id).isPresent()) {
            ProjectTopic oldProjectTopic = projectTopicRepository.findById(id).get();
            oldProjectTopic.setName(projectTopicRequest.getName());
            oldProjectTopic.setDescription(projectTopicRequest.getDescription());
            oldProjectTopic.setMultipleMaxCount(projectTopicRequest.getMultipleMaxCount());
            oldProjectTopic.setDeadline(projectTopicRequest.getDeadline());
            return new ProjectTopicResponse(projectTopicRepository.save(oldProjectTopic));
        } else {
            return null;
        }
    }

    public void deleteProjectTopic(Long id){
       if (projectTopicRepository.findById(id).isPresent()) {
           projectTopicRepository.delete(projectTopicRepository.findById(id).get());
       }
    }

    public List<ProjectTopicResponse> getAllProjectTopics(){
        List<ProjectTopic> projectTopics = projectTopicRepository.findAll();
        return projectTopics.stream()
                .map(ProjectTopicResponse::new)
                .collect(java.util.stream.Collectors.toList());
    }

    public ProjectTopicResponse reserveProjectTopic(Long id, Long studentId){
        ProjectTopic projectTopic = projectTopicRepository.findById(id).get();
        if (projectTopic.isReserved()) {
            return null;
        } else {
            Student student = studentRepository.findById(studentId).get();
            TakenProjectRequest takenProject = TakenProjectRequest.builder()
                            .projectTopic(projectTopic)
                    .student(student)
                    .deadline(projectTopic.getDeadline())
                    .build();
            takenProjectService.createTakenProject(takenProject);
            if (projectTopic.getTakenProjects().size() - 1 >= projectTopic.getMultipleMaxCount()) {
                projectTopic.setReserved(true);
            }
            }
            return new ProjectTopicResponse(projectTopicRepository.save(projectTopic));
    }
}

