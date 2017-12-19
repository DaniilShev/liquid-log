package ru.naumen.perfhouse.plugins.sdng.parser;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.DataParser;

import java.util.regex.Pattern;

/**
 * Created by doki on 22.10.16.
 */
@Service
public class ErrorDataParser implements DataParser<SdngData>
{
    private static final Pattern warnRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) WARN");
    private static final Pattern errorRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) ERROR");
    private static final Pattern fatalRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) FATAL");

    @Override
    public void parseLine(String line, SdngData currentSet)
    {
        ErrorData errorData = currentSet.getErrors();
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
