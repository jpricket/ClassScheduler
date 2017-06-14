import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jpricket on 6/13/2017.
 */
public class CacheReader {
    private final String cacheFolder;

    public CacheReader(final String cacheFolder) {
        this.cacheFolder = cacheFolder;
    }

    public List<CourseDescriptor> read() throws IOException {
        final List<CourseDescriptor> courses = new ArrayList<CourseDescriptor>();
        final File folder = new File(cacheFolder);
        if (folder.exists()) {
            for(final File f: folder.listFiles()) {
                Collections.addAll(courses, read(f.getName()));
            }
        }
        return courses;
    }

    public CourseDescriptor[] read(final String filename) throws IOException {
        final List<String> lines = Files.readAllLines(Paths.get(cacheFolder, filename));
        final ObjectMapper mapper = new ObjectMapper();
        CourseDescriptor[] courses = mapper.readValue("[" + StringUtils.join(lines, ",\n") + "]", CourseDescriptor[].class);
        return courses;
    }
}
