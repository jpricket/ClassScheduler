import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class is the same as the ApacheHttpRestClient2 class, but with
 * fewer try/catch clauses, and fewer comments.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Reading courses from the cache...");
        final String cacheLocation = "c:\\users\\jpricket\\desktop\\classCache";
        CacheReader reader = new CacheReader(cacheLocation);
        List<CourseDescriptor> courses = reader.read();
        if (courses.size() == 0) {
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

        System.out.println("Writing courses to the cache...");
        CacheWriter writer = new CacheWriter(cacheLocation);
        writer.clearCache();
        writer.write(courses);
    }

}