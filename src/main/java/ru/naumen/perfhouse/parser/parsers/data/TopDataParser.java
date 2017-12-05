package ru.naumen.perfhouse.parser.parsers.data;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.DataParser;
import ru.naumen.perfhouse.parser.sets.TopDataSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Top output parser
 * @author dkolmogortsev
 *
 */
@Service
public class TopDataParser implements DataParser<TopDataSet>
{
    private static final Pattern cpuAndMemPattern = Pattern.compile(
            "^ *\\d+ \\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ \\S+ +(\\S+) +(\\S+) +\\S+ java");
    private static final Pattern loadAvgPattern = Pattern.compile(".*load average:(.*)");

    @Override
    public void parseLine(String line, TopDataSet currentSet)
    {
        Matcher loadAvgMatcher = loadAvgPattern.matcher(line);
        if (loadAvgMatcher.find())
        {
            String data = loadAvgMatcher.group(1).split(",")[0].trim();
            currentSet.getData().addLa(Double.parseDouble(data));

            return;
        }

        Matcher cpuAndMemMatcher = cpuAndMemPattern.matcher(line);
        if (cpuAndMemMatcher.find())
        {
            currentSet.getData().addCpu(Double.valueOf(cpuAndMemMatcher.group(1)));
            currentSet.getData().addMem(Double.valueOf(cpuAndMemMatcher.group(2)));
        }
    }
}