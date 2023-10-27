package sde.virginia.edu.hw4;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {
    private RegistrationService registrationService;
    @Mock
    Student student;
    @Mock
    Section section;


    @Test
    void registerSuccess(){

    }
    @Test
    void registerAlreadyEnrolled(){

    }
    @Test
    void registerEnrollmentClosed(){

    }
    @Test
    void registerSectionFull(){

    }
    @Test
    void registerScheduleConflict(){

    }
    @Test
    void registerPrerequisiteNotMet(){

    }
    @Test
    void registerCreditLimitExceeded(){

    }
    @Test
    void registerEnrollSuccess(){

    }
    @Test
    void registerWaitlistSuccess(){

    }

    @Test
    void dropNotEnrolledorWaitlisted(){

    }
    @Test
    void dropWaitlisted(){

    }
    @Test
    void dropEnrolledFullClassWithWaitlist(){

    }
    @Test
    void dropEnrolledNotFullClass(){

    }




}
