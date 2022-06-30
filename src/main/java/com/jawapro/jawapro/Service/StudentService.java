package com.jawapro.jawapro.Service;

import com.jawapro.jawapro.DTO.Request.StudentRequest;
import com.jawapro.jawapro.DTO.Response.TakenProjectResponse;
import com.jawapro.jawapro.Entity.Student;
import com.jawapro.jawapro.Entity.TakenProject;
import com.jawapro.jawapro.Repository.StudentRepository;
import com.jawapro.jawapro.Repository.TakenProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Transactional
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TakenProjectRepository takenProjectRepository;


    public Student createStudent(StudentRequest student) {
        Student student1 = Student.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .indexNumber(student.getIndexNumber())
                .user(student.getUser())
                .build();
        return studentRepository.save(student1);
    }

    public Student getStudentByUsername(String username) {
        return studentRepository.findByUser_Username(username);
    }

    public Student getStudent(Long id){
        if(studentRepository.findById(id).isPresent())
            return studentRepository.findById(id).get();
        else
            return null;
    }

    public Student updateStudent(Long id,  StudentRequest studentRequest){
        if(studentRepository.findById(id).isPresent()) {
            Student oldStudent = studentRepository.findById(id).get();
            oldStudent.setFirstName(studentRequest.getFirstName());
            oldStudent.setLastName(studentRequest.getLastName());
            oldStudent.setIndexNumber(studentRequest.getIndexNumber());
            return studentRepository.save(oldStudent);
        }
        else return null;
    }

    public void deleteStudent(Long id){
        if(studentRepository.findById(id).isPresent())
            studentRepository.delete(studentRepository.findById(id).get());
    }

    public List<TakenProjectResponse> getStudentProjects(Long id) {
        List<TakenProject> takenProjects = takenProjectRepository.findAllByStudentId(id);
        List<TakenProjectResponse> result = new ArrayList<>();
        for (TakenProject takenProject : takenProjects) {
            result.add(new TakenProjectResponse(takenProject));
        }
        return result;
    }
}
