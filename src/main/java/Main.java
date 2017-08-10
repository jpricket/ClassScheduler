import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is the same as the ApacheHttpRestClient2 class, but with
 * fewer try/catch clauses, and fewer comments.
 */
public class Main {

    public static void main(String[] args) throws IOException {
//        if (args == null || args.length == 0) {
//            WebReader.getInstructorRatings();
//            return;
//        }


        System.out.println("Reading courses from the cache...");
        final String cacheLocation = "c:\\users\\jpricket\\desktop\\classCache";
        CacheReader reader = new CacheReader(cacheLocation);
        List<CourseDescriptor> courses = reader.read();
        boolean writeCache = false;
        if (courses.size() == 0) {
            writeCache = true;
            System.out.println("Cache is empty. Reading courses from Web...");
            // Load classes from web
            int lastCount = 0;
            String term = "201810";
            System.out.println("Gather subjects for term: " + term);
            List<Subject> subjects = WebReader.getSubjects(term); // Year is plus one if fall term; 10 == fall, 20 == spring, 60 == summer
            for (Subject s : subjects) {
                System.out.println("Gathering courses for term: " + term + " and subject: " + s.getName());
                try {
                    courses.addAll(WebReader.getCourses(term, s.getCode()));
                    System.out.println(s.getCode() + " course count = " + (courses.size() - lastCount));
                    lastCount = courses.size();
                } catch (Exception ex) {
                    System.out.println(s.getCode() + " Error: " + ex.getMessage());
                }
            }
            System.out.println("Total course count = " + courses.size());
        }
        System.out.println("Courses loaded.");

        HtmlSchedulePrinter printer = new HtmlSchedulePrinter();
        List<CourseDescriptor> filteredCourses =
                CourseFilter.create(courses)
                    .in("10558", "15530", "12505", "17786", "14097", "13220")
                    .go();
        for(CourseDescriptor course: filteredCourses) {
            printer.addClass(course, true);
        }
        // Add optional classes
        printer.addClasses(CourseFilter.create(courses)
                .where("subject", "=", "csc")
                .where("course", "=", "131")
                .go()
        );
        printer.printToFile("c:\\users\\jpricket\\desktop\\schedule.html");
        System.out.println("Schedule saved to \"c:\\\\users\\\\jpricket\\\\desktop\\\\schedule.html\"");

        filteredCourses =
                CourseFilter.create(courses)
                        .where("subject", "=", "csc")
                        .where("course", "=", "131")
                        .where("schedule.days", "contains", "mon")
                        //.where("seatsremaining", ">=", "1")
                        //.where("schedule.starttime", "<", "1500")
                        .go();

        System.out.println("Found: " + filteredCourses.size());
        for(CourseDescriptor course: filteredCourses) {
            System.out.println(course.toString());
        }


        if (writeCache) {
            System.out.println("Writing courses to the cache...");
            CacheWriter writer = new CacheWriter(cacheLocation);
            writer.clearCache();
            writer.write(courses);
        }
    }
}