package com.custom.keyboard.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.custom.keyboard.util.StringUtils.largeIntToChar;

public class TimeUtils {
    // time-related stuff
    public static long nowAsLong() {
        return Instant.now().getEpochSecond();
    }
    public static int nowAsInt() {
        // new Date().getTime() / 1000;
        return (int) (System.currentTimeMillis() / 1000L);
    }
    public static Date stringToDate(String date, String format) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        return simpleDateFormat.parse(date);
    }
    public static String getDateString(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        return sdf.format(cal.getTime());
    }
    public static String getTimeString(String timeFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.US);
        return sdf.format(cal.getTime());
    }
    public static String timemoji() {
        String clocks = "🕐🕜🕑🕝🕒🕞🕓🕟🕔🕠🕕🕡🕖🕢🕗🕣🕘🕤🕙🕥🕚🕦🕛🕧";

        Calendar rightNow = Calendar.getInstance();
        rightNow.getTime();
        int hours = rightNow.get(Calendar.HOUR_OF_DAY);
        if (hours == 0) hours = 12;
        if (hours > 12) hours -= 12;
        int minutes = rightNow.get(Calendar.MINUTE);

        // 0 thru 29, 30 thru 59
        int which = (((hours - 1) * 2) + (minutes / 30)) * 2;

        return largeIntToChar(clocks.codePointAt(which));
    }

}
