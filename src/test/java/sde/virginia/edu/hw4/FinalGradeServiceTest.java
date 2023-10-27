package sde.virginia.edu.hw4;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FinalGradeServiceTest {
    @Mock
    Section section;
    @Mock
    Map<Student, Grade> finalGrades;



    @Test
    void uploadFinalGradeA(){

    }

    @Test
    void uploadFinalGradeDrop(){

    }
    @Test
    void uploadFinalGradeFs(){

    }
    @Test
    void uploadFinalGradeStudentNotEnrolled(){

    }

}
