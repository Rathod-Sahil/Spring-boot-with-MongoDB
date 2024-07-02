package com.first.service;

import com.first.decorator.*;
import com.first.enums.Fields;
import com.first.enums.Role;
import com.first.enums.UserStatus;
import com.first.exception.*;
import com.first.helper.AdminConfigEmailTemplateDataHelper;
import com.first.helper.MustacheHelper;
import com.first.model.AdminConfig;
import com.first.model.Student;
import com.first.model.TemplateModel;
import com.first.rabbitMQ.publisher.EmailPublisher;
import com.first.repository.AdminConfigRepository;
import com.first.repository.StudentRepository;
import com.first.utils.AdminConfigUtils;
import com.first.utils.NullAwareBeans;
import com.first.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
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
    private final AdminConfigUtils adminConfigUtils;
    private final AdminConfigRepository adminConfigRepository;

    // Create a single student
    @Override
    public Student createStudent(StudentDto sd) {
        Optional<Student> student1 = studentRepository.findByEmailAndSoftDeleteFalse(sd.getEmail());
        if (student1.isPresent()) {
            throw new StudentExistedException("Student is already existed");
        }

        // Validation
        validationService.nullFieldValidation(sd.getEmail(), sd.getPassword(), sd.getPhoneNo());
        validationService.emailValidate(sd.getEmail());
        validationService.passwordValidation(sd.getPassword());
        validationService.phoneNoValidate(sd.getPhoneNo());

        Student student = modelMapper.map(sd, Student.class);
        student.setFullName(String.join(" ", sd.getFirstName(), sd.getLastName()));

        boolean isAdmin = student.getRole().contains(Role.ADMIN);
        if (isAdmin) {
            student.setUserStatus(UserStatus.APPROVE);
        } else {
            student.setUserStatus(UserStatus.DRAFT);
        }
        student.setPassword(passwordEncoder.encode(sd.getPassword()));

        student.setRegistrationDate(LocalDate.now());

        EmailDto emailDto = new EmailDto();
        emailDto.setUserName(sd.getFirstName() + " " + sd.getLastName());

        AdminConfig adminConfig = adminConfigUtils.getAdminConfig();

        if (isAdmin) {
            if (adminConfig.getAdminList() == null) {
                adminConfig.setAdminList(Set.of(student.getEmail()));
            } else {
                adminConfig.getAdminList().add(student.getEmail());
            }
            adminConfigRepository.save(adminConfig);
        }

        TemplateModel emailTemplate = AdminConfigEmailTemplateDataHelper.getEmailTemplate(adminConfig);
        String content = MustacheHelper.setTemplateContent(emailTemplate.getBody(), emailDto);

        emailPublisher.sendEmailNotification(new EmailDetails(sd.getEmail(), emailTemplate.getSubject(), content));

        if (!isAdmin) {
            List<Student> adminList = studentRepository.filterByRole(List.of(Role.ADMIN.toString()));

            emailDto.setEmail(sd.getEmail());
            TemplateModel userRegisterTemplate = AdminConfigEmailTemplateDataHelper.getUserRegisterTemplate(adminConfig);

            adminList.forEach(admin -> {
                emailDto.setAdminName(admin.getFullName());
                String content1 = MustacheHelper.setTemplateContent(userRegisterTemplate.getBody(), emailDto);
                emailPublisher.sendEmailNotification(new EmailDetails(admin.getEmail(), emailTemplate.getSubject(), content1));
            });
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
        student.setFullName(String.join(" ", studentDTO.getFirstName(), studentDTO.getLastName()));

        if (student.getRole().contains(Role.ADMIN)) {
            student.setUserStatus(UserStatus.APPROVE);
        } else {
            student.setUserStatus(UserStatus.DRAFT);
        }

        return studentRepository.save(student);
    }

    //Get specific student
    @Override
    public Student getStudent(String id) {
        return studentRepository.findByIdAndSoftDeleteFalse(id).orElseThrow(StudentNotExistedException::new);
    }

    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmailAndSoftDeleteFalse(email).orElseThrow(StudentNotExistedException::new);

    }

    // Get the details of all the students
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findBySoftDeleteFalse().orElseThrow(StudentNotExistedException::new);
    }

    // Delete a student of specific id
    @Override
    public void deleteStudent(String id) {
        Student student = getStudent(id);

        if (student.getRole().contains(Role.ADMIN)) {
            throw new AdminDeletionException();
        }
        student.setSoftDelete(true);
        studentRepository.save(student);
    }

    // Recover deleted student
    @Override
    public Student recoverStudent(String email) {
        Student student = studentRepository.findTopByOrderByIdDesc(email).orElseThrow(StudentNotExistedException::new);
        student.setSoftDelete(false);
        studentRepository.save(student);
        return student;
    }

    //Reset password
    @Override
    public void resetPassword(String id, PasswordDto passwordDTO) {
        Student student = getStudent(id);
        String passwordInDB = student.getPassword();
        addRecentlyUsedPasswords(student, passwordInDB);

        if (passwordEncoder.matches(passwordDTO.getOldPassword(), passwordInDB)) {
            setNewPassword(student, passwordDTO.getNewPassword());
        } else {
            throw new WrongPasswordException("Enter correct old password");
        }
    }

    // Verify email for forget password
    @Override
    public void sendOTP(String email) {
        Student student = getStudentByEmail(email);
        int otp = Integer.parseInt(new DecimalFormat("000000").format(new Random().nextInt(999999)));

        EmailDto emailDto = new EmailDto();
        emailDto.setOtp(otp);

        AdminConfig adminConfig = adminConfigUtils.getAdminConfig();
        TemplateModel otpTemplate = AdminConfigEmailTemplateDataHelper.getOtpTemplate(adminConfig);
        String content = MustacheHelper.setTemplateContent(otpTemplate.getBody(), emailDto);
        emailPublisher.sendEmailNotification(new EmailDetails(email, otpTemplate.getSubject(), content));
        student.setOtp(otp);
        studentRepository.save(student);
    }

    // Verify otp and change password
    @Override
    public void verifyOTP(String email, String newPassword, int otp) {
        Student student = getStudentByEmail(email);
        String passwordInDb = student.getPassword();
        addRecentlyUsedPasswords(student, passwordInDb);

        if (student.getOtp() != otp) {
            throw new WrongOTPException("OTP not matched");
        }

        setNewPassword(student, newPassword);
    }

    public void addRecentlyUsedPasswords(Student student, String passwordInDb) {
        if (student.getRecentlyUsedPasswords() == null) {
            student.setRecentlyUsedPasswords(Set.of(passwordInDb));
        } else {
            student.getRecentlyUsedPasswords().add(passwordInDb);
        }
    }

    /**
     * Use this method for check new password with recently used password and then save it if it is not used before
     *
     * @param student
     * @param newPassword
     */
    public void setNewPassword(Student student, String newPassword) {
        student.getRecentlyUsedPasswords().forEach(recentlyUsedPassword -> {
            if (passwordEncoder.matches(newPassword, recentlyUsedPassword)) {
                throw new SamePasswordException("New password is already used by user once before");
            }
        });

        validationService.passwordValidation(newPassword);
        student.setPassword(passwordEncoder.encode(newPassword));
        studentRepository.save(student);
    }

    @Override
    public List<Student> getStudentsByLetter(String letter) {
        return studentRepository.searchByLetter(letter);
    }

    @Override
    public Page<Student> getStudentsByPageNo(PaginationDto pagination) {
        return studentRepository.findAllByPageNo(pagination.getPageNo(), pagination.getPageSize());
    }

    @Override
    public List<Student> filterByRole(FilterDto filter) {
        return studentRepository.filterByRole(filter.getRoles());
    }

    @Override
    public List<Student> sortByField(Fields field, Fields field2, Direction direction) {
        if (direction == null) {
            direction = Direction.ASC;
        }
        return studentRepository.sortByField(field, field2, direction);
    }

    @Override
    public Page<Student> filterSortAndPagination(SortFilterPagination sortFilterPagination) {
        if (sortFilterPagination.getSort().getDirection() == null) {
            sortFilterPagination.getSort().setDirection(Direction.ASC);
        }
        return studentRepository.filterSortAndPagination(sortFilterPagination);
    }

    public LocalDate getRandomDate(LocalDate startDate, LocalDate endDate) {
        long start = startDate.toEpochDay();
        long end = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(start, end + 1);
        return LocalDate.ofEpochDay(randomEpochDay);
    }

    @Override
    public void setRandomDate(LocalDate startDate, LocalDate endDate) {
        List<Student> students = getAllStudents();
        students.forEach(student -> {
            LocalDate randomDate = getRandomDate(startDate, endDate);
            student.setRegistrationDate(randomDate);
            studentRepository.save(student);
        });
    }

    @Override
    public Page<StudentDtoExt> findByRegistrationDate(PaginationApi2 paginationApi2) throws IOException {
        return studentRepository.findByRegistrationDate(paginationApi2);
    }
}
