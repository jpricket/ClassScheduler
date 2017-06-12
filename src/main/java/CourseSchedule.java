import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpricket on 6/11/2017.
 */
public class CourseSchedule {
    private List<String> days = new ArrayList<String>();
    private List<String> times = new ArrayList<String>();

    public CourseSchedule(String days, String time) {
        add(days, time);
    }

    public void add(String days, String time) {
        this.days.add(days);
        this.times.add(time);
    }

    public List<String> getDays() {
        return days;
    }

    public List<String> getTimes() {
        return times;
    }

    @Override
    public String toString() {
        String schedule = "";
        for(int i = 0; i < days.size(); i++) {
            schedule += "[" + days.get(i) + " @ " + times.get(i) + "]";
        }
        return schedule;
    }
}
