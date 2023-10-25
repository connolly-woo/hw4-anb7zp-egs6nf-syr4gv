package sde.virginia.edu.hw4;

import java.util.*;

/**
 * //TODO: Extract the enrollment logic from {@link Section} into this class
 */

public class Enrollment {
    private int enrollmentCapacity;
    private int waitListCapacity;
    private Set<Student> enrolledStudents;
    private List<Student> waitListedStudents;
    private EnrollmentStatus enrollmentStatus;

    public Enrollment(int enrollmentCapacity, int waitListCapacity) {
        this(enrollmentCapacity, waitListCapacity, new HashSet<>(), new ArrayList<>(), EnrollmentStatus.OPEN);
    }

    protected Enrollment(int enrollmentCapacity, int waitListCapacity,
                      Set<Student> enrolledStudents, List<Student> waitListedStudents,
                      EnrollmentStatus enrollmentStatus) {
        if (enrollmentCapacity < 0 || waitListCapacity < 0 || enrolledStudents == null || waitListedStudents == null ||
        enrollmentStatus == null) {
            throw new IllegalArgumentException();
        }

        this.enrollmentCapacity = enrollmentCapacity;
        this.waitListCapacity = waitListCapacity;
        this.enrolledStudents = enrolledStudents;
        this.waitListedStudents = waitListedStudents;
        this.enrollmentStatus = enrollmentStatus;
    }


    public int getEnrollmentCapacity() {
        return enrollmentCapacity;
    }

    public void setEnrollmentCapacity(int updatedEnrollmentCapacity, int roomCapacity) {
        if(updatedEnrollmentCapacity < 0){
            throw new IllegalArgumentException("Enrollment capacity can't be negative");
        }
        if(roomCapacity < updatedEnrollmentCapacity){
            throw new IllegalArgumentException("Enrollment capacity can't be greater than room capacity");
        }
        this.enrollmentCapacity = updatedEnrollmentCapacity;
    }

    public int getEnrollementSize(){
        return enrolledStudents.size();
    }

    public boolean isEnrollmentFull(){
        return getEnrollementSize() >= enrollmentCapacity;
    }

    public boolean isEnrollmentOpen(){
        return enrollmentStatus == EnrollmentStatus.OPEN;
    }

    public void addStudentToEnrollment(Student student){
        if(!isEnrollmentOpen()){
            throw new IllegalStateException("Enrollment closed");
        }
        if(!isEnrollmentOpen()){
            throw new IllegalStateException(
                    "Enrollment not full. Cannot add student: " + student + " to wait list.");
        }
        if(enrolledStudents.contains(student)){
            throw new IllegalArgumentException("Student: " + student + " is already enrolled in the section." );
        }
        if(waitListedStudents.contains(student)){
            // add this one too?
            throw new IllegalArgumentException("Student: " + student + " is already wait listed in the section." );
        }
        enrolledStudents.add(student);
    }

    public void removeStudentFromEnrolled(Student student){
        if (!enrolledStudents.contains(student)) {
            throw new IllegalArgumentException(
                    "Student: " + student + " is not enrolled in the section.");
        }
        enrolledStudents.remove(student);
    }

    public int getWaitListCapacity(){
        if (waitListCapacity < 0) {
            throw new IllegalArgumentException("Enrollment Capacity cannot be negative");
        }
        return waitListCapacity;
    }

    public int getWaitListSize(){
        return waitListedStudents.size();
    }

    public boolean isWaitListFull(){
        return getWaitListSize() >= waitListCapacity;
    }

    public void setWaitListCapacity(int updatedWaitListCapacity){
        if(updatedWaitListCapacity < 0){
            throw new IllegalArgumentException("Wait list capacity can't be negative");
        }
        this.waitListCapacity = updatedWaitListCapacity;
    }

    public List<Student> getWaitListedStudents(){
        return Collections.unmodifiableList(waitListedStudents);
    }

    public Set<Student> getEnrolledStudents(){
        return Collections.unmodifiableSet(enrolledStudents);
    }

    public Student getFirstStudentOnWaitList(){
        if(waitListedStudents.isEmpty()){
            throw new IllegalStateException("Wait list is empty");
        }
        return waitListedStudents.get(0);
    }

    public void addStudentToWaitList(Student student){
        if(!isEnrollmentOpen()){
            throw new IllegalStateException("Enrollment closed");
        }
        if (!isEnrollmentFull()) {
            throw new IllegalStateException(
                    "Enrollment not full. Cannot add student: " + student + " to wait list for " + this);
        }
        if(isWaitListFull()){
            throw new IllegalStateException(
                    "Wait list is full. Cannot add student: " + student + " to wait list.");
        }
        if(enrolledStudents.contains(student)){
            throw new IllegalArgumentException("Student: " + student + " is already enrolled in the section." );
        }
        if(waitListedStudents.contains(student)){
            throw new IllegalArgumentException("Student: " + student + " is already wait listed in the section." );
        }
        waitListedStudents.add(student);
    }

    public boolean isStudentWaitListed(Student student){
        return waitListedStudents.contains(student);
    }

    public void removeStudentFromWaitList(Student student){
        if (!waitListedStudents.contains(student)) {
            throw new IllegalArgumentException(
                    "Student: " + student + " is not wait listed in the section.");
        }
        waitListedStudents.remove(student);
    }

    public EnrollmentStatus getEnrollmentStatus(){
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(EnrollmentStatus enrollmentStatus){
        this.enrollmentStatus = enrollmentStatus;
    }
    public boolean isStudentEnrolled(Student student){
        return enrolledStudents.contains(student);
    }
}
