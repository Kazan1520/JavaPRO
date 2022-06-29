package com.jawapro.jawapro.Service;


import com.jawapro.jawapro.DTO.Request.StudentRequest;
import com.jawapro.jawapro.DTO.Request.UserRequest;
import com.jawapro.jawapro.Entity.Role;
import com.jawapro.jawapro.Entity.Student;
import com.jawapro.jawapro.Entity.User;
import com.jawapro.jawapro.Repository.RoleRepository;
import com.jawapro.jawapro.Repository.UserRepository;
import com.jawapro.jawapro.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.security.Principal;
@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User createUser(UserRequest user) {
        Role roleUser = roleRepository.findByName(RoleEnum.USER).get();
        User user1 = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .role(roleUser)
                .build();
        User savedUser = userRepository.save(user1);
        savedUser.setStudent(studentService.createStudent(new StudentRequest(savedUser, user)));
        savedUser = userRepository.save(savedUser);
        return savedUser;
    }

    public User updateUser(Long id, UserRequest userRequest){
        if(userRepository.findById(id).isPresent()){
            User oldUser = userRepository.findById(id).get();
            oldUser.setUsername(userRequest.getUsername());
            oldUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            oldUser.setEmail(userRequest.getEmail());
            Student oldStudent = oldUser.getStudent();
            studentService.updateStudent(oldStudent.getId(),new StudentRequest(oldUser,userRequest));
            return userRepository.save(oldUser);
        }
        else
            return null;
    }

    public User getUser(Long id){
        if(userRepository.findById(id).isPresent())
            return userRepository.findById(id).get();
        else
            return null;
    }

    public void deleteUser(Long id){
        if(userRepository.findById(id).isPresent())
            userRepository.delete(userRepository.findById(id).get());
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
    public Boolean isUser(Model model, Principal principal){
        try {
            String username = principal.getName();
            User user = getUserByUsername(username);
            return user.getRole().getName().equals(RoleEnum.USER);
        }
        catch (Exception e){
            return false;
        }
    }

    public Boolean isAdmin(Model model, Principal principal){
        try {
        String username = principal.getName();
        User user = getUserByUsername(username);
        return user.getRole().getName().equals(RoleEnum.ADMIN);}
        catch (Exception e){
            return false;
        }
    }

}
