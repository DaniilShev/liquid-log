package ru.naumen.perfhouse.parser.factories;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.ParserFactory;
import ru.naumen.perfhouse.parser.annotation.ParsingMode;
import ru.naumen.perfhouse.parser.data.SdngData;
import ru.naumen.perfhouse.parser.parsers.time.SdngTimeParser;

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
