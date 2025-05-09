import java.util.List;
import java.util.Scanner;

public class Casters {

    private static String sanitize(String value) {
        return value
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", " ")
            .replace("\r", "");
    }

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
        String courseInstance = sanitize(scanner.next());

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
        String examiner = sanitize(scanner.next());

        return List.of(("""
                ex:CourseInstance_%s a ex:CourseInstances ;
                    ex:instanceId "%s" ;
                    ex:academicYear "%s" ;
                    ex:studyPeriod "%s" ;
                    ex:hasCourse ex:Course_%s .
                    """).formatted(instanceId, instanceId, academicYear, studyPeriod, courseCode),
                    ("ex:SeniorTeacher_%s ex:examinerIn ex:CourseInstance_%s .").formatted(examiner, instanceId));
}

    public static List<String> coursePlanningsCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String course = scanner.next();
        String plannedNumberOfStudents = scanner.next();
        String seniorHours = scanner.next();
        String assistantHours = sanitize(scanner.next());

        return List.of(("""
                ex:CourseInstance_%s      a ex:CourseInstances ;
                ex:planningNumStudents     "%s"^^xsd:integer ;
                ex:assistantHours           "%s"^^xsd:decimal ;
                ex:seniorHours           "%s"^^xsd:decimal .""").formatted(course, plannedNumberOfStudents, assistantHours, seniorHours));
    }

    public static List<String> coursesCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseName = scanner.next();
        String courseCode = scanner.next();
        String credits = scanner.next();
        String level = scanner.next();
        String department = scanner.next();
        String division = scanner.next();
        String ownedBy = sanitize(scanner.next());

        return List.of(("""
                ex:Course_%s a ex:Course ;
                    ex:courseCode "%s" ;
                    ex:courseName "%s" ;
                    ex:credits "%s"^^xsd:decimal ;
                    ex:level "%s" ;
                    ex:isOfDepartment "%s" ;
                    ex:isOfDivision "%s" ;
                    ex:ownedBy ex:Programme_%s .""").formatted(courseCode, courseCode, courseName, credits, level, department, division, ownedBy));
    }

    public static List<String> programmeCoursesCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String programmeCode = scanner.next();
        String studyYear = scanner.next();
        String academicYear = scanner.next();
        String course = scanner.next();
        String courseType = sanitize(scanner.next());

        return List.of(("""
                ex:Programme_%s ex:hasProgrammeCourse [
                    a ex:ProgrammeCourse ;
                    ex:course ex:Course_%s ;
                    ex:academicYear "%s" ;
                    ex:studyYear "%s" ;
                    ex:courseType "%s"
                ] .""").formatted(programmeCode, course, academicYear, studyYear, courseType));
    }

    public static List<String> programmesCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String programmeName = scanner.next();
        String programmeCode = scanner.next();
        String departmentName = scanner.next();
        String director = sanitize(scanner.next());

        return List.of(("""
            ex:Programme_%s a ex:Programme ;
                ex:programmeCode "%s" ;
                ex:programmeName "%s" ;
                ex:dept "%s" ;
                ex:directedBy ex:SeniorTeacher_%s .
                ex:SeniorTeacher_%s a ex:SeniorTeacher ;
                ex:teacherId "%s" .""").formatted(programmeCode, programmeCode, programmeName, departmentName, director, director, director));
    }

    public static List<String> registrationsCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseInstance = scanner.next();
        String studentId = scanner.next();
        String status = scanner.next();
        String grade = sanitize(scanner.next());

        return List.of(("""
            ex:Student_%s ex:registeredIn ex:CourseInstance_%s .
            ex:Student_%s ex:registrationStatus "%s" .
            ex:Student_%s ex:grade "%s" .""").formatted(studentId, courseInstance, studentId, status, studentId, grade));
    }

    public static List<String> reportedHoursCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseCode = scanner.next();
        String teacherId = scanner.next();
        String hours = sanitize(scanner.next());

        return List.of(("""
                ex:HoursLog_%s_%s      a ex:HoursLog ;
                ex:reportedHours  "%s"^^xsd:decimal .""").formatted(teacherId, courseCode, hours));
    }

    public static List<String> seniorTeachersCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String teacherName = scanner.next();
        String teacherId = scanner.next();
        String departmentName = scanner.next();
        String divisionName = sanitize(scanner.next());

        return List.of(("""
            ex:SeniorTeacher_%s a ex:SeniorTeacher , ex:StaffMember ;
                ex:teacherId "%s" ;
                ex:name "%s" ;
                ex:isOfDepartment "%s" ;
                ex:isOfDivision "%s" .""").formatted(teacherId, teacherId, teacherName, departmentName, divisionName));
    }

    public static List<String> studentsCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String studentName = scanner.next();
        String studentId = scanner.next();
        String programme = scanner.next();
        String year = scanner.next();
        String graduated = sanitize(scanner.next());

        return List.of((""" 
        ex:Student_%s a ex:Student ;
            ex:studentId "%s" ;
            ex:name "%s" ;
            ex:year "%s"^^xsd:decimal ;
            ex:graduated "%s"^^xsd:boolean ;
            ex:enrolledIn ex:Programme_%s .
        """).formatted(studentId, studentId, studentName, year, graduated, programme));
    }

    public static List<String> teachingAssistantsCaster(String line){
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String teacherName = scanner.next();
        String teacherId = scanner.next();
        String departmentName = scanner.next();
        String divisionName = sanitize(scanner.next());

        return List.of((""" 
        ex:TeachingAssistant_%s a ex:TeachingAssistant , ex:StaffMember ;
            ex:teacherId "%s" ;
            ex:name "%s" ;
            ex:isOfDepartment "%s" ;
            ex:isOfDivision "%s" .
        """).formatted(teacherId, teacherId, teacherName, departmentName, divisionName));
    }
}
