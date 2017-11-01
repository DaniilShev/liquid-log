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
import ru.naumen.perfhouse.parser.GCParser.GCTimeParser;

/**
 * Created by doki on 22.10.16.
 */
@Service
public class Parser
{
    @Autowired
    private InfluxDAO influxDAO;

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
    public void parse(String dbName, String parsingMode, String logPath, String timeZone, Boolean printLog) throws IOException, ParseException
    {
        dbName = dbName.replaceAll("-", "_");
        influxDAO.connectToDB(dbName);

        String finalDbName = dbName;
        BatchPoints points = influxDAO.startBatchPoints(dbName);
        HashMap<Long, DataSet> data = new HashMap<>();

        TimeParser timeParser = new TimeParser(timeZone);
        GCTimeParser gcTime = new GCTimeParser(timeZone);

        switch (parsingMode)
        {
        case "sdng":
            //Parse sdng
            try (BufferedReader br = new BufferedReader(new FileReader(logPath), 32 * 1024 * 1024))
            {
                String line;
                while ((line = br.readLine()) != null)
                {
                    long time = timeParser.parseLine(line);

                    if (time == 0)
                    {
                        continue;
                    }

                    int min5 = 5 * 60 * 1000;
                    long count = time / min5;
                    long key = count * min5;

                    data.computeIfAbsent(key, k -> new DataSet()).parseLine(line);
                }
            }
            break;
        case "gc":
            //Parse gc log
            try (BufferedReader br = new BufferedReader(new FileReader(logPath)))
            {
                String line;
                while ((line = br.readLine()) != null)
                {
                    long time = gcTime.parseTime(line);

                    if (time == 0)
                    {
                        continue;
                    }

                    int min5 = 5 * 60 * 1000;
                    long count = time / min5;
                    long key = count * min5;
                    data.computeIfAbsent(key, k -> new DataSet()).parseGcLine(line);
                }
            }
            break;
        case "top":
            TopParser topParser = new TopParser(logPath, data);
            topParser.configureTimeZone(timeZone);
            //Parse top
            topParser.parse();
            break;
        default:
            throw new IllegalArgumentException(
                    "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + parsingMode);
        }

        if (printLog)
        {
            System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");
        }
        BatchPoints finalPoints = points;
        data.forEach((k, set) ->
        {
            ActionDoneParser dones = set.getActionsDone();
            dones.calculate();
            ErrorParser erros = set.getErrors();
            if (printLog)
            {
                System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n", k, dones.getCount(),
                        dones.getMin(), dones.getMean(), dones.getStddev(), dones.getPercent50(), dones.getPercent95(),
                        dones.getPercent99(), dones.getPercent999(), dones.getMax(), erros.getErrorCount()));
            }
            if (!dones.isNan())
            {
                influxDAO.storeActionsFromLog(finalPoints, finalDbName, k, dones, erros);
            }

            GCParser gc = set.getGc();
            if (!gc.isNan())
            {
                influxDAO.storeGc(finalPoints, finalDbName, k, gc);
            }

            TopData cpuData = set.cpuData();
            if (!cpuData.isNan())
            {
                influxDAO.storeTop(finalPoints, finalDbName, k, cpuData);
            }
        });
        influxDAO.writeBatch(points);
    }
}