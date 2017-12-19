package ru.naumen.perfhouse.plugins.top.parser;

import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.interfaces.StoragePacker;
import ru.naumen.perfhouse.parser.ParsingMode;
import ru.naumen.perfhouse.plugins.top.TopConstants;
import ru.naumen.perfhouse.statdata.Constants;

import java.util.concurrent.TimeUnit;

@Service
@ParsingMode(name="top")
public class TopStoragePacker implements StoragePacker<TopData> {
    private TopConstants constants;

    @Autowired
    public TopStoragePacker(TopConstants constants) {
        this.constants = constants;
    }

    public void store(BatchPoints points, String currentDb, long currentKey,
                      TopData currentSet, boolean printLog) {
        if (!currentSet.isNan())
        {
            Point point = Point.measurement(Constants.MEASUREMENT_NAME)
                    .time(currentKey, TimeUnit.MILLISECONDS)
                    .addField(constants.AVG_LA, currentSet.getAvgLa())
                    .addField(constants.AVG_CPU, currentSet.getAvgCpuUsage())
                    .addField(constants.AVG_MEM, currentSet.getAvgMemUsage())
                    .addField(constants.MAX_LA, currentSet.getMaxLa())
                    .addField(constants.MAX_CPU, currentSet.getMaxCpu())
                    .addField(constants.MAX_MEM, currentSet.getMaxMem()).build();

            points.getPoints().add(point);
        }
    }
}
