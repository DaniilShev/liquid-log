package ru.naumen.perfhouse.parser.factories;

import ru.naumen.perfhouse.parser.ParserFactory;
import ru.naumen.perfhouse.parser.sets.SdngDataSet;

public class SdngParserFactory implements ParserFactory {
    @Override
    public SdngDataSet getDataSet() {
        return new SdngDataSet();
    }
}
