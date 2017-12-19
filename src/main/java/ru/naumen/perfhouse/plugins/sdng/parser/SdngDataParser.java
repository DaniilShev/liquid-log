package ru.naumen.perfhouse.plugins.sdng.parser;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.DataParser;
import ru.naumen.perfhouse.parser.interfaces.DataSet;
import ru.naumen.perfhouse.parser.ParsingMode;
import ru.naumen.perfhouse.parser.CompositeDataParser;
import ru.naumen.perfhouse.plugins.sdng.parser.ActionDoneDataParser;
import ru.naumen.perfhouse.plugins.sdng.parser.ErrorDataParser;

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
