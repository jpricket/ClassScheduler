import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by jpricket on 6/12/2017.
 */
public class CacheWriter {
    private final String cacheFolder;

    public CacheWriter(final String cacheFolder) {
        this.cacheFolder = cacheFolder;
    }

    public void write(final List<CourseDescriptor> courses) throws IOException {
        for(final CourseDescriptor cd: courses) {
            write(cd);
        }
    }

    public void write(final CourseDescriptor course) throws IOException {
        final String path = cacheFolder + "\\" + course.getSubject() + course.getCourse() + ".json";
        final FileWriter writer = new FileWriter(path, true);
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String json = mapper.writeValueAsString(course);
            writer.write(json + "\n");
        } finally {
            writer.close();
        }
    }

    public void clearCache() throws IOException {
        FileUtils.cleanDirectory(new File(cacheFolder));
    }
}
