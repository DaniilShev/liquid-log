package ru.naumen.perfhouse.parser.parsers.data;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.DataParser;
import ru.naumen.perfhouse.parser.DataSet;
import ru.naumen.perfhouse.parser.annotation.ParsingMode;

@Service
@ParsingMode(name="sdng")
public class SdngDataParser implements DataParser
{
    private CompositeDataParser dataParser = new CompositeDataParser(
            new ActionDoneDataParser(), new ErrorDataParser());

    @Override
    public void parseLine(String line, DataSet currentSet) {
        dataParser.parseLine(line, currentSet);
    }
}
