package sde.virginia.edu.hw4;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoSession;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatalogServiceTest {

    @Mock
    Catalog catalog;
    @Mock
    Section section, section1, section2;
    @Mock
    Course course, course1;

    @Mock
    Lecturer lecturer, lecturer1;
    @Mock
    TimeSlot timeSlot, timeSlot1;
    @Mock
    Enrollment enroll, enroll1;



    @Test
    void addSectionAlreadyExists(){
        when(catalog.contains(section)).thenReturn(true);
        CatalogService cs = new CatalogService(catalog);
        assertEquals(cs.add(section), CatalogService.AddSectionResult.FAILED_SECTION_ALREADY_EXISTS);
        verify(catalog, times(0)).add(section);
    }

    @Test
    void addSectionSemesterMismatch(){
        when(catalog.contains(section)).thenReturn(false);
        when(section.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
        when(section1.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
        when(catalog.getSemester()).thenReturn(new Semester(Term.SPRING, 2023));
        CatalogService cs = new CatalogService(catalog);
        cs.add(section);
        assertEquals(cs.add(section1), CatalogService.AddSectionResult.FAILED_SEMESTER_MISMATCH);
        verify(catalog, times(0)).add(section1);

    }
    @Test
    void addSectionCRNConflict(){
        when(catalog.getSections()).thenReturn(Set.of(section1));
        lenient().when(catalog.contains(section)).thenReturn(false);
//        when(section.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
        when(section1.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
        when(catalog.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
//        when(section.getCourseRegistrationNumber()).thenReturn(1);
        when(section1.getCourseRegistrationNumber()).thenReturn(1);
        CatalogService cs = new CatalogService(catalog);
        assertEquals(CatalogService.AddSectionResult.FAILED_CRN_CONFLICT, cs.add(section1));
        verify(catalog, times(0)).add(section1);

    }
    @Test
    void addSectionLocationConflict(){
        when(catalog.getSections()).thenReturn(Set.of(section));
        Location location = new Location("a", "b", 1);
        lenient().when(catalog.contains(section)).thenReturn(false);
//        when(section.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
        when(section1.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
        when(catalog.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
        when(section.getCourseRegistrationNumber()).thenReturn(1);
        when(section1.getCourseRegistrationNumber()).thenReturn(2);
        when(section.getLocation()).thenReturn(location);
        when(section1.getLocation()).thenReturn(location);
        when(section.getTimeSlot()).thenReturn(timeSlot);
        when(section1.getTimeSlot()).thenReturn(timeSlot);
        CatalogService cs = new CatalogService(catalog);
        assertEquals(cs.add(section1), CatalogService.AddSectionResult.FAILED_LOCATION_CONFLICT);
        verify(catalog, times(0)).add(section1);

    }

    @Test
    void addSectionLecturerConflict(){
        when(catalog.getSections()).thenReturn(Set.of(section1));
        lenient().when(catalog.contains(section)).thenReturn(false);
        when(section.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
        when(section1.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
        when(catalog.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
        when(section.getCourseRegistrationNumber()).thenReturn(1);
        when(section1.getCourseRegistrationNumber()).thenReturn(2);
        when(section.getLocation()).thenReturn(new Location("b", "b", 1));
        when(section1.getLocation()).thenReturn(new Location("a", "b", 1));
        when(section.getTimeSlot()).thenReturn(timeSlot);
        when(section1.getTimeSlot()).thenReturn(timeSlot);
        when(section.getLecturer()).thenReturn(lecturer);
        when(section1.getLecturer()).thenReturn(lecturer);
        CatalogService cs = new CatalogService(catalog);
        cs.add(section1);
        assertEquals(cs.add(section), CatalogService.AddSectionResult.FAILED_LECTURER_CONFLICT);
        verify(catalog, times(0)).add(section);


    }
    @Test
    void addSectionEnrollmentNotEmpty(){
        when(catalog.getSections()).thenReturn(Set.of(section1));
        when(catalog.contains(section)).thenReturn(false);
        when(section.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
        when(catalog.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
        when(section.getCourseRegistrationNumber()).thenReturn(1);
        when(section1.getCourseRegistrationNumber()).thenReturn(2);
        when(section.getLocation()).thenReturn(new Location("a", "b", 1));
        when(section1.getTimeSlot()).thenReturn(timeSlot);
        when(section.getTimeSlot()).thenReturn(timeSlot1);
        when(section.getEnrollmentSize()).thenReturn(1);
        CatalogService cs = new CatalogService(catalog);
        assertEquals(cs.add(section), CatalogService.AddSectionResult.FAILED_ENROLLMENT_NOT_EMPTY);
        verify(catalog, times(0)).add(section);


    }
    @Test
    void addSectionSuccess(){
        when(catalog.getSections()).thenReturn(Set.of(section1));
        when(catalog.contains(section)).thenReturn(false);
        when(section.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
        when(catalog.getSemester()).thenReturn(new Semester(Term.FALL, 2023));
        when(section.getCourseRegistrationNumber()).thenReturn(1);
        when(section1.getCourseRegistrationNumber()).thenReturn(2);
        when(section.getLocation()).thenReturn(new Location("a", "b", 1));
        when(section.getTimeSlot()).thenReturn(timeSlot);
//        when(section.getLecturer()).thenReturn(lecturer);
        when(section.getEnrollmentSize()).thenReturn(0);
        CatalogService cs = new CatalogService(catalog);
        assertEquals(cs.add(section), CatalogService.AddSectionResult.SUCCESSFUL);
        verify(catalog).add(section);

    }
    @Test
    void closeAllSection(){
        when(catalog.getSections()).thenReturn(Set.of(section, section1));
        CatalogService cs = new CatalogService(catalog);
        cs.closeAllSection();
        verify(section).setEnrollmentStatus(EnrollmentStatus.CLOSED);
        verify(section1).setEnrollmentStatus(EnrollmentStatus.CLOSED);
    }
    @Test
    void closeSomeSections(){
        CatalogService cs = new CatalogService(catalog);
        when(catalog.getSections()).thenReturn(Set.of(section, section1));
        cs.closeAllSection();
        verify(section).setEnrollmentStatus(EnrollmentStatus.CLOSED);
        verify(section1).setEnrollmentStatus(EnrollmentStatus.CLOSED);
    }


    
}
