package ru.naumen.perfhouse.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.dataparsers.*;
import ru.naumen.perfhouse.parser.timeparsers.*;

/**
 * Created by doki on 22.10.16.
 */
@Service
public class Parser
{
    @Autowired
    private BeanFactory factory;

    /**
     * 
     * @param dbName - Name of database for saving logs
     * @param parsingMode - Mode of parser
     * @param logPath - Path to log file
     * @param timeZone - Time zone
     * @param printLog - Should print logs in console
     * @throws IOException - Can fall when reading new line
     * @throws ParseException - Can fall when parsing timeparsers from line
     */
    public void parse(String dbName, String parsingMode, String logPath,
                      String timeZone, Boolean printLog)
            throws IOException, ParseException
    {
        Storage storage = factory.getBean(Storage.class);
        storage.init(dbName, printLog);

        TimeParser timeParser;
        DataParser dataParser;

        switch (parsingMode)
        {
            case "sdng":
                timeParser = new SdngTimeParser(timeZone);
                dataParser = new CompositeDataParser(
                        factory.getBean(ActionDoneDataParser.class),
                        factory.getBean(ErrorDataParser.class));
                break;
            case "gc":
                timeParser = new GCTimeParser(timeZone);
                dataParser = factory.getBean(GCDataParser.class);
                break;
            case "top":
                timeParser = new TopTimeParser(logPath, timeZone);
                dataParser = factory.getBean(TopDataParser.class);
                break;
            default:
                throw new IllegalArgumentException(
                        "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + parsingMode);
        }

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
    }
}
