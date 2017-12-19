package ru.naumen.perfhouse.plugins.nio.parser;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.ParserFactory;
import ru.naumen.perfhouse.parser.ParsingMode;

@Service
@ParsingMode(name="nio")
public class NioParserFactory implements ParserFactory {
    @Override
    public NioData getDataSet() {
        return new NioData();
    }

    @Override
    public NioTimeParser getTimeParser(String logPath, String timeZone) {
        return new NioTimeParser(timeZone);
    }
}
