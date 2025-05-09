import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class RDFCaster {

    public static void cast(InputStream in, AsyncStringWriter out, Function<String, List<String>> rowCaster) {
        Scanner scanner = new Scanner(in).useDelimiter("\n");
        if (scanner.hasNext()) scanner.next(); // skip headers

        scanner.forEachRemaining((line) -> out.write(rowCaster.apply(line)));
    }

    public static void castFile(String inputPath, String outputPath, Function<String, List<String>> rowCaster) {
        try (InputStream in = new FileInputStream(inputPath);
             Writer out = new FileWriter(outputPath)) {
            AsyncStringWriter asyncWriter = new AsyncStringWriter(out);
            cast(in, asyncWriter, rowCaster);
            asyncWriter.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        castFile("Assignment3/RDFCaster/Resources/Assigned_Hours.csv", "assigned_hours.ttl", Casters::assignedHoursCaster);
        castFile("Assignment3/RDFCaster/Resources/Course_Instances.csv", "course_instances.ttl", Casters::courseInstancesCaster);
        castFile("Assignment3/RDFCaster/Resources/Course_plannings.csv", "course_plannings.ttl", Casters::coursePlanningsCaster);
        castFile("Assignment3/RDFCaster/Resources/Courses.csv", "courses.ttl", Casters::coursesCaster);
        castFile("Assignment3/RDFCaster/Resources/Programme_Courses.csv", "programme_courses.ttl", Casters::programmeCoursesCaster);
        castFile("Assignment3/RDFCaster/Resources/Programmes.csv", "programmes.ttl", Casters::programmesCaster);
        castFile("Assignment3/RDFCaster/Resources/Registrations.csv", "registrations.ttl", Casters::registrationsCaster);
        castFile("Assignment3/RDFCaster/Resources/Reported_Hours.csv", "reported_hours.ttl", Casters::reportedHoursCaster);
        castFile("Assignment3/RDFCaster/Resources/Senior_Teachers.csv", "senior_teachers.ttl", Casters::seniorTeachersCaster);
        castFile("Assignment3/RDFCaster/Resources/Students.csv", "students.ttl", Casters::studentsCaster);
        castFile("Assignment3/RDFCaster/Resources/Teaching_Assistants.csv", "teaching_assistants.ttl", Casters::teachingAssistantsCaster);
    }
}
