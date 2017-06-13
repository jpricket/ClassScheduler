import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by jpricket on 6/12/2017.
 */
public class CacheWriter {
    private String cacheFolder;

    public CacheWriter(String cacheFolder) {
        this.cacheFolder = cacheFolder;
    }

    public void write(List<CourseDescriptor> courses) throws IOException {
        for(CourseDescriptor cd: courses) {
            write(cd);
        }
    }

    public void write(CourseDescriptor course) throws IOException {
        String path = cacheFolder + "\\" + course.getSubject() + course.getCourse() + ".json";
        FileWriter writer = new FileWriter(path, true);
        try {
            writer.write("{" +
                    "subject:" + course.getSubject() + "\n" +
                    "course:" + course.getCourse() + "\n" +
                    "refNumber:" + course.getRefNumber() + "\n" +
                    "creditHours:" + course.getCreditHours() + "\n" +
                    "title:" + course.getTitle() + "\n" +
                    "instructor:" + course.getInstructor() + "\n" +
                    "seatsRemaining:" + course.getSeatsRemaining() + "\n" +
                    "waitListCapacity:" + course.getWaitListCapacity() + "\n" +
                    "waitListActual:" + course.getWaitListActual() + "\n" +
                    "waitListRemaining:" + course.getWaitListRemaining() + "\n" +
                    "dates:" + course.getDates() + "\n" +
                    "location:" + course.getLocation() + "\n" +
                    "section:" + course.getSection() + "\n" +
                    "session:" + course.getSession() + "\n" +
                    "campus:" + course.getCampus() + "\n" +
                    "reservedSectionRemaining:" + course.getReservedSectionRemaining() + "\n" +
                    "attribute:" + course.getAttribute() + "\n" +
                    "schedule:" + course.getSchedule().toString() + "\n" +
                    "}");
        } finally {
            writer.close();
        }
    }
}
