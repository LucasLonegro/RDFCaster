import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class RDFCaster {

    public static void cast(InputStream in, AsyncStringWriter out, Function<String, List<String>> rowCaster) {
        Scanner scanner = new Scanner(in).useDelimiter("\n");
        if (scanner.hasNext()) scanner.next(); // skip headers
        out.write(List.of());
        scanner.forEachRemaining((line) -> out.write(rowCaster.apply(line)));
    }

    public static void castFile(String inputPath, String outputPath, Function<String, List<String>> rowCaster) {
        try (InputStream in = new FileInputStream(inputPath);
             Writer out = new FileWriter(outputPath)) {
            out.write("""
                    @prefix ex:  <http://example.org/vocab#> .
                    @prefix xsd: <http://www.w3.org/2001/XMLSchema#> .
                    """);
            AsyncStringWriter asyncWriter = new AsyncStringWriter(out);
            cast(in, asyncWriter, rowCaster);
            asyncWriter.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        castFile("Resources/Assigned_Hours.csv", "assigned_hours.ttl", Casters::assignedHoursCaster);
        castFile("Resources/Course_Instances.csv", "course_instances.ttl", Casters::courseInstancesCaster);
        castFile("Resources/Course_plannings.csv", "course_plannings.ttl", Casters::coursePlanningsCaster);
        castFile("Resources/Programmes.csv", "programmes.ttl", Casters::programmesCaster);
        castFile("Resources/Courses.csv", "courses.ttl", Casters::coursesCaster);
        castFile("Resources/Programme_Courses.csv", "programme_courses.ttl", Casters::programmeCoursesCaster);
        castFile("Resources/Registrations.csv", "registrations.ttl", Casters::registrationsCaster);
        castFile("Resources/Reported_Hours.csv", "reported_hours.ttl", Casters::reportedHoursCaster);
        castFile("Resources/Senior_Teachers.csv", "senior_teachers.ttl", Casters::seniorTeachersCaster);
        castFile("Resources/Students.csv", "students.ttl", Casters::studentsCaster);
        castFile("Resources/Teaching_Assistants.csv", "teaching_assistants.ttl", Casters::teachingAssistantsCaster);
    }
}
