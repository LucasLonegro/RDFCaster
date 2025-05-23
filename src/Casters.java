import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Casters {

    private final static Map<String,String> ProgrammeNameToCode = new HashMap<>();

    private static String sanitize(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", " ")
                .replace("\r", "");
    }

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
        String courseInstance = sanitize(scanner.next());

        return List.of(("""
                ex:HoursLog_%s_%s      a ex:HoursLog ;
                ex:assignedHours  "%s"^^xsd:decimal ;
                ex:logs           ex:TeachingAssistant_%s ;
                ex:hasLog         ex:CourseInstance_%s .""").formatted(teacherId, courseInstance, hours, teacherId, courseInstance));
    }

    public static List<String> courseInstancesCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseCode = scanner.next();
        String studyPeriod = scanner.next();
        String academicYear = scanner.next();
        String instanceId = scanner.next();
        String examiner = sanitize(scanner.next());

        return List.of(("""
                        ex:CourseInstance_%s      a ex:CourseInstances ;
                        ex:instanceId     ex:%s ;
                        ex:academicYear           "%s" ;
                        ex:studyPeriod           "%s" ;
                        ex:instanceOf ex:Course_%s .""").formatted(instanceId, instanceId, academicYear, studyPeriod, courseCode),
                ("ex:SeniorTeacher_%s    ex:examinerIn ex:CourseInstance_%s .").formatted(examiner, instanceId));
    }

    public static List<String> coursePlanningsCaster(String line) {
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

    public static List<String> coursesCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseName = scanner.next();
        String courseCode = scanner.next();
        String credits = scanner.next();
        String level = scanner.next();
        String department = scanner.next();
        String division = scanner.next();
        String ownedBy = sanitize(scanner.next());

        return List.of(("""
                        ex:Course_%s      a ex:Courses ;
                                ex:courseCode     ex:%s ;
                                ex:courseName           "%s" ;
                                ex:level           "%s" ;
                                ex:ownedBy ex:Programme_%s ;
                                ex:isOfDivision ex:Division_%s ;
                                ex:isCourseOfDepartment ex:Department_%s ;
                                ex:credits           %s .
                ex:Division_%s      a ex:Division ;
                ex:belongsToDepartment ex:Department_%s ;
                ex:divisionName     "%s" .
                ex:Department_%s      a ex:Department ;
                ex:departmentName     "%s" .""").formatted(courseCode, courseCode, courseName, level, ProgrammeNameToCode.get(ownedBy), division, department, credits, division, department, division, department, department));
    }

    public static List<String> programmeCoursesCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String programmeCode = scanner.next();
        String studyYear = scanner.next();
        String academicYear = scanner.next();
        String course = scanner.next();
        String courseType = sanitize(scanner.next());

        return List.of(("""
                        ex:ProgrammeCourses_%s_%s a ex:ProgrammeCourses ;
                        ex:academicYear "%s" ;
                        ex:studyYear "%s" ;
                        ex:courseType "%s" .""").formatted(programmeCode, course, academicYear, studyYear, courseType),
                "ex:Programme_%s ex:ownsCourse ex:ProgrammeCourses_%s_%s .".formatted(programmeCode, programmeCode, course),
                "ex:ProgrammeCourses_%s_%s ex:isProgramme ex:Courses_%s .".formatted(programmeCode, course, course));
    }

    public static List<String> programmesCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String programmeName = scanner.next();
        String programmeCode = scanner.next();
        String departmentName = scanner.next();
        String director = sanitize(scanner.next());
        ProgrammeNameToCode.put(programmeName, programmeCode);

        return List.of(("""
                        ex:Programme_%s      a ex:Programmes ;
                                ex:programmeCode     ex:%s ;
                                ex:programmeName     "%s" .""").formatted(programmeCode, programmeCode, programmeName),
                ("ex:SeniorTeacher_%s    ex:directorIn ex:Programme_%s .").formatted(director, programmeCode),
                ("ex:Programme_%s    ex:isOfDepartment \"Department_%s\" .").formatted(programmeCode, departmentName));
    }

    public static List<String> registrationsCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseInstance = scanner.next();
        String studentId = scanner.next();
        String status = scanner.next();
        String grade = sanitize(scanner.next());

        if(grade.equals("")) grade = "-1";

        return List.of(("""
                ex:Registration_%s_%s      a ex:Registration ;
                ex:grade  "%s"^^xsd:decimal ;
                ex:status           "%s" ;
                ex:registeredTo           ex:CourseInstance_%s ;
                ex:registeredAs         ex:Students_%s .""").formatted(courseInstance, studentId, grade, status, courseInstance, studentId));
    }

    public static List<String> reportedHoursCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String courseCode = scanner.next();
        String teacherId = scanner.next();
        String hours = sanitize(scanner.next());

        return List.of(("""
                ex:HoursLog_%s_%s      a ex:HoursLog ;
                ex:reportedHours  "%s"^^xsd:decimal .
                """).formatted(teacherId, courseCode, hours));
    }

    public static List<String> seniorTeachersCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String teacherName = scanner.next();
        String teacherId = scanner.next();
        String department = scanner.next();
        String division = sanitize(scanner.next());


        return List.of(("""
                        ex:SeniorTeacher_%s      a ex:SeniorTeacher ;
                                ex:teacherId     ex:%s ;
                                ex:name     "%s" ;
                                ex:isInDivision ex:Division_%s ;
                                ex:isInDepartment ex:Department_%s .
                
                ex:Division_%s      a ex:Division ;
                ex:belongsToDepartment ex:Department_%s ;
                ex:divisionName     "%s" .
                ex:Department_%s      a ex:Department ;
                ex:departmentName     "%s" .""").formatted(teacherId, teacherId, teacherName, division, department, division, department, division, department, department));
    }

    public static List<String> studentsCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String studentName = scanner.next();
        String studentId = scanner.next();
        String programme = scanner.next();
        String year = scanner.next();
        String graduated = sanitize(scanner.next());

        if (graduated.equals("True")) graduated = "true";
        else if (graduated.equals("False")) graduated = "false";

        return List.of((""" 
                        ex:Students_%s      a ex:Students ;
                                ex:studentId     ex:%s ;
                                ex:graduated     "%s"^^xsd:boolean ;
                                ex:year     %s ;
                                ex:name     "%s" .""").formatted(studentId, studentId, graduated, year, studentName),
                ("ex:Student_%s    ex:isEnrolledIn ex:Programme_%s .").formatted(studentId, programme));
    }

    public static List<String> teachingAssistantsCaster(String line) {
        Scanner scanner = new Scanner(line).useDelimiter(",");
        String teacherName = scanner.next();
        String teacherId = scanner.next();
        String department = scanner.next();
        String division = sanitize(scanner.next());

        return List.of(("""
                 ex:TeachingAssistant_%s a ex:TeachingAssistant , ex:StaffMember ;
                     ex:teacherId "%s" ;
                     ex:name "%s" ;
                     ex:isInDepartment ex:Department_%s ;
                     ex:isInDivision ex:Division_%s .
                \s
                 ex:Students_%s ex:TAs ex:TeachingAssistant_%s .               \s
                \s
                 ex:Division_%s      a ex:Division ;
                 ex:belongsToDepartment ex:Department_%s ;
                 ex:divisionName     "%s" .
                 ex:Department_%s      a ex:Department ;
                 ex:departmentName     "%s" .""").formatted(teacherId, teacherId, teacherName, department, division, teacherId, teacherId, division, department, division, department, department));
    }
}
