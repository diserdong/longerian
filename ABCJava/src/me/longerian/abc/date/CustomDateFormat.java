package me.longerian.abc.date;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huifeng.hxl on 2014/8/16.
 */
public class CustomDateFormat {

    public static void main(String[] args) {
        String format = "#dd#:#HH#:#mm#:#ss#";
        long countdown = 10 * 86400 * 1000 + 36400 * 1000;

        long localCountdown = countdown;

        Pattern pattern = Pattern.compile("#d+#");
        Matcher matcher = pattern.matcher(format);
        if(matcher.find()) {
            int days = (int) (localCountdown / (86400 * 1000));
            pattern = Pattern.compile("#dd#");
            matcher = pattern.matcher(format);
            if(matcher.find()) {
                if(days >= 10) {
                    format = format.replaceAll("#dd#", days + "");
                } else {
                    format = format.replaceAll("#dd#", "0" + days + "");
                }
            } else {
                format = format.replaceAll("#d+#", days + "");
            }
            localCountdown = localCountdown - (days * 86400 * 1000);
        }

        pattern = Pattern.compile("H+");
        matcher = pattern.matcher(format);
        if(matcher.find()) {
            int hours = (int) (localCountdown / (3600 * 1000));
            pattern = Pattern.compile("#HH#");
            matcher = pattern.matcher(format);
            if(matcher.find()) {
                if(hours >= 10) {
                    format = format.replaceAll("#HH#", hours + "");
                } else {
                    format = format.replaceAll("#HH#", "0" + hours + "");
                }
            } else {
                format = format.replaceAll("#H+#", hours + "");
            }
            localCountdown = localCountdown - (hours * 3600 * 1000);
        }

        pattern = Pattern.compile("m+");
        matcher = pattern.matcher(format);
        if(matcher.find()) {
            int minutes = (int) (localCountdown / (60 * 1000));
            pattern = Pattern.compile("#mm#");
            matcher = pattern.matcher(format);
            if(matcher.find()) {
                if(minutes >= 10) {
                    format = format.replaceAll("#mm#", minutes + "");
                } else {
                    format = format.replaceAll("#mm#", "0" + minutes + "");
                }
            } else {
                format = format.replaceAll("#m+#", minutes + "");
            }
            localCountdown = localCountdown - (minutes * 60 * 1000);
        }

        pattern = Pattern.compile("s+");
        matcher = pattern.matcher(format);
        if(matcher.find()) {
            int seconds = (int) (localCountdown / (1000));
            pattern = Pattern.compile("#ss#");
            matcher = pattern.matcher(format);
            if(matcher.find()) {
                if(seconds >= 10) {
                    format = format.replaceAll("#ss#", seconds + "");
                } else {
                    format = format.replaceAll("#ss#", "0" + seconds + "");
                }
            } else {
                format = format.replaceAll("#s+#", seconds + "");
            }
            localCountdown = localCountdown - (seconds * 1000);
        }

        System.out.println("format = " + format + " localCountdown = " + localCountdown);


        format = "#dd#:#HH#:#mm#:#ss#";
        countdown = 10 * 86400 * 1000 + 36400 * 1000;

        FormatParams formatParams = new FormatParams(countdown, format);

        formatField(formatParams, 86400 * 1000, "#d+#", "#dd#");
        formatField(formatParams, 3600 * 1000, "#H+#", "#HH#");
        formatField(formatParams, 60 * 1000, "#m+#", "#mm#");
        formatField(formatParams, 1000, "#s+#", "#ss#");
        System.out.println("format = " + formatParams.text + " localCountdown = " + formatParams.countdown);
    }

    private static class FormatParams {
        public long countdown;
        public String text;

        private FormatParams(long countdown, String text) {
            this.countdown = countdown;
            this.text = text;
        }
    }

    private static FormatParams formatField(FormatParams formatParams, long unitMills, String fieldBasicPattern, String fieldSpecificPattern) {
        Pattern pattern = Pattern.compile(fieldBasicPattern);
        Matcher matcher = pattern.matcher(formatParams.text);
        if(matcher.find()) {
            int filedCount = (int) (formatParams.countdown / (unitMills));
            pattern = Pattern.compile(fieldSpecificPattern);
            matcher = pattern.matcher(formatParams.text);
            if(matcher.find()) {
                if(filedCount >= 10) {
                    formatParams.text = formatParams.text.replaceAll(fieldSpecificPattern, filedCount + "");
                } else {
                    formatParams.text = formatParams.text.replaceAll(fieldSpecificPattern, "0" + filedCount + "");
                }
            } else {
                formatParams.text = formatParams.text.replaceAll(fieldBasicPattern, filedCount + "");
            }
            formatParams.countdown = formatParams.countdown - (filedCount * unitMills);
        }
        return formatParams;
    }

}
