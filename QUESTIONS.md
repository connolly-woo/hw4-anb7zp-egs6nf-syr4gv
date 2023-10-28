# Reflection Questions

Answer these questions thoroughly after completing the assignment.

## Question 1

UML Class Diagram - Following the directions on the second to last page of the directions, use draw.io to complete a UML class diagram. Save the diagram by using File -> Export as PNG and save as diagram.png. Add this png to the root directory of your project, and push it to GitHub.

## Question 2

In this assignment, you use several wrapper methods, such as those you added in Section.java as well as those already in Student.java. What is the benefit of these wrapper methods? What are the costs? Overall, would you envision yourself using wrapper methods in your own development? <br>
<br>
The benefit of using the wrapper methods, especially for Enrollment.java, helps to encapsulate the behavior of the class and makes it easier to implement in other parts of the code, such as within Section.java, where we were able to remove redundant or unnecessary parts of the code and replace it with more versatile, meaningful methods that were better able to be reused, and overall, we were able to shorten the code. In encapsulating the bahavior of Enrollement.java, methods in Section.java were able to be shortened, for instance in validateInputs(), where the following four lines:
        this.enrollmentCapacity = enrollmentCapacity; <br>
        this.waitListCapacity = waitListCapacity; <br>
        this.enrolledStudents = enrolledStudents; <br>
        this.waitListedStudents = waitListedStudents; <br>
Were able to be replaced by the following two:  <br>
        this.enrollmentStatus = enrollmentStatus;  <br>
        this.enrollment = enrollment; <br>
Because this.waitListedStudents is now included in enrollmentStatus. We were also able to remove many of the exceptions in the Section.java class because they were included in the new wrapper methods. In Student.java, we can change classes like Schedule or Transcript without worrying about how it may change Student, because that functions independently. However, the cost of wrapper methods is that they are not as direct as calling methods within the class or doing work directly in the class. Furthermore, with more wrappers, this adds more pathways between classes which can become complex. Overall, I would use simple wrappers, but likely would not use several with multiple layers or wrappers that would not be reused frequently, as it would add unnecessary complexity. Wrappers would be most effective for code reused multiple times.  
