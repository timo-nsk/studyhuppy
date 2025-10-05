package com.studyhuppy.systemtests.service;

public class TimeStringParser {

    public static int parseTextToSeconds(String text) {
        int totalSeconds = 0;

        // Entferne überflüssige Leerzeichen
        text = text.trim();

        // Regex zum Extrahieren von Tagen, Stunden, Minuten und Sekunden
        String regex = "(\\d+)d|(\\d+)h|(\\d+)m|(\\d+)s";
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(text);

        while (matcher.find()) {
            if (matcher.group(1) != null) { // Tage
                totalSeconds += Integer.parseInt(matcher.group(1)) * 86400;
            } else if (matcher.group(2) != null) { // Stunden
                totalSeconds += Integer.parseInt(matcher.group(2)) * 3600;
            } else if (matcher.group(3) != null) { // Minuten
                totalSeconds += Integer.parseInt(matcher.group(3)) * 60;
            } else if (matcher.group(4) != null) { // Sekunden
                totalSeconds += Integer.parseInt(matcher.group(4));
            }
        }

        return totalSeconds;
    }
}
