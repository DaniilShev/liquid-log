package ru.naumen.perfhouse.parser.parsers.data;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.DataParser;
import ru.naumen.perfhouse.parser.annotation.ParsingMode;
import ru.naumen.perfhouse.parser.data.TopData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Top output parser
 * @author dkolmogortsev
 *
 */
@Service
@ParsingMode(name="top")
public class TopDataParser implements DataParser<TopData>
{
    private static final Pattern cpuAndMemPattern = Pattern.compile(
            "^ *\\d+ \\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ \\S+ +(\\S+) +(\\S+) +\\S+ java");
    private static final Pattern loadAvgPattern = Pattern.compile(".*load average:(.*)");

    @Override
    public void parseLine(String line, TopData currentSet)
    {
        Matcher loadAvgMatcher = loadAvgPattern.matcher(line);
        if (loadAvgMatcher.find())
        {
            String data = loadAvgMatcher.group(1).split(",")[0].trim();
            currentSet.addLa(Double.parseDouble(data));

            return;
        }

        Matcher cpuAndMemMatcher = cpuAndMemPattern.matcher(line);
        if (cpuAndMemMatcher.find())
        {
            currentSet.addCpu(Double.valueOf(cpuAndMemMatcher.group(1)));
            currentSet.addMem(Double.valueOf(cpuAndMemMatcher.group(2)));
        }
    }
}
