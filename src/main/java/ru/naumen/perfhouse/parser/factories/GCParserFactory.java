package ru.naumen.perfhouse.parser.factories;

import ru.naumen.perfhouse.parser.ParserFactory;
import ru.naumen.perfhouse.parser.data.GCData;

public class GCParserFactory implements ParserFactory
{
    @Override
    public GCData getDataSet () {
        return new GCData();
    }
}
