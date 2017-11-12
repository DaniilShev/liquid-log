package ru.naumen.perfhouse.parser.data;

import ru.naumen.perfhouse.parser.DataParser;
import ru.naumen.perfhouse.parser.DataSet;

public class CompositeDataParser implements DataParser
{
    private DataParser[] parsers;

    public CompositeDataParser(DataParser[] dataParsers) {
        parsers = dataParsers;
    }

    @Override
    public void setCurrentSet(DataSet dataSet) {
        for (DataParser parser : parsers) {
            parser.setCurrentSet(dataSet);
        }
    }

    @Override
    public void parseLine(String line)
    {
        for (DataParser parser : parsers) {
            parser.parseLine(line);
        }
    }
}
