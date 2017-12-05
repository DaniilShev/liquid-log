package ru.naumen.perfhouse.parser.factories;

import ru.naumen.perfhouse.parser.ParserFactory;
import ru.naumen.perfhouse.parser.data.TopData;

public class TopParserFactory implements ParserFactory {
    @Override
    public TopData getDataSet() {
        return new TopData();
    }
}
