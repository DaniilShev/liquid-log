package ru.naumen.perfhouse.parser.data;

import ru.naumen.perfhouse.parser.DataParser;
import ru.naumen.perfhouse.parser.DataSet;

public class SdngDataParser implements DataParser
{
    @Override
    public void parseLine(String line, DataSet currentSet)
    {
        currentSet.getActionsDone().parseLine(line);
        currentSet.getErrors().parseLine(line);
    }
}
