package sde.virginia.edu.hw4;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {
    private RegistrationService registrationService;
    @Mock
    Student student;
    @Mock
    Section section, section1;
    @Mock
     Course course, course1;
    @Mock
    Prerequisite prerequisite;
    @Mock
    TimeSlot timeslot, timeslot1;


    @Test
    void registerSuccessEnroll(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(student.isWaitListedInSection(section)).thenReturn(false);
        when(section.isEnrollmentOpen()).thenReturn(true);
        when(section.isEnrollmentFull()).thenReturn(false);
//        when(section.isWaitListFull()).thenReturn(false);
        when(student.getEnrolledSections()).thenReturn(Set.of(section1));
        when(section.getTimeSlot()).thenReturn(timeslot);
        when(section1.getTimeSlot()).thenReturn(timeslot1);
        when(timeslot.overlapsWith(timeslot1)).thenReturn(false);
        when(section1.getCourse()).thenReturn(course1);
        when(section.getCourse()).thenReturn(course);
        when(course.getPrerequisite()).thenReturn(prerequisite);
        when(prerequisite.isSatisfiedBy(student)).thenReturn(true);
        when(student.getCreditLimit()).thenReturn(17);
        when(course.getCreditHours()).thenReturn(3);
        when(course1.getCreditHours()).thenReturn(3);
        RegistrationService rs = new RegistrationService();
        assertEquals(rs.register(student, section), RegistrationService.RegistrationResult.SUCCESS_ENROLLED);
        verify(section).addStudentToEnrollment(student);
        verify(student).addEnrolledSection(section);



    }

    @Test
    void registerWaitListSuccess(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(student.isWaitListedInSection(section)).thenReturn(false);
        when(section.isEnrollmentOpen()).thenReturn(true);
        when(section.isEnrollmentFull()).thenReturn(true);
        when(section.isWaitListFull()).thenReturn(false);
        when(student.getEnrolledSections()).thenReturn(Set.of(section1));
        when(section.getTimeSlot()).thenReturn(timeslot);
        when(section1.getTimeSlot()).thenReturn(timeslot1);
        when(timeslot.overlapsWith(timeslot1)).thenReturn(false);
        when(section1.getCourse()).thenReturn(course1);
        when(section.getCourse()).thenReturn(course);
        when(course.getPrerequisite()).thenReturn(prerequisite);
        when(prerequisite.isSatisfiedBy(student)).thenReturn(true);
        when(student.getCreditLimit()).thenReturn(17);
        when(course.getCreditHours()).thenReturn(3);
        when(course1.getCreditHours()).thenReturn(3);
        RegistrationService rs = new RegistrationService();
        assertEquals(rs.register(student, section), RegistrationService.RegistrationResult.SUCCESS_WAIT_LISTED);
        verify(student).addWaitListedSection(section);
        verify(section).addStudentToWaitList(student);
    }
    @Test
    void registerAlreadyEnrolled(){
        when(student.isEnrolledInSection(section)).thenReturn(true);
        RegistrationService rs = new RegistrationService();
        assertEquals(rs.register(student, section), RegistrationService.RegistrationResult.FAILED_ALREADY_IN_COURSE);
        verify(student, times(0)).addWaitListedSection(section);
        verify(section, times(0)).addStudentToEnrollment(student);

    }
    @Test
    void registerEnrollmentClosed(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(student.isWaitListedInSection(section)).thenReturn(false);
        when(section.isEnrollmentOpen()).thenReturn(false);
        RegistrationService rs = new RegistrationService();
        assertEquals(rs.register(student, section), RegistrationService.RegistrationResult.FAILED_ENROLLMENT_CLOSED);
        verify(student, times(0)).addWaitListedSection(section);
        verify(section, times(0)).addStudentToEnrollment(student);

    }
    @Test
    void registerSectionFull(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(student.isWaitListedInSection(section)).thenReturn(false);
        when(section.isEnrollmentOpen()).thenReturn(true);
        when(section.isEnrollmentFull()).thenReturn(true);
        when(section.isWaitListFull()).thenReturn(true);
        RegistrationService rs = new RegistrationService();
        assertEquals(rs.register(student, section), RegistrationService.RegistrationResult.FAILED_SECTION_FULL);
        verify(student, times(0)).addWaitListedSection(section);
        verify(section, times(0)).addStudentToEnrollment(student);

    }
    @Test
    void registerScheduleConflict(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(student.isWaitListedInSection(section)).thenReturn(false);
        when(section.isEnrollmentOpen()).thenReturn(true);
        when(section.isEnrollmentFull()).thenReturn(false);
//        when(section.isWaitListFull()).thenReturn(false);
        when(student.getEnrolledSections()).thenReturn(Set.of(section1));
        when(section.getTimeSlot()).thenReturn(timeslot);
        when(section1.getTimeSlot()).thenReturn(timeslot1);
        when(timeslot.overlapsWith(timeslot1)).thenReturn(true);
        RegistrationService rs = new RegistrationService();
        assertEquals(rs.register(student, section), RegistrationService.RegistrationResult.FAILED_SCHEDULE_CONFLICT);
        verify(student, times(0)).addWaitListedSection(section);
        verify(section, times(0)).addStudentToEnrollment(student);

    }
    @Test
    void registerPrerequisiteNotMet(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(student.isWaitListedInSection(section)).thenReturn(false);
        when(section.isEnrollmentOpen()).thenReturn(true);
        when(section.isEnrollmentFull()).thenReturn(false);
//        when(section.isWaitListFull()).thenReturn(false);
        when(student.getEnrolledSections()).thenReturn(Set.of(section1));
        when(section.getTimeSlot()).thenReturn(timeslot);
        when(section1.getTimeSlot()).thenReturn(timeslot1);
        when(timeslot.overlapsWith(timeslot1)).thenReturn(false);
//        when(section1.getCourse()).thenReturn(course1);
        when(section.getCourse()).thenReturn(course);
        when(course.getPrerequisite()).thenReturn(prerequisite);
        when(prerequisite.isSatisfiedBy(student)).thenReturn(false);
        RegistrationService rs = new RegistrationService();
        assertEquals(rs.register(student, section), RegistrationService.RegistrationResult.FAILED_PREREQUISITE_NOT_MET);
        verify(student, times(0)).addWaitListedSection(section);
        verify(section, times(0)).addStudentToEnrollment(student);

    }
    @Test
    void registerCreditLimitExceeded(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(student.isWaitListedInSection(section)).thenReturn(false);
        when(section.isEnrollmentOpen()).thenReturn(true);
        when(section.isEnrollmentFull()).thenReturn(true);
        when(section.isWaitListFull()).thenReturn(false);
        when(student.getEnrolledSections()).thenReturn(Set.of(section1));
        when(section.getTimeSlot()).thenReturn(timeslot);
        when(section1.getTimeSlot()).thenReturn(timeslot1);
        when(timeslot.overlapsWith(timeslot1)).thenReturn(false);
        when(section1.getCourse()).thenReturn(course1);
        when(section.getCourse()).thenReturn(course);
        when(course.getPrerequisite()).thenReturn(prerequisite);
        when(prerequisite.isSatisfiedBy(student)).thenReturn(true);
        when(student.getCreditLimit()).thenReturn(5);
        when(course.getCreditHours()).thenReturn(3);
        when(course1.getCreditHours()).thenReturn(3);
        RegistrationService rs = new RegistrationService();
        assertEquals(rs.register(student, section), RegistrationService.RegistrationResult.FAILED_CREDIT_LIMIT_VIOLATION);
        verify(student, times(0)).addWaitListedSection(section);
        verify(section, times(0)).addStudentToEnrollment(student);


    }

    @Test
    void dropNotEnrolledorWaitlisted(){
        when(section.isStudentEnrolled(student)).thenReturn(false);
        when(section.isStudentWaitListed(student)).thenReturn(false);
        RegistrationService rs = new RegistrationService();
        assertEquals(rs.drop(student, section), false);
        verify(section, times(0)).removeStudentFromEnrolled(student);
        verify(student, times(0)).removeEnrolledSection(section);
        verify(section, times(0)).removeStudentFromWaitList(student);
        verify(student, times(0)).removeWaitListedSection(section);
    }
    @Test
    void dropWaitlisted(){
        when(section.isStudentEnrolled(student)).thenReturn(false);
        when(section.isStudentWaitListed(student)).thenReturn(true);
        RegistrationService rs = new RegistrationService();
        assertEquals(rs.drop(student, section), true);
        verify(section).removeStudentFromWaitList(student);
        verify(student).removeWaitListedSection(section);

    }
    @Test
    void dropEnrolled(){
        when(section.isStudentEnrolled(student)).thenReturn(true);
        when(section.isStudentWaitListed(student)).thenReturn(false);
        RegistrationService rs = new RegistrationService();
        assertEquals(rs.drop(student, section), true);
        verify(section).removeStudentFromEnrolled(student);
        verify(student).removeEnrolledSection(section);

    }




}
