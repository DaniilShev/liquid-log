package ru.naumen.perfhouse.parser.parsers.data;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.DataParser;
import ru.naumen.perfhouse.parser.sets.GCDataSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GCDataParser implements DataParser<GCDataSet>
{
    private static final Pattern gcExecutionTimePattern = Pattern.compile(".*real=(.*)secs.*");

    @Override
    public void parseLine(String line, GCDataSet currentSet)
    {
        Matcher matcher = gcExecutionTimePattern.matcher(line);
        if (!matcher.find())
        {
            return;
        }

        String data = matcher.group(1).trim().replace(',', '.');
        currentSet.getData().addValue(Double.parseDouble(data));
    }
}
