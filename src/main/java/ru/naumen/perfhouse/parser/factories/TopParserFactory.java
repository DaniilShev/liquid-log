package ru.naumen.perfhouse.parser.factories;

import ru.naumen.perfhouse.parser.ParserFactory;
import ru.naumen.perfhouse.parser.sets.TopDataSet;

public class TopParserFactory implements ParserFactory {
    @Override
    public TopDataSet getDataSet() {
        return new TopDataSet();
    }
}
