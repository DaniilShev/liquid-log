package ru.naumen.perfhouse.parser;

import ru.naumen.perfhouse.parser.interfaces.DataParser;
import ru.naumen.perfhouse.parser.interfaces.DataSet;

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
