package ru.naumen.perfhouse.parser.parsers.data;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.DataParser;
import ru.naumen.perfhouse.parser.data.ErrorData;
import ru.naumen.perfhouse.parser.sets.SdngDataSet;

import java.util.regex.Pattern;

/**
 * Created by doki on 22.10.16.
 */
@Service
public class ErrorDataParser implements DataParser<SdngDataSet>
{
    private static final Pattern warnRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) WARN");
    private static final Pattern errorRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) ERROR");
    private static final Pattern fatalRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) FATAL");

    @Override
    public void parseLine(String line, SdngDataSet currentSet)
    {
        ErrorData errorData = currentSet.getData().getY();
        if (warnRegEx.matcher(line).find())
        {
            errorData.incWarnCount();
        }
        if (errorRegEx.matcher(line).find())
        {
            errorData.incErrorCount();
        }
        if (fatalRegEx.matcher(line).find())
        {
            errorData.incFatalCount();
        }
    }
}
