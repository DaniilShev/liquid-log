package ru.naumen.perfhouse.plugins.top.parser;

import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.interfaces.StoragePacker;
import ru.naumen.perfhouse.parser.ParsingMode;
import ru.naumen.perfhouse.statdata.Constants;

import java.util.concurrent.TimeUnit;

import static ru.naumen.perfhouse.plugins.top.TopConstants.*;
import static ru.naumen.perfhouse.plugins.top.TopConstants.MAX_CPU;
import static ru.naumen.perfhouse.plugins.top.TopConstants.MAX_MEM;

@Service
@ParsingMode(name="top")
public class TopStoragePacker implements StoragePacker<TopData> {
    private InfluxDAO influxDAO;

    @Autowired
    public TopStoragePacker(InfluxDAO influxDAO) {
        this.influxDAO = influxDAO;
    }

    public void store(BatchPoints points, String currentDb, long currentKey,
                      TopData currentSet, boolean printLog) {
        if (!currentSet.isNan())
        {
            Point point = Point.measurement(Constants.MEASUREMENT_NAME)
                    .time(currentKey, TimeUnit.MILLISECONDS)
                    .addField(AVG_LA, currentSet.getAvgLa())
                    .addField(AVG_CPU, currentSet.getAvgCpuUsage())
                    .addField(AVG_MEM, currentSet.getAvgMemUsage())
                    .addField(MAX_LA, currentSet.getMaxLa())
                    .addField(MAX_CPU, currentSet.getMaxCpu())
                    .addField(MAX_MEM, currentSet.getMaxMem()).build();

            points.getPoints().add(point);
        }
    }
}
