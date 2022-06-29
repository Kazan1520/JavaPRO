package com.jawapro.jawapro.Service;

import com.jawapro.jawapro.DTO.Request.TakenProjectRequest;
import com.jawapro.jawapro.DTO.Response.TakenProjectResponse;
import com.jawapro.jawapro.Entity.File;
import com.jawapro.jawapro.Entity.TakenProject;
import com.jawapro.jawapro.Repository.TakenProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class TakenProjectService {

    @Autowired
    private TakenProjectRepository takenProjectRepository;

    @Autowired
    private FileService fileService;

    public TakenProject createTakenProject(TakenProjectRequest takenProject){
        TakenProject takenProject1 = TakenProject.builder()
                .deadline(takenProject.getDeadline())
                .projectTopic(takenProject.getProjectTopic())
                .student(takenProject.getStudent())
                .file(takenProject.getFile())
                .build();
        return takenProjectRepository.save(takenProject1);
    }

    public TakenProject getTakenProject(Long id){
        if (takenProjectRepository.findById(id).isPresent()) {
            return takenProjectRepository.findById(id).get();
        } else {
            return null;
        }
    }

    public List<TakenProjectResponse> getAllTakenProjects(){
        List<TakenProject> takenProjects = takenProjectRepository.findAll();
        return takenProjects.stream()
                .map(TakenProjectResponse::new)
                .collect(java.util.stream.Collectors.toList());
    }

    public TakenProject updateTakenProject(Long id, TakenProjectRequest takenProject){
        if (takenProjectRepository.findById(id).isPresent()) {
            TakenProject oldTakenProject = takenProjectRepository.findById(id).get();
            oldTakenProject.setDeadline(takenProject.getDeadline());
            oldTakenProject.setProjectTopic(takenProject.getProjectTopic());
            oldTakenProject.setStudent(takenProject.getStudent());
            oldTakenProject.setFile(takenProject.getFile());
            return takenProjectRepository.save(oldTakenProject);
        } else {
            return null;
        }
    }

    public TakenProject changeTakenProjectDeadline(Long id, String deadline) throws ParseException {
        if (takenProjectRepository.findById(id).isPresent()) {
            TakenProject takenProject = takenProjectRepository.findById(id).get();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(deadline);
            takenProject.setDeadline(new Timestamp(date.getTime()));
            return takenProjectRepository.save(takenProject);
        } else {
            return null;
        }
    }

    public void deleteTakenProject(Long id){
        if (takenProjectRepository.findById(id).isPresent()) {
            takenProjectRepository.delete(takenProjectRepository.findById(id).get());
        }
    }

    @Transactional
    public TakenProject uploadFile(Long id, MultipartFile file){
        if (takenProjectRepository.findById(id).isPresent()) {
            File fileSaved = fileService.saveFile(file);
            TakenProject takenProject = takenProjectRepository.findById(id).get();
            takenProject.setFile(fileSaved);
            return takenProjectRepository.save(takenProject);
        }
        else
            return null;
    }

}
