package com.first.service;

import com.first.decorator.LoginDto;
import com.first.decorator.RequestSession;
import com.first.enums.Role;
import com.first.enums.UserStatus;
import com.first.exception.*;
import com.first.model.AdminConfig;
import com.first.model.Student;
import com.first.repository.StudentRepository;
import com.first.scheduler.jobs.BlockJob;
import com.first.utils.AdminConfigUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminConfigUtils adminConfigUtils;
    private final SchedulingService schedulingService;
    private final Scheduler scheduler;

    public Student login(LoginDto loginDTO) {
        Student student = studentRepository.findByEmailAndSoftDeleteFalse(loginDTO.getEmail())
                .orElseThrow(StudentNotExistedException::new);
        if (student.getUserStatus() != UserStatus.BLOCKED) {
            int loginAttempts = student.getFailedLoginAttempts();
            AdminConfig adminConfig = adminConfigUtils.getAdminConfig();

            if (passwordEncoder.matches(loginDTO.getPassword(), student.getPassword())) {
                student.setFailedLoginAttempts(0);
                studentRepository.save(student);
                return student;
            } else if (loginAttempts < adminConfig.getMaxLoginAttempts()) {
                if (!student.getRole().contains(Role.ADMIN)) {
                    student.setFailedLoginAttempts(loginAttempts + 1);
                }
                studentRepository.save(student);
                throw new WrongPasswordException("Password doesn't match");
            }
            student.setUserStatus(UserStatus.BLOCKED);
            studentRepository.save(student);
            schedulingService.dateSchedule(student.getId(), BlockJob.class, new DateTime().plusDays(adminConfig.getMaxAccountBlockedDays()));
        }
        throw new StudentBlockedException("Student is blocked, contact admin for unblock");
    }

    public void unBlock(String id) {
        Student student = studentRepository.findByIdAndSoftDeleteFalse(id).orElseThrow(StudentNotExistedException::new);

        if (student.getRole().contains(Role.ADMIN)) {
            throw new AdminBlockException();
        } else if (!student.getUserStatus().equals(UserStatus.BLOCKED)) {
            throw new StudentNotBlockedException("Student is not blocked");
        }

        student.setUserStatus(UserStatus.ACTIVE);
        student.setFailedLoginAttempts(0);
        studentRepository.save(student);
        try {
            scheduler.deleteJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            log.error("Scheduler exception : { }",e);
        }
    }
}
