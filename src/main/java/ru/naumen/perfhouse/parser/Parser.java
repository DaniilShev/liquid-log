package ru.naumen.perfhouse.parser;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.DataParser;
import ru.naumen.perfhouse.parser.interfaces.ParserFactory;
import ru.naumen.perfhouse.parser.interfaces.StoragePacker;
import ru.naumen.perfhouse.parser.interfaces.TimeParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by doki on 22.10.16.
 */
@Service
public class Parser
{

    private BeanFactory factory;
    private ParserModes parserModes;

    @Autowired
    public Parser(BeanFactory factory, ParserModes parserModes) {
        this.factory = factory;
        this.parserModes = parserModes;
    }

    /**
     * 
     * @param dbName - Name of database for saving logs
     * @param parsingMode - Mode of parser
     * @param logPath - Path to log file
     * @param timeZone - Time zone
     * @param printLog - Should print logs in console
     * @throws IOException - Can fall when reading new line
     * @throws ParseException - Can fall when parsing time from line
     */
    public void parse(String dbName, String parsingMode, String logPath,
                      String timeZone, Boolean printLog)
            throws IOException, ParseException
    {
        if (!parserModes.hasMode(parsingMode)) {
            throw new IllegalArgumentException(
                    String.format("Unknown parse mode! Available modes: %s. Requested mode: %s",
                            String.join(",", parserModes.getModes()), parsingMode));
        }
        ParserFactory parserFactory = parserModes.getParserFactory(parsingMode);
        StoragePacker storagePacker = parserModes.getStoragePacker(parsingMode);
        DataParser dataParser = parserModes.getDataParser(parsingMode);

        Storage storage = factory.getBean(Storage.class);
        storage.init(parserFactory, storagePacker, dbName, printLog);

        TimeParser timeParser = parserFactory.getTimeParser(logPath, timeZone);
        try (BufferedReader br = new BufferedReader(new FileReader(logPath), 32 * 1024 * 1024))
        {
            String line;
            while ((line = br.readLine()) != null) {
                long time = timeParser.parseTime(line);
                if (time == 0)
                {
                    continue;
                }

                int min5 = 5 * 60 * 1000;
                long count = time / min5;
                long key = count * min5;

                dataParser.parseLine(line, storage.get(key));
            }
        }

        storage.close();
    }
}
