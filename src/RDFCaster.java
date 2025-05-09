import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class RDFCaster {

    public static void cast(InputStream in, AsyncStringWriter out, Function<String, List<String>> rowCaster) {
        Scanner scanner = new Scanner(in).useDelimiter("\n");
        scanner.next(); // ignore headers

        scanner.forEachRemaining((line) -> {
            out.write(rowCaster.apply(line));
        });
    }

    public static void castFile(String fileName, AsyncStringWriter asyncWriter, Function<String, List<String>> rowCaster) {
        try (InputStream in = new FileInputStream(fileName)) {
            cast(in, asyncWriter, Casters::noopCaster);
            asyncWriter.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try (Writer out = new FileWriter("out.txt");
             InputStream assignedHours = new FileInputStream("Resources/Assigned_Hours.csv");
             InputStream courseInstances = new FileInputStream("Resources/Course_Instances.csv");
             InputStream coursePlannings = new FileInputStream("Resources/Course_plannings.csv");
             InputStream courses = new FileInputStream("Resources/Courses.csv");
             InputStream programmeCourses = new FileInputStream("Resources/Programme_Courses.csv");
             InputStream programmes = new FileInputStream("Resources/Programmes.csv");
             InputStream registrations = new FileInputStream("Resources/Registrations.csv");
             InputStream reportedHours = new FileInputStream("Resources/Reported_Hours.csv");
             InputStream seniorTeachers = new FileInputStream("Resources/Senior_Teachers.csv");
             InputStream students = new FileInputStream("Resources/Students.csv");
             InputStream teachingAssistants = new FileInputStream("Resources/Teaching_Assistants.csv");) {
            AsyncStringWriter asyncWriter = new AsyncStringWriter(out);

            cast(assignedHours, asyncWriter, Casters::assignedHoursCaster);
            cast(courseInstances, asyncWriter, Casters::courseInstancesCaster);
            cast(coursePlannings, asyncWriter, Casters::coursePlanningsCaster);
            cast(courses, asyncWriter, Casters::coursesCaster);
            cast(programmeCourses, asyncWriter, Casters::programmeCoursesCaster);
            cast(programmes, asyncWriter, Casters::programmesCaster);
            cast(registrations, asyncWriter, Casters::registrationsCaster);
            cast(reportedHours, asyncWriter, Casters::reportedHoursCaster);
            cast(seniorTeachers, asyncWriter, Casters::seniorTeachersCaster);
            cast(students, asyncWriter, Casters::studentsCaster);
            cast(teachingAssistants, asyncWriter, Casters::teachingAssistantsCaster);

            asyncWriter.shutdown();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
