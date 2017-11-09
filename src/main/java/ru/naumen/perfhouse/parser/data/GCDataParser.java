package ru.naumen.perfhouse.parser.data;

import ru.naumen.perfhouse.parser.DataParser;
import ru.naumen.perfhouse.parser.DataSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GCDataParser implements DataParser {
    private Pattern gcExecutionTimePattern = Pattern.compile(".*real=(.*)secs.*");

    public void parseLine(String line, DataSet currentSet)
    {
        Matcher matcher = gcExecutionTimePattern.matcher(line);
        if (!matcher.find()) {
            return;
        }

        String data = matcher.group(1).trim().replace(',', '.');
        currentSet.getGc().addValue(Double.parseDouble(data));
    }
}
