package ru.naumen.perfhouse.parser.parsers.data;

import ru.naumen.perfhouse.parser.DataParser;
import ru.naumen.perfhouse.parser.DataSet;

public class CompositeDataParser implements DataParser
{
    private DataParser[] parsers;

    public CompositeDataParser(DataParser... dataParsers) {
        parsers = dataParsers;
    }

    @Override
    public void parseLine(String line, DataSet currentSet)
    {
        for (DataParser parser : parsers) {
            parser.parseLine(line, currentSet);
        }
    }
}
