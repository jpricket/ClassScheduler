import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CacheWriter {
    private final String cacheFolder;

    public CacheWriter(final String cacheFolder) throws IOException {
        this.cacheFolder = cacheFolder;
        Files.createDirectories(Paths.get(cacheFolder));
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
