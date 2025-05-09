import java.util.List;
import java.util.Scanner;

public class Casters {

    public static List<String> noopCaster(String line){
        return List.of(line);
    }

    public static List<String> assignedHoursCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseCode = scanner.next();
        String studyPeriod = scanner.next();
        String academicYear = scanner.next();

        String teacherId = scanner.next();
        String hours = scanner.next();

        String courseInstance = scanner.next();
        return List.of(("""
                ex:HoursLog_%s_%s      a ex:HoursLog ;
                ex:assignedHours  "%s"^^xsd:decimal ;
                ex:logs           ex:%s ;
                ex:hasLog         ex:%s .""").formatted(teacherId, courseInstance, hours, teacherId, courseInstance));
    }

    public static List<String> courseInstancesCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseCode = scanner.next();
        String studyPeriod = scanner.next();
        String academicYear = scanner.next();
        String instanceId = scanner.next();
        String examiner = scanner.next();

        return List.of(("""
                ex:CourseInstance_%s      a ex:CourseInstances ;
                ex:instanceId     ex:%s ;
                ex:academicYear           ex:%s ;
                ex:studyPeriod           ex:%s .""").formatted(instanceId,instanceId,academicYear,studyPeriod),
                ("ex:%s    ex:examinerIn ex:%s .").formatted(examiner,instanceId));
    }

    public static List<String> coursePlanningsCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String course = scanner.next();
        String plannedNumberOfStudents = scanner.next();
        String seniorHours = scanner.next();
        String assistantHours = scanner.next();

        return List.of(line);
    }

    public static List<String> coursesCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseName = scanner.next();
        String courseCode = scanner.next();
        String credits = scanner.next();
        String level = scanner.next();
        String department = scanner.next();
        String division = scanner.next();
        String ownedBy = scanner.next();

        return List.of(line);
    }

    public static List<String> programmeCoursesCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String programmeCode = scanner.next();
        String studyYear = scanner.next();
        String academicYear = scanner.next();
        String course = scanner.next();
        String courseType = scanner.next();

        return List.of(line);
    }

    public static List<String> programmesCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String programmeName = scanner.next();
        String programmeCode = scanner.next();
        String departmentName = scanner.next();
        String director = scanner.next();

        return List.of(line);
    }

    public static List<String> registrationsCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseInstance = scanner.next();
        String studentId = scanner.next();
        String status = scanner.next();
        String grade = scanner.next();

        return List.of(line);
    }

    public static List<String> reportedHoursCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseCode = scanner.next();
        String teacherId = scanner.next();
        String hours = scanner.next();

        return List.of(("""
                ex:HoursLog_%s_%s      a ex:HoursLog ;
                ex:reportedHours  "%s"^^xsd:decimal ;
                """).formatted(teacherId, courseCode, hours));
    }

    public static List<String> seniorTeachersCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String teacherName = scanner.next();
        String teacherId = scanner.next();
        String departmentName = scanner.next();
        String divisionName = scanner.next();

        return List.of(line);
    }

    public static List<String> studentsCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String studentName = scanner.next();
        String studentId = scanner.next();
        String programme = scanner.next();
        String year = scanner.next();
        String graduated = scanner.next();

        return List.of(line);
    }

    public static List<String> teachingAssistantsCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String teacherName = scanner.next();
        String teacherId = scanner.next();
        String departmentName = scanner.next();
        String divisionName = scanner.next();

        return List.of(line);
    }
}
