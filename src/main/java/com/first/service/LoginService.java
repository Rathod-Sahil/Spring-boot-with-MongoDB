package com.first.service;

import com.first.decorator.LoginDto;
import com.first.decorator.RequestSession;
import com.first.enums.Roles;
import com.first.enums.Status;
import com.first.exception.*;
import com.first.model.Student;
import com.first.model.AdminConfig;
import com.first.repository.AdminConfigRepository;
import com.first.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final RequestSession requestSession;
    private final AdminConfigService adminConfigService;

    public Student login(LoginDto loginDTO){
        Student student = studentRepository.findByEmailAndSoftDeleteFalse(loginDTO.getEmail()).orElseThrow(StudentIsNotExisted::new);
        if(student.getStatus()!=Status.BLOCK){
            int loginAttempts = student.getFailedLoginAttempts();
            AdminConfig adminConfig = adminConfigService.getAdminConfig();

            if(passwordEncoder.matches(loginDTO.getPassword(), student.getPassword())){
                student.setFailedLoginAttempts(0);
                studentRepository.save(student);
                return student;

            }else if(loginAttempts < adminConfig.getMaxLoginAttempts()){
                if(!student.getRole().contains(Roles.ADMIN)){
                    student.setFailedLoginAttempts(loginAttempts+1);
                }
                studentRepository.save(student);
                throw new PasswordNotMatchException();
            }
            student.setStatus(Status.BLOCK);
            studentRepository.save(student);
        }
        throw new StudentBlockedException();
    }

    public void unBlock(String id){
        Object role = requestSession.getJwtUser().getRole();

        if(!role.toString().contains("ADMIN")) {
            throw new NotAdminException();
        }

        Student student = studentRepository.findByIdAndSoftDeleteFalse(id).orElseThrow(StudentIsNotExisted::new);

        if(student.getRole().contains(Roles.ADMIN)){
            throw new AdminCanNotBeBlocked();
        }else if(!student.getStatus().equals(Status.BLOCK)){
            throw new StudentIsNotBlocked();
        }

        student.setStatus(Status.UNBLOCK);
        student.setFailedLoginAttempts(0);
        studentRepository.save(student);
    }
}
