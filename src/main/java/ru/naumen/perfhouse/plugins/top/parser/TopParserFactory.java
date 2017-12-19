package ru.naumen.perfhouse.plugins.top.parser;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.ParserFactory;
import ru.naumen.perfhouse.parser.ParsingMode;

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
