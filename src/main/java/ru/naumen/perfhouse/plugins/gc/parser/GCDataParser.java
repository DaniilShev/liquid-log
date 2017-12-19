package ru.naumen.perfhouse.plugins.gc.parser;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.DataParser;
import ru.naumen.perfhouse.parser.ParsingMode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@ParsingMode(name="gc")
public class GCDataParser implements DataParser<GCData>
{
    private static final Pattern gcExecutionTimePattern = Pattern.compile(".*real=(.*)secs.*");

    @Override
    public void parseLine(String line, GCData currentSet)
    {
        Matcher matcher = gcExecutionTimePattern.matcher(line);
        if (!matcher.find())
        {
            return;
        }

        String data = matcher.group(1).trim().replace(',', '.');
        currentSet.addValue(Double.parseDouble(data));
    }
}
