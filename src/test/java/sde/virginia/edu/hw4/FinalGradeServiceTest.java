package sde.virginia.edu.hw4;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @Mock
    Grade grade;



    @Test
    void uploadFinalGradeStudentNotEnrolled(){
        when(section.isStudentEnrolled(student)).thenReturn(false);
        FinalGradesService FGS = new FinalGradesService();

        assertThrows(IllegalArgumentException.class, () -> FGS.uploadFinalGrades(section, finalGrades));

    }

}
