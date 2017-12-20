package ru.naumen.perfhouse.plugins.nio.parser;

import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.StoragePacker;
import ru.naumen.perfhouse.parser.ParsingMode;
import ru.naumen.perfhouse.plugins.nio.NioConstants;
import ru.naumen.perfhouse.statdata.Constants;

import java.util.concurrent.TimeUnit;

@Service
@ParsingMode(name="nio")
public class NioStoragePacker implements StoragePacker<NioData> {
    private NioConstants constants;

    @Autowired
    public NioStoragePacker(NioConstants constants) {
        this.constants = constants;
    }

    public void store(BatchPoints points, String currentDb, long currentKey,
                      NioData currentSet, boolean printLog) {

        if (!currentSet.isNan())
        {
            Point point = Point.measurement(Constants.MEASUREMENT_NAME)
                    .time(currentKey, TimeUnit.MILLISECONDS)
                    .addField(constants.AVG_TIME, currentSet.getAvg())
                    .addField(constants.MAX_TIME, currentSet.getMax())
                    .build();

            points.getPoints().add(point);
        }
    }
}
