package ru.naumen.perfhouse.parser.data;

import ru.naumen.perfhouse.parser.DataParser;
import ru.naumen.perfhouse.parser.DataSet;

/**
 * Created by doki on 22.10.16.
 */
public class ActionDataParser implements DataParser
{
    private DataSet currentSet;

    @Override
    public void setCurrentSet(DataSet dataSet) {
        currentSet = dataSet;
    }

    @Override
    public void parseLine(String line)
    {
        currentSet.getActionsDone().parseLine(line);
    }
}