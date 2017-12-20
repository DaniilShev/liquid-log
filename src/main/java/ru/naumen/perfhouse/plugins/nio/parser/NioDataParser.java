package ru.naumen.perfhouse.plugins.nio.parser;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.ParsingMode;
import ru.naumen.perfhouse.parser.interfaces.DataParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by doki on 22.10.16.
 */
@Service
@ParsingMode(name="nio")
public class NioDataParser implements DataParser<NioData> {
    private static final Pattern renderTimePattern = Pattern.compile(".*render time: (\\d+)");

    @Override
    public void parseLine(String line, NioData currentSet)
    {
        Matcher matcher = renderTimePattern.matcher(line);
        if (!matcher.find())
        {
            return;
        }

        currentSet.addValue(Integer.parseInt(matcher.group(1)));
    }
}