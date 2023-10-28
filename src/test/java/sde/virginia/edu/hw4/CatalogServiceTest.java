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
public class CatalogServiceTest {

    @Mock
    Catalog catalog;
    @Mock
    Section section, section1;
    @Mock
    Course course, course1;
    @Mock
    Semester semester, semester1;
    @Mock
    Lecturer lecturer, lecturer1;
    @Mock
    Location location, location1;
    @Mock
    TimeSlot timeSlot, timeSlot1;
    @Mock
    EnrollmentStatus enrollStat, enrollStat1;
    @Mock
    Enrollment enroll, enroll1;



    @Test
    void addSectionAlreadyExists(){
        when(catalog.contains(section)).thenReturn(true);
        CatalogService cs = new CatalogService(catalog);
        assertEquals(cs.add(section), CatalogService.AddSectionResult.FAILED_SECTION_ALREADY_EXISTS);
        assertFalse(catalog.contains(section));
    }

    @Test
    void addSectionSemesterMismatch(){
        when(catalog.contains(section)).thenReturn(false);
        when(section.getSemester()).thenReturn(semester);
        when(catalog.getSemester()).thenReturn(semester1);
        CatalogService cs = new CatalogService(catalog);
        cs.add(section);
        assertEquals(cs.add(section1), CatalogService.AddSectionResult.FAILED_SEMESTER_MISMATCH);

    }
    @Test
    void addSectionCRNConflict(){
        when(catalog.contains(section)).thenReturn(false);
        when(section.getSemester()).thenReturn(semester);
        when(catalog.getSemester()).thenReturn(semester);
        when(section.getCourseRegistrationNumber()).thenReturn(1);
        when(section1.getCourseRegistrationNumber()).thenReturn(1);
        CatalogService cs = new CatalogService(catalog);
        cs.add(section);
        assertEquals(cs.add(section1), CatalogService.AddSectionResult.FAILED_CRN_CONFLICT);

    }
    @Test
    void addSectionLocationConflict(){
        when(catalog.getSections()).thenReturn(Set.of(section, section1));
        when(catalog.contains(section)).thenReturn(false);
        when(section.getSemester()).thenReturn(semester);
        when(catalog.getSemester()).thenReturn(semester);
        when(section.getCourseRegistrationNumber()).thenReturn(1);
        when(section1.getCourseRegistrationNumber()).thenReturn(2);
        when(section.getLocation()).thenReturn(location);
        when(section1.getLocation()).thenReturn(location);
        when(section.getTimeSlot()).thenReturn(timeSlot);
        when(section1.getTimeSlot()).thenReturn(timeSlot);
        CatalogService cs = new CatalogService(catalog);
        cs.add(section);
        assertEquals(cs.add(section1), CatalogService.AddSectionResult.FAILED_LOCATION_CONFLICT);

    }

    @Test
    void addSectionLecturerConflict(){
        when(catalog.getSections()).thenReturn(Set.of(section, section1));
        when(catalog.contains(section)).thenReturn(false);
        when(section.getSemester()).thenReturn(semester);
        when(catalog.getSemester()).thenReturn(semester);
        when(section.getCourseRegistrationNumber()).thenReturn(1);
        when(section1.getCourseRegistrationNumber()).thenReturn(2);
        when(section.getLocation()).thenReturn(location);
        when(section1.getLocation()).thenReturn(location1);
        when(section.getTimeSlot()).thenReturn(timeSlot);
        when(section1.getTimeSlot()).thenReturn(timeSlot);
        when(section.getLecturer()).thenReturn(lecturer);
        when(section1.getLecturer()).thenReturn(lecturer);
        CatalogService cs = new CatalogService(catalog);
        cs.add(section1);
        assertEquals(cs.add(section), CatalogService.AddSectionResult.FAILED_LECTURER_CONFLICT);


    }
    @Test
    void addSectionEnrollmentNotEmpty(){
        when(catalog.getSections()).thenReturn(Set.of(section, section1));
        when(catalog.contains(section)).thenReturn(false);
        when(section.getSemester()).thenReturn(semester);
        when(catalog.getSemester()).thenReturn(semester);
        when(section.getCourseRegistrationNumber()).thenReturn(1);
        when(section.getLocation()).thenReturn(location);
        when(section.getTimeSlot()).thenReturn(timeSlot);
        when(section.getLecturer()).thenReturn(lecturer);
        when(section.getEnrollmentSize()).thenReturn(1);
        CatalogService cs = new CatalogService(catalog);
        assertEquals(cs.add(section), CatalogService.AddSectionResult.FAILED_ENROLLMENT_NOT_EMPTY);


    }
    @Test
    void addSectionSuccess(){
        when(catalog.getSections()).thenReturn(Set.of(section, section1));
        when(catalog.contains(section)).thenReturn(false);
        when(section.getSemester()).thenReturn(semester);
        when(catalog.getSemester()).thenReturn(semester);
        when(section.getCourseRegistrationNumber()).thenReturn(1);
        when(section.getLocation()).thenReturn(location);
        when(section.getTimeSlot()).thenReturn(timeSlot);
        when(section.getLecturer()).thenReturn(lecturer);
        when(section.getEnrollmentSize()).thenReturn(0);
        CatalogService cs = new CatalogService(catalog);
        assertEquals(cs.add(section), CatalogService.AddSectionResult.SUCCESSFUL);

    }
    @Test
    void closeAllSection(){
        section = new Section(1, 1, course, semester, location, timeSlot, lecturer, EnrollmentStatus.OPEN, enroll);
        section1 = new Section(2, 2, course1, semester, location, timeSlot, lecturer, EnrollmentStatus.OPEN, enroll);
        CatalogService cs = new CatalogService(catalog);
        when(catalog.getSections()).thenReturn(Set.of(section, section1));
        cs.closeAllSection();
        assertTrue(section.getEnrollmentStatus().equals(EnrollmentStatus.CLOSED));
        assertTrue(section1.getEnrollmentStatus().equals(EnrollmentStatus.CLOSED));
    }
    @Test
    void closeSomeSections(){
        section = new Section(1, 1, course, semester, location, timeSlot, lecturer, EnrollmentStatus.OPEN, enroll);
        section1 = new Section(2, 2, course1, semester, location, timeSlot, lecturer, EnrollmentStatus.CLOSED, enroll);
        CatalogService cs = new CatalogService(catalog);
        when(catalog.getSections()).thenReturn(Set.of(section, section1));
        cs.closeAllSection();
        assertTrue(section.getEnrollmentStatus().equals(EnrollmentStatus.CLOSED));
        assertTrue(section1.getEnrollmentStatus().equals(EnrollmentStatus.CLOSED));
    }


    
}
