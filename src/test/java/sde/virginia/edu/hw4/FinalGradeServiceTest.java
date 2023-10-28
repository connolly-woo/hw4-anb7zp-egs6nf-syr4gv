package sde.virginia.edu.hw4;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Map;
import java.util.Set;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FinalGradeServiceTest {
    @Mock
    Section section;
    @Mock
    Map<Student, Grade> finalGrades;
    @Mock
    Student student, student1;


    @Test
    void uploadFinalGrade(){
        when(finalGrades.keySet()).thenReturn(Set.of(student));
        when(finalGrades.get(student)).thenReturn(Grade.A);
        when(section.isStudentEnrolled(student)).thenReturn(true);
        FinalGradesService FGS = new FinalGradesService();
        FGS.uploadFinalGrades(section, finalGrades);
        verify(student).addGrade(section, Grade.A);
    }

    @Test
    void uploadFinalGradeStudentNotEnrolled(){
        when(section.isStudentEnrolled(student)).thenReturn(false);
        finalGrades = Map.of(student, Grade.A);
        FinalGradesService FGS = new FinalGradesService();
        assertThrows(IllegalArgumentException.class, () -> FGS.uploadFinalGrades(section, finalGrades));

    }

}
