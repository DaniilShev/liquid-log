package ru.naumen.perfhouse.plugins.nio.parser;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.TimeParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by doki on 22.10.16.
 */
@Service
@Scope("request")
public class NioTimeParser implements TimeParser
{
    private static final Pattern TIME_PATTERN = Pattern.compile(
            "^\\d{9} (\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3})");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss,SSS", new Locale("ru", "RU"));

    public NioTimeParser(String timeZone)
    {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timeZone));
    }

    @Override
    public long parseTime(String line) throws ParseException
    {
        Matcher matcher = TIME_PATTERN.matcher(line);

        if (matcher.find())
        {
            String timeString = matcher.group(1);

            return DATE_FORMAT.parse(timeString).getTime();
        }

        return 0L;
    }
}
