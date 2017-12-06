package ru.naumen.perfhouse.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.packers.*;
import ru.naumen.perfhouse.parser.parsers.data.*;
import ru.naumen.perfhouse.parser.factories.*;

/**
 * Created by doki on 22.10.16.
 */
@Service
public class Parser
{

    private BeanFactory factory;

    private CompositeDataParser sdngDataParser;
    private TopDataParser topDataParser;
    private GCDataParser gcDataParser;

    private SdngStoragePacker sdngStoragePacker;
    private TopStoragePacker topStoragePacker;
    private GCStoragePacker gcStoragePacker;

    private SdngParserFactory sdngParserFactory;
    private TopParserFactory topParserFactory;
    private GCParserFactory gcParserFactory;

    @Autowired
    public Parser(BeanFactory factory, TopDataParser topDataParser,
                  GCDataParser gcDataParser,
                  ActionDoneDataParser actionDoneDataParser,
                  ErrorDataParser errorDataParser, SdngStoragePacker sdngStoragePacker,
                  TopStoragePacker topStoragePacker, GCStoragePacker gcStoragePacker,
                  SdngParserFactory sdngParserFactory, TopParserFactory topParserFactory,
                  GCParserFactory gcParserFactory) {
        this.factory = factory;

        this.sdngDataParser = new CompositeDataParser(actionDoneDataParser,
                errorDataParser);
        this.topDataParser = topDataParser;
        this.gcDataParser = gcDataParser;

        this.sdngStoragePacker = sdngStoragePacker;
        this.topStoragePacker = topStoragePacker;
        this.gcStoragePacker = gcStoragePacker;

        this.sdngParserFactory = sdngParserFactory;
        this.topParserFactory = topParserFactory;
        this.gcParserFactory = gcParserFactory;
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

        ParserFactory parserFactory;
        DataParser dataParser;
        StoragePacker storagePacker;
        switch (parsingMode)
        {
            case "sdng":
                parserFactory = sdngParserFactory;
                dataParser = sdngDataParser;
                storagePacker = sdngStoragePacker;
                break;
            case "gc":
                parserFactory = gcParserFactory;
                dataParser = gcDataParser;
                storagePacker = gcStoragePacker;
                break;
            case "top":
                parserFactory = topParserFactory;
                dataParser = topDataParser;
                storagePacker = topStoragePacker;
                break;
            default:
                throw new IllegalArgumentException(
                        "Unknown parse mode! Available modes: sdng, gc, top. Requested mode: " + parsingMode);
        }

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
