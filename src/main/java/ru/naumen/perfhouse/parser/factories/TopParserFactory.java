package ru.naumen.perfhouse.parser.factories;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.ParserFactory;
import ru.naumen.perfhouse.parser.annotation.ParsingMode;
import ru.naumen.perfhouse.parser.data.TopData;
import ru.naumen.perfhouse.parser.parsers.time.TopTimeParser;

@Service
@ParsingMode(name="top")
public class TopParserFactory implements ParserFactory {
    @Override
    public TopData getDataSet() {
        return new TopData();
    }

    @Override
    public TopTimeParser getTimeParser(String logPath, String timeZone) {
        return new TopTimeParser(logPath, timeZone);
    }
}
