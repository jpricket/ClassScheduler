import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpricket on 6/11/2017.
 */
public class CourseSchedule {
    private List<String> days = new ArrayList<String>();
    private List<String> times = new ArrayList<String>();

    public static final int FirstClassHour = 8;
    public enum Day { Mon, Tue, Wed, Thu, Fri }
    public class ClassTime {
        public final Day Day;
        public final int Hour;
        public final int Minute;
        public final int DurationMinutes;
        public ClassTime(Day day, int hour, int minute, int durationMinutes) {
            this.Day = day;
            this.Hour = hour;
            this.Minute = minute;
            this.DurationMinutes = durationMinutes;
        }
    }

    public CourseSchedule() {
    }

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

    public List<ClassTime> getClassTimes() {
        List<ClassTime> classTimes = new ArrayList<>();
        for(int i = 0; i < days.size(); i++) {
            String[] parts = StringUtils.split(times.get(i), "-: ");
            int startHour = FirstClassHour;
            int startMinute = 0;
            int duration = 0;
            if (parts.length >= 6) {
                startHour = Integer.parseInt(parts[0]);
                if (startHour < FirstClassHour) {
                    startHour += 12;
                }
                startMinute = Integer.parseInt(parts[1]);
                int endHour = Integer.parseInt(parts[3]);
                if (endHour < startHour) {
                    endHour += 12;
                }
                int endMinute = Integer.parseInt(parts[4]);
                duration = (endHour - startHour)*60 + endMinute - startMinute;
            }
            if (days.get(i).contains("M")) {
                classTimes.add(new ClassTime(Day.Mon, startHour, startMinute, duration));
            }
            if (days.get(i).contains("T")) {
                classTimes.add(new ClassTime(Day.Tue, startHour, startMinute, duration));
            }
            if (days.get(i).contains("W")) {
                classTimes.add(new ClassTime(Day.Wed, startHour, startMinute, duration));
            }
            if (days.get(i).contains("R")) {
                classTimes.add(new ClassTime(Day.Thu, startHour, startMinute, duration));
            }
            if (days.get(i).contains("F")) {
                classTimes.add(new ClassTime(Day.Fri, startHour, startMinute, duration));
            }
        }
        return classTimes;
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
