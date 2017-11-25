package ru.naumen.perfhouse.parser.dataparsers;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.DataParser;
import ru.naumen.perfhouse.parser.DataSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GCDataParser implements DataParser
{
    private static final Pattern gcExecutionTimePattern = Pattern.compile(".*real=(.*)secs.*");

    @Override
    public void parseLine(String line, DataSet currentSet)
    {
        Matcher matcher = gcExecutionTimePattern.matcher(line);
        if (!matcher.find())
        {
            return;
        }

        String data = matcher.group(1).trim().replace(',', '.');
        currentSet.getGc().addValue(Double.parseDouble(data));
    }
}
