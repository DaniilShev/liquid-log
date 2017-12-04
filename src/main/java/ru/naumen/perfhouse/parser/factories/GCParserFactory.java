package ru.naumen.perfhouse.parser.factories;

import ru.naumen.perfhouse.parser.ParserFactory;
import ru.naumen.perfhouse.parser.sets.GCDataSet;

public class GCParserFactory implements ParserFactory
{
    @Override
    public GCDataSet getDataSet () {
        return new GCDataSet();
    }
}
