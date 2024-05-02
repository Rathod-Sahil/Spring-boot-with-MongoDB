package com.first.service;

import com.first.configuration.NullAwareBeans;
import com.first.decorator.*;
import com.first.enums.Direction;
import com.first.enums.Fields;
import com.first.enums.Roles;
import com.first.enums.Status;
import com.first.exception.*;
import com.first.model.*;
import com.first.repository.AdminConfigRepository;
import com.first.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final NullAwareBeans nullAwareBeans;
    private final ValidationUtils validationService;
    private final EmailPublisher emailPublisher;
    private final AdminConfigService adminConfigService;
    private final AdminConfigRepository adminConfigRepository;
    private final MustacheHelper mustacheHelper;

    // Create a single student
    @Override
    public Student createStudent(StudentDto sd) {
        Optional<Student> student1 = studentRepository.findByEmailAndSoftDeleteFalse(sd.getEmail());
        if (student1.isPresent()) {
            throw new StudentIsAlreadyExisted();
        }

        // Validation
        validationService.nullFieldValidation(sd.getEmail(),sd.getPassword(),sd.getPhoneNo());
        validationService.emailValidate(sd.getEmail());
        validationService.passwordValidation(sd.getPassword());
        validationService.phoneNoValidate(sd.getPhoneNo());

        Student student = modelMapper.map(sd, Student.class);

        student.setFullName(sd.getFirstName().concat(sd.getLastName()));
        if (student.getRole().contains(Roles.ADMIN)) {
            student.setStatus(Status.APPROVE);
        } else {
            student.setStatus(Status.DRAFT);
        }
        student.setPassword(passwordEncoder.encode(sd.getPassword()));

        student.setRegistrationDate(LocalDate.now());

        EmailDto emailDto = new EmailDto();
        emailDto.setUserName(sd.getFirstName() + " " + sd.getLastName());
        String content =  mustacheHelper.setMessageContent("templates/email.html", emailDto);

        emailPublisher.sendEmailNotification(new EmailDetails(sd.getEmail(),"Registration",content));

        if(!student.getRole().contains(Roles.ADMIN)){
            List<Student> admin = studentRepository.filterByRole(List.of("ADMIN"));

            emailDto.setEmail(sd.getEmail());

                for (Student s:admin) {
                    emailDto.setAdminName(s.getFullName());
                    String content1 = mustacheHelper.setMessageContent("templates/userRegister.html", emailDto);
                    emailPublisher.sendEmailNotification(new EmailDetails(s.getEmail(), "Registration", content1));
                }
        }else {
            AdminConfig adminConfig = adminConfigService.getAdminConfig();
            if(adminConfig.getAdminList()==null){
                adminConfig.setAdminList(List.of(student.getEmail()));
            }else {
                    adminConfig.getAdminList().add(student.getEmail());
            }
            adminConfigRepository.save(adminConfig);
        }
        return studentRepository.save(student);
    }

    // Update Student
    @Override
    public Student updateStudent(String id, StudentDto studentDTO) {
        if (studentDTO.getPhoneNo() != null) {
            validationService.phoneNoValidate(studentDTO.getPhoneNo());
        }

        Student student = getStudent(id);
        studentDTO.setEmail(null);
        studentDTO.setPassword(null);

        try {
            nullAwareBeans.copyProperties(student, studentDTO);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        student.setFullName(studentDTO.getFirstName().concat(studentDTO.getLastName()));

        if (student.getRole().contains(Roles.ADMIN)) {
            student.setStatus(Status.APPROVE);
        } else {
            student.setStatus(Status.DRAFT);
        }

        return studentRepository.save(student);
    }

    //Get specific student
    @Override
    public Student getStudent(String id) {
        return studentRepository.findByIdAndSoftDeleteFalse(id).orElseThrow(StudentIsNotExisted::new);
    }

    public Student getStudentByEmail(String email){
       return studentRepository.findByEmailAndSoftDeleteFalse(email).orElseThrow(StudentIsNotExisted::new);

    }

    // Get the details of all the students
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findBySoftDeleteFalse().orElseThrow(StudentIsNotExisted::new);
    }

    // Delete a student of specific id
    @Override
    public void deleteStudent(String id) {
        Student student = getStudent(id);

        if(student.getRole().contains(Roles.ADMIN)){
            throw new AdminCanNotBeDeleted();
        }
        student.setSoftDelete(true);
        studentRepository.save(student);
    }

    // Recover deleted student
    @Override
    public Student recoverStudent(String email){
        Student student = studentRepository.findTopByOrderByIdDesc(email).orElseThrow(StudentIsNotExisted::new);
        student.setSoftDelete(false);
        studentRepository.save(student);
        return student;
    }

    //Reset password
    @Override
    public void resetPassword(String id, PasswordDto passwordDTO) {
        Student student = getStudent(id);
        String pass = student.getPassword();
        if(student.getOldPasswordSet()==null){
            student.setOldPasswordSet(Set.of(pass));
        }else{
            student.getOldPasswordSet().add(pass);
        }

        if (passwordEncoder.matches(passwordDTO.getOldPassword(), pass)) {
            for(String old:student.getOldPasswordSet()){
                if(passwordEncoder.matches(passwordDTO.getNewPassword(),old)){
                    throw new SamePasswordException();
                }
            }

            validationService.passwordValidation(passwordDTO.getNewPassword());
            student.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
            studentRepository.save(student);
        } else {
            throw new WrongPasswordException();
        }
    }

    // Verify email for forget password
    @Override
    public void sendOTP(String email) {
        Student student = getStudentByEmail(email);
        int otp= Integer.parseInt(new DecimalFormat("000000").format(new Random().nextInt(999999)));

        EmailDto emailDto = new EmailDto();
        emailDto.setOtp(otp);

        String content = mustacheHelper.setMessageContent("otp.html", emailDto);
        emailPublisher.sendEmailNotification(new EmailDetails(email,"OTP for Password change", content));
        student.setOtp(otp);
        studentRepository.save(student);
    }

    // Verify otp and change password
    @Override
    public void verifyOTP(String email, String newPassword, int otp) {
        Student student = getStudentByEmail(email);
        String pass = student.getPassword();
        if(student.getOldPasswordSet()==null){
            student.setOldPasswordSet(Set.of(pass));
        }else{
            student.getOldPasswordSet().add(pass);
        }

        if(student.getOtp() != otp){
            throw new OTPNotMatchedException("OTP not matched");
        }

        for(String old:student.getOldPasswordSet()){
            if(passwordEncoder.matches(newPassword,old)){
                throw new SamePasswordException();
            }
        }

        validationService.passwordValidation(newPassword);
        student.setPassword(passwordEncoder.encode(newPassword));
        studentRepository.save(student);
    }

    @Override
    public List<Student> getStudentsByLetter(String letter){
        return studentRepository.searchByLetter(letter);
    }

    @Override
    public Page<Student> getStudentsByPageNo(PaginationDto pagination){
        return studentRepository.findAllByPageNo(pagination.getPageNo(),pagination.getPageSize());
    }

    @Override
    public List<Student> filterByRole(FilterDto filter){
        return studentRepository.filterByRole(filter.getRoles());
    }

    @Override
    public List<Student> sortByField(Fields field,Fields field2, Direction direction){
        if(direction==null){
            direction = Direction.ASC;
        }
        return studentRepository.sortByField(field,field2,direction);
    }
    @Override
    public Page<Student> filterSortAndPagination(PaginationApi paginationApi){
        if(paginationApi.getSort().getDirection()==null){
            paginationApi.getSort().setDirection(Direction.ASC);
        }
        return studentRepository.filterSortAndPagination(paginationApi);
    }

    public LocalDate getRandomDate(LocalDate startDate, LocalDate endDate) {
        long start = startDate.toEpochDay();
        long end = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(start, end + 1);
        return LocalDate.ofEpochDay(randomEpochDay);
    }

    @Override
    public void setRandomDate(LocalDate startDate, LocalDate endDate){
        List<Student> students = getAllStudents();
        students.forEach(student -> {
            LocalDate randomDate = getRandomDate(startDate, endDate);
            student.setRegistrationDate(randomDate);
            studentRepository.save(student);
        });
    }
}
