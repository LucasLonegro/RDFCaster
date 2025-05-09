import java.util.List;
import java.util.Scanner;

public class Casters {

    public static List<String> noopCaster(String line) {
        return List.of(line);
    }

    public static List<String> assignedHoursCaster(String line) {
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

    public static List<String> courseInstancesCaster(String line) {
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
                        ex:studyPeriod           ex:%s .""").formatted(instanceId, instanceId, academicYear, studyPeriod),
                ("ex:%s    ex:examinerIn ex:%s .").formatted(examiner, instanceId),
                ("ex:%s    ex:instanceOf ex:%s .").formatted(instanceId, courseCode));
    }

    public static List<String> coursePlanningsCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String course = scanner.next();
        String plannedNumberOfStudents = scanner.next();
        String seniorHours = scanner.next();
        String assistantHours = scanner.next();

        return List.of(("""
                ex:CourseInstance_%s      a ex:CourseInstances ;
                ex:planningNumStudents     "%s"^^xsd:integer ;
                ex:assistantHours           "%s"^^xsd:decimal ;
                ex:seniorHours           "%s"^^xsd:decimal .""").formatted(course, plannedNumberOfStudents, assistantHours, seniorHours));
    }

    public static List<String> coursesCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseName = scanner.next();
        String courseCode = scanner.next();
        String credits = scanner.next();
        String level = scanner.next();
        String department = scanner.next();
        String division = scanner.next();
        String ownedBy = scanner.next();

        return List.of(("""
                        ex:Course_%s      a ex:Courses ;
                        ex:courseCode     ex:%s ;
                        ex:courseName           ex:%s ;
                        ex:level           ex:%s ;
                        ex:credits           ex:%s .""").formatted(courseCode, courseCode, courseName, level, credits),
                ("ex:%s    ex:isOfDivision ex:%s .").formatted(courseCode, division),
                ("ex:%s    ex:belongsToDivision ex:%s .").formatted(department, division), // note: this will create duplicated triples. GraphDB automatically deduplicates them, the performance overhead is negligible compared to doing filtered inserts
                ("ex:%s    ex:ownedBy ex:%s .").formatted(courseCode, ownedBy));
    }

    public static List<String> programmeCoursesCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String programmeCode = scanner.next();
        String studyYear = scanner.next();
        String academicYear = scanner.next();
        String course = scanner.next();
        String courseType = scanner.next();

        return List.of(line);
    }

    public static List<String> programmesCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String programmeName = scanner.next();
        String programmeCode = scanner.next();
        String departmentName = scanner.next();
        String director = scanner.next();

        return List.of(("""
                        ex:Programme_%s      a ex:Programmes ;
                        ex:programmeCode     ex:%s ;
                        ex:programmeName     ex:%s .""").formatted(programmeCode, programmeCode, programmeName),
                ("ex:%s    ex:directorIn ex:%s .").formatted(programmeCode, director),
                ("ex:%s    ex:isOfDepartment ex:%s .").formatted(programmeCode, departmentName));
    }

    public static List<String> registrationsCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseInstance = scanner.next();
        String studentId = scanner.next();
        String status = scanner.next();
        String grade = scanner.next();

        return List.of(("""
                ex:Registration_%s_%s      a ex:Registration ;
                ex:grade  "%s"^^xsd:decimal ;
                ex:status           ex:%s ;
                ex:registeredTo           ex:%s ;
                ex:registeredAs         ex:%s .""").formatted(courseInstance, studentId, grade, status, courseInstance, studentId));
    }

    public static List<String> reportedHoursCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseCode = scanner.next();
        String teacherId = scanner.next();
        String hours = scanner.next();

        return List.of(("""
                ex:HoursLog_%s_%s      a ex:HoursLog ;
                ex:reportedHours  "%s"^^xsd:decimal ;
                """).formatted(teacherId, courseCode, hours));
    }

    public static List<String> seniorTeachersCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String teacherName = scanner.next();
        String teacherId = scanner.next();
        String departmentName = scanner.next();
        String divisionName = scanner.next();

        return List.of(("""
                        ex:SeniorTeacher_%s      a ex:Programmes ;
                        ex:teacherId     ex:%s ;
                        ex:name     ex:%s .""").formatted(teacherId, teacherId, teacherName),
                ("ex:%s    ex:isInDivision ex:%s .").formatted(teacherId, divisionName));
    }

    public static List<String> studentsCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String studentName = scanner.next();
        String studentId = scanner.next();
        String programme = scanner.next();
        String year = scanner.next();
        String graduated = scanner.next();

        return List.of(line);
    }

    public static List<String> teachingAssistantsCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String teacherName = scanner.next();
        String teacherId = scanner.next();
        String departmentName = scanner.next();
        String divisionName = scanner.next();

        return List.of(line);
    }
}
