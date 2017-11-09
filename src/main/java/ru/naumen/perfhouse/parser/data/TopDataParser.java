package ru.naumen.perfhouse.parser.data;

import ru.naumen.perfhouse.parser.DataParser;
import ru.naumen.perfhouse.parser.DataSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Top output parser
 * @author dkolmogortsev
 *
 */
public class TopDataParser implements DataParser
{
    private Pattern cpuAndMemPattren = Pattern.compile(
            "^ *\\d+ \\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ \\S+ +(\\S+) +(\\S+) +\\S+ java");
    private Pattern loadAvgPattern = Pattern.compile(".*load average:(.*)");

    public void parseLine(String line, DataSet currentSet)
    {
        Matcher loadAvgMatcher = loadAvgPattern.matcher(line);
        if (!loadAvgMatcher.find())
        {
            return;
        }

        String data = loadAvgMatcher.group(1).split(",")[0].trim();
        currentSet.getCpuData().addLa(Double.parseDouble(data));

        Matcher cpuAndMemMatcher = cpuAndMemPattren.matcher(line);
        if (!cpuAndMemMatcher.find())
        {
            return;
        }

        currentSet.getCpuData().addCpu(Double.valueOf(cpuAndMemMatcher.group(1)));
        currentSet.getCpuData().addMem(Double.valueOf(cpuAndMemMatcher.group(2)));

    }
}
