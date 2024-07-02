package com.first.scheduler.jobs;

import org.jetbrains.annotations.NotNull;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.first.enums.UserStatus;
import com.first.model.Student;
import com.first.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BlockJob extends QuartzJobBean{

    private final StudentRepository studentRepository;

    @Override
    protected void executeInternal(@NotNull JobExecutionContext context){
        Student student = studentRepository.findById(context.getJobDetail().getKey().getName()).get();

            student.setUserStatus(UserStatus.ACTIVE);
            student.setFailedLoginAttempts(0);
            studentRepository.save(student);
            log.info("Block job run");
    }
}
