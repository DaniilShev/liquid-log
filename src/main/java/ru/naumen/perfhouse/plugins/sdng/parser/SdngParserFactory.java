package ru.naumen.perfhouse.plugins.sdng.parser;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.ParserFactory;
import ru.naumen.perfhouse.parser.ParsingMode;

@Service
@ParsingMode(name="sdng")
public class SdngParserFactory implements ParserFactory {
    @Override
    public SdngData getDataSet() {
        return new SdngData();
    }

    @Override
    public SdngTimeParser getTimeParser(String logPath, String timeZone) {
        return new SdngTimeParser(timeZone);
    }
}
