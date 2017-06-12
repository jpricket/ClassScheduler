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
        int lastCount = 0;
        String term = "201810";
        System.out.println("Gather subjects for term: " + term);
        List<Subject> subjects = getSubjects(term); // Year is plus one if fall term; 10 == fall, 20 == spring, 60 == summer
        List<CourseDescriptor> courses = new ArrayList<CourseDescriptor>();
        for(Subject s: subjects) {
            System.out.println("Gathering courses for term: " + term + " and subject: " + s.getName());
            try {
                courses.addAll(getCourses(term, s.getCode()));
                System.out.println(s.getCode() + " course count = " + (courses.size() - lastCount));
                lastCount = courses.size();
            } catch(Exception ex) {
                System.out.println(s.getCode() + " Error: " + ex.getMessage());
            }
        }
        System.out.println("Total course count = " + courses.size());

        //for(CourseDescriptor cd: courses) {
        //    if (cd.getSubject().contains("BIO")) {
        //        System.out.println(cd);
        //    }
        //}
    }

    private static List<CourseDescriptor> getCourses(String term, String subjectCode) throws IOException {
        List<CourseDescriptor> courses = new ArrayList<CourseDescriptor>();
        Document doc = Jsoup.connect("https://seanet.uncw.edu/TEAL/swkfccl.P_GetCrse")
                .requestBody("term_in=" + term +
                        "&sel_subj=dummy&sel_day=dummy&sel_schd=dummy&sel_insm=dummy&sel_camp=dummy&sel_levl=dummy&sel_sess=dummy" +
                        "&sel_instr=dummy&sel_ptrm=dummy&sel_attr=dummy" +
                        "&sel_subj=" + subjectCode +
                        "&sel_crse=&sel_title=&sel_schd=%25&sel_insm=%25&sel_from_cred=&sel_to_cred=&sel_camp=%25" +
                        "&sel_levl=%25&sel_ptrm=%25&sel_instr=%25&sel_sess=%25&sel_attr=%25&begin_hh=0&begin_mi=0" +
                        "&begin_ap=a&end_hh=0&end_mi=0&end_ap=a")
                .post();
        final Elements elements = doc.select("tr:has(td[class=dddefault])");
        CourseDescriptor lastCourse = null;
        for (final Element element : elements) {
            final CourseDescriptor cs = new CourseDescriptor((element));
            if (lastCourse != null && cs.getRefNumber().length() < 2) {
                // This is not a new course but more of the schedule
                lastCourse.getSchedule().add(cs.getSchedule().getDays().get(0), cs.getSchedule().getTimes().get(0));
            } else {
                courses.add(cs);
                lastCourse = cs;
            }
        }
        return courses;
    }

    private static List<Subject> getSubjects(String term) throws IOException {
        final List<Subject> subjects = new ArrayList<Subject>();
        final Document doc = Jsoup.connect("https://seanet.uncw.edu/TEAL/swkfccl.p_sel_term_date")
                .requestBody("call_proc_in=swkfccl.p_sel_crse_search&term_in=" + term)
                .post();
        final Elements elements = doc.select("SELECT[NAME=sel_subj] > OPTION");
        for(final Element e: elements) {
            subjects.add(new Subject(e));
        }
        return subjects;
    }
}