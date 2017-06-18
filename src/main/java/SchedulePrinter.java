
/**
 * Created by jpricket on 6/17/2017.
 */
public class SchedulePrinter {
    private static int xOffset = 8;
    private static int yOffset = 1;
    private static int dayWidth = 10;
    private char[][] scheduleArray = new char[30][60];

    public SchedulePrinter() {
        for(int x = 0; x < 60; x++) {
            for(int y = 0; y < 30; y++) {
                scheduleArray[y][x] = ' ';
            }
        }
        int y = yOffset;
        addCaption(xOffset, 0, " Monday   Tuesday  Wednesday  Thursday   Friday  ");
        addCaption(0, y++, " 8:00 ------------------------------------------------------");
        addCaption(0, y++, " 8:30");
        addCaption(0, y++, " 9:00 ------------------------------------------------------");
        addCaption(0, y++, " 9:30");
        addCaption(0, y++, "10:00 ------------------------------------------------------");
        addCaption(0, y++, "10:30");
        addCaption(0, y++, "11:00 ------------------------------------------------------");
        addCaption(0, y++, "11:30");
        addCaption(0, y++, "12:00 ------------------------------------------------------");
        addCaption(0, y++, "12:30");
        addCaption(0, y++, "13:00 ------------------------------------------------------");
        addCaption(0, y++, "13:30");
        addCaption(0, y++, "14:00 ------------------------------------------------------");
        addCaption(0, y++, "14:30");
        addCaption(0, y++, "15:00 ------------------------------------------------------");
        addCaption(0, y++, "15:30");
        addCaption(0, y++, "16:00 ------------------------------------------------------");
        addCaption(0, y++, "16:30");
        addCaption(0, y++, "17:00 ------------------------------------------------------");
        addCaption(0, y++, "17:30");
        addCaption(0, y++, "18:00 ------------------------------------------------------");
        addCaption(0, y++, "18:30");
        addCaption(0, y++, "19:00 ------------------------------------------------------");
        addCaption(0, y++, "19:30");
    }

    public void addClass(CourseDescriptor course) {
        for(CourseSchedule.ClassTime time: course.getSchedule().getClassTimes()) {
            addBlock(time, course.getSubject() + course.getCourse());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < 30; y++) {
            sb.append(scheduleArray[y]);
            sb.append("\n");
        }
        return sb.toString();
    }

    private void addBlock(CourseSchedule.ClassTime time, String label) {
        int xStart = getDayIndex(time.Day);
        int yStart = getTimeIndex(time.Hour, time.Minute);
        addBoxTop(xStart, yStart++);
        addBoxMiddle(xStart, yStart++, label);
        for(int i = 2; i < time.DurationMinutes/30; i++) {
            addBoxMiddle(xStart, yStart++, "");
        }
        addBoxBottom(xStart, yStart);
    }

    private int getDayIndex(CourseSchedule.Day day) {
        switch(day) {
            case Mon: return xOffset;
            case Tue: return xOffset + dayWidth;
            case Wed: return xOffset + dayWidth * 2;
            case Thu: return xOffset + dayWidth * 3;
            case Fri: return xOffset + dayWidth * 4;
            default: return 0;
        }
    }

    private int getTimeIndex(int hour, int minute) {
        int index = yOffset + (hour - CourseSchedule.FirstClassHour) * 2;
        if (minute >= 30) {
            index++;
        }
        return index;
    }

    private void addBoxTop(int startX, int startY) {
        scheduleArray[startY][startX++] = 0x250C;
        scheduleArray[startY][startX++] = 0x2500;
        scheduleArray[startY][startX++] = 0x2500;
        scheduleArray[startY][startX++] = 0x2500;
        scheduleArray[startY][startX++] = 0x2500;
        scheduleArray[startY][startX++] = 0x2500;
        scheduleArray[startY][startX++] = 0x2500;
        scheduleArray[startY][startX] = 0x2510;
    }

    private void addBoxMiddle(int startX, int startY, String caption) {
        scheduleArray[startY][startX++] = 0x2502;
        scheduleArray[startY][startX++] = caption.length() >= 5 ? caption.charAt(0) : ' ';
        scheduleArray[startY][startX++] = caption.length() >= 5 ? caption.charAt(1) : ' ';
        scheduleArray[startY][startX++] = caption.length() >= 5 ? caption.charAt(2) : ' ';
        scheduleArray[startY][startX++] = caption.length() >= 5 ? caption.charAt(3) : ' ';
        scheduleArray[startY][startX++] = caption.length() >= 5 ? caption.charAt(4) : ' ';
        scheduleArray[startY][startX++] = caption.length() >= 6 ? caption.charAt(5) : ' ';
        scheduleArray[startY][startX] = 0x2502;
    }

    private void addBoxBottom(int startX, int startY) {
        scheduleArray[startY][startX++] = 0x2514;
        scheduleArray[startY][startX++] = 0x2500;
        scheduleArray[startY][startX++] = 0x2500;
        scheduleArray[startY][startX++] = 0x2500;
        scheduleArray[startY][startX++] = 0x2500;
        scheduleArray[startY][startX++] = 0x2500;
        scheduleArray[startY][startX++] = 0x2500;
        scheduleArray[startY][startX] = 0x2518;
    }

    private void addCaption(int startX, int startY, String caption) {
        for(int i = 0; i < caption.length(); i++) {
            scheduleArray[startY][startX+i] = caption.charAt(i);
        }
    }
}
