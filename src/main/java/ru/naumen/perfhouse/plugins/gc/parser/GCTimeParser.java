package ru.naumen.perfhouse.plugins.gc.parser;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.TimeParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Scope("request")
public class GCTimeParser implements TimeParser
{
    private static final Pattern PATTERN = Pattern.compile(
            "^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{4}).*");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            new Locale("ru", "RU"));

    public GCTimeParser(String timeZone)
    {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timeZone));
    }

    @Override
    public long parseTime(String line) throws ParseException
    {
        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find())
        {
            String timeString = matcher.group(1);

            return DATE_FORMAT.parse(timeString).getTime();
        }

        return 0L;
    }
}
