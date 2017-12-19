package ru.naumen.perfhouse.plugins.gc.parser;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.ParserFactory;
import ru.naumen.perfhouse.parser.ParsingMode;

@Service
@ParsingMode(name="gc")
public class GCParserFactory implements ParserFactory
{
    @Override
    public GCData getDataSet () {
        return new GCData();
    }

    @Override
    public GCTimeParser getTimeParser(String logPath, String timeZone) {
        return new GCTimeParser(timeZone);
    }
}
