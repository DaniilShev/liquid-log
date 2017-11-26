package ru.naumen.perfhouse.parser.dataparsers;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.DataParser;
import ru.naumen.perfhouse.parser.DataSet;

import java.util.regex.Pattern;

/**
 * Created by doki on 22.10.16.
 */
@Service
public class ErrorDataParser implements DataParser
{
    private static final Pattern warnRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) WARN");
    private static final Pattern errorRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) ERROR");
    private static final Pattern fatalRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) FATAL");

    @Override
    public void parseLine(String line, DataSet currentSet)
    {
        if (warnRegEx.matcher(line).find())
        {
            currentSet.getErrors().incWarnCount();
        }
        if (errorRegEx.matcher(line).find())
        {
            currentSet.getErrors().incErrorCount();
        }
        if (fatalRegEx.matcher(line).find())
        {
            currentSet.getErrors().incFatalCount();
        }
    }
}
