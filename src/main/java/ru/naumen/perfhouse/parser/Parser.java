package ru.naumen.perfhouse.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.influxdb.dto.BatchPoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.data.*;
import ru.naumen.perfhouse.parser.time.*;

/**
 * Created by doki on 22.10.16.
 */
@Service
public class Parser
{
    /**
     * 
     * @param dbName - Name of database for saving logs
     * @param parsingMode - Mode of parser
     * @param logPath - Path to log file
     * @param timeZone - Time zone
     * @param printLog - Should print logs in console
     * @throws IOException
     * @throws ParseException
     */
    public void parse(String dbName, String parsingMode, String logPath,
                      String timeZone, Boolean printLog)
            throws IOException, ParseException
    {
        Storage storage = new Storage(dbName, printLog);

        TimeParser timeParser;
        DataParser dataParser;

        switch (parsingMode)
        {
            case "sdng":
                timeParser = new SdngTimeParser(timeZone);
                dataParser = new CompositeDataParser(
                        new ActionDataParser(), new ErrorDataParser());
                break;
            case "gc":
                timeParser = new GCTimeParser(timeZone);
                dataParser = new GCDataParser();
                break;
            case "top":
                timeParser = new TopTimeParser(logPath, timeZone);
                dataParser = new TopDataParser();
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

        storage.save();
    }
}
