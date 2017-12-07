package ru.naumen.perfhouse.parser.factories;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.ParserFactory;
import ru.naumen.perfhouse.parser.annotation.ParsingMode;
import ru.naumen.perfhouse.parser.data.GCData;
import ru.naumen.perfhouse.parser.parsers.time.GCTimeParser;

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
