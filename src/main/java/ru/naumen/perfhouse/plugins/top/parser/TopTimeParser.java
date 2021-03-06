package ru.naumen.perfhouse.plugins.top.parser;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.TimeParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Scope("request")
public class TopTimeParser implements TimeParser
{

    private static final Pattern TIME_PATTERN = Pattern.compile("^_+ (\\S+)");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyyMMddHH:mm");
    private long lastTime;
    private String basicTime;

    public TopTimeParser(String file, String timeZone) throws IllegalArgumentException
    {
        //Supports these masks in file name: YYYYmmdd, YYY-mm-dd i.e. 20161101, 2016-11-01
        Matcher matcher = Pattern.compile("\\d{8}|\\d{4}-\\d{2}-\\d{2}").matcher(file);
        if (!matcher.find())
        {
            throw new IllegalArgumentException();
        }
        basicTime = matcher.group(0).replaceAll("-", "");

        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timeZone));
    }

    @Override
    public long parseTime(String line) throws ParseException
    {
        Matcher matcher = TIME_PATTERN.matcher(line);

        if (matcher.find())
        {
            String timeString = basicTime + matcher.group(1);

            lastTime = DATE_FORMAT.parse(timeString).getTime();
        }

        return lastTime;
    }
}
