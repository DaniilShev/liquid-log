package ru.naumen.perfhouse.parser.factories;

import ru.naumen.perfhouse.parser.ParserFactory;
import ru.naumen.perfhouse.parser.data.SdngData;

public class SdngParserFactory implements ParserFactory {
    @Override
    public SdngData getDataSet() {
        return new SdngData();
    }
}
