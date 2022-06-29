package com.jawapro.jawapro.Controller;

import com.jawapro.jawapro.DTO.Request.ProjectTopicRequest;
import com.jawapro.jawapro.DTO.Request.UserRequest;
import com.jawapro.jawapro.DTO.Response.ProjectTopicResponse;
import com.jawapro.jawapro.DTO.Response.TakenProjectResponse;
import com.jawapro.jawapro.Entity.Student;
import com.jawapro.jawapro.Service.ProjectTopicService;
import com.jawapro.jawapro.Service.StudentService;
import com.jawapro.jawapro.Service.TakenProjectService;
import com.jawapro.jawapro.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class WebController {
    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TakenProjectService takenProjectService;

    @Autowired
    private ProjectTopicService projectTopicService;

    @ModelAttribute("module")
    String module() {
        return "home";
    }


    Model checkUser(Model model, Principal principal) {
        if (userService.isUser(model, principal)){
            model.addAttribute("user", true);
        }
        else {
            model.addAttribute("user", false);
        }
        if (userService.isAdmin(model, principal)){
            model.addAttribute("admin", true);
        }
        else {
            model.addAttribute("admin", false);
        }

        return model;
    }
    @GetMapping("/")
    String index (Model model, Principal principal) {
        checkUser(model, principal);
        return "index";
    }

    @GetMapping("/register")
    String register(Model model){
        return "register";
    }

    @RequestMapping("/register")
    String register(@RequestBody MultiValueMap<String, String> formData, Model model){
        UserRequest userRequest = UserRequest.builder()
                .username(formData.get("Username").get(0))
                .password(formData.get("Password").get(0))
                .email(formData.get("Email").get(0))
                .firstName(formData.get("FirstName").get(0))
                .lastName(formData.get("LastName").get(0))
                .indexNumber(formData.get("IndexNumber").get(0))
                .build();
        if(Objects.equals(formData.get("Password").get(0), formData.get("Password2").get(0))) {
            try {
                userService.createUser(userRequest);
                model.addAttribute("registerSuccessful", true);
            }catch(Exception e){
                model.addAttribute("registerFailed", true);
            }
        }else{
            model.addAttribute("passwordDoesNotMatch",true);
        }
        return login(model);
    }

    @RequestMapping("/addProjectTopic")
    String addProjectTopic(@RequestBody MultiValueMap<String, String> formData, Model model, Principal principal) throws ParseException {
        checkUser(model, principal);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(formData.get("deadline").get(0));
        ProjectTopicRequest projectTopicRequest = ProjectTopicRequest.builder()
                .name(formData.get("name").get(0))
                .description(formData.get("description").get(0))
                .deadline(new Timestamp(date.getTime()))
                .multipleMaxCount(Integer.parseInt(formData.get("multipleMaxCount").get(0)))
                .build();
        projectTopicService.createProjectTopic(projectTopicRequest);
        return adminProjects(model, principal);
    }

    @RequestMapping("/changeTakenProjectDeadline/{id}")
    String changeTakenProjectDate(@RequestBody MultiValueMap<String, String> formData, @PathVariable Long id, Model model, Principal principal) throws ParseException {
        checkUser(model, principal);
        takenProjectService.changeTakenProjectDeadline(id, formData.get("deadline").get(0));
        return adminTakenProjects(model, principal);
    }

    @GetMapping("/login")
    String login(Model model){
        return "login";
    }

    String projects(Model model, Principal principal){
        checkUser(model, principal);
        String username = principal.getName();
        Student student = studentService.getStudentByUsername(username);
        List<ProjectTopicResponse> projects1 = projectTopicService.getAllProjectTopics();
        List<TakenProjectResponse> takenProjects = studentService.getStudentProjects(student.getId());
        List<Long> studentProjects = new ArrayList<>();
        for (TakenProjectResponse takenProject : takenProjects) {
            studentProjects.add(takenProject.getProjectTopic().getId());
        }
        model.addAttribute("projects", projects1);
        model.addAttribute("studentProjects", studentProjects);
        return "projects";
        }

    @RequestMapping("/projects")
    String adminOrUser(Model model, Principal principal){
        checkUser(model, principal);
        if(Boolean.TRUE.equals(userService.isAdmin(model,principal)))
            return adminProjects(model,principal);
        else
            return projects(model,principal);
    }
    @RequestMapping("/account")
    String userAccount(Model model, Principal principal){
        checkUser(model, principal);
        String username = principal.getName();
        Student student = studentService.getStudentByUsername(username);
        model.addAttribute("student",student);
        return "account";
    }

    String adminProjects(Model model, Principal principal){
        checkUser(model, principal);
        List<ProjectTopicResponse> projects1 = projectTopicService.getAllProjectTopics();
        model.addAttribute("projects", projects1);
        return "adminallprojects";
    }

    @RequestMapping("/userprojects")
    String project(Model model, Principal principal){
        checkUser(model, principal);
        String username = principal.getName();
        Student student = studentService.getStudentByUsername(username);
        List<TakenProjectResponse> takenProjects = studentService.getStudentProjects(student.getId());
        model.addAttribute("projects",takenProjects);
        return "userprojects";
    }

    @RequestMapping("/adminTakenProjects")
    String adminTakenProjects(Model model, Principal principal){
        checkUser(model, principal);
        if (Boolean.TRUE.equals(userService.isAdmin(model,principal))) {
            List<TakenProjectResponse> takenProjects = takenProjectService.getAllTakenProjects();
            model.addAttribute("projects", takenProjects);
            return "takenprojectsadmin";
        }
        return "index";
    }

    @Transactional
    @RequestMapping("/uploadFile/{id}")
    String uploadFile(Model model, Principal principal, @PathVariable Long id, @RequestParam("file") MultipartFile file) {
        if(Boolean.FALSE.equals(userService.isUser(model,principal))){
            return index(model,principal);}
        takenProjectService.uploadFile(id,file);
        return project(model,principal);
    }



}


