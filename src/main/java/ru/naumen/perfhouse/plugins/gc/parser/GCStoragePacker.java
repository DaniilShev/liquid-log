package ru.naumen.perfhouse.plugins.gc.parser;

import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.interfaces.StoragePacker;
import ru.naumen.perfhouse.parser.ParsingMode;
import ru.naumen.perfhouse.plugins.gc.GCConstants;
import ru.naumen.perfhouse.statdata.Constants;

import java.util.concurrent.TimeUnit;

@Service
@ParsingMode(name="gc")
public class GCStoragePacker implements StoragePacker<GCData> {
    private GCConstants constants;

    @Autowired
    public GCStoragePacker(GCConstants constants) {
        this.constants = constants;
    }

    public void store(BatchPoints points, String currentDb, long currentKey,
                      GCData currentSet, boolean printLog) {
        if (!currentSet.isNan())
        {
            Point point = Point.measurement(Constants.MEASUREMENT_NAME)
                    .time(currentKey, TimeUnit.MILLISECONDS)
                    .addField(constants.GCTIMES, currentSet.getGcTimes())
                    .addField(constants.AVARAGE_GC_TIME, currentSet.getCalculatedAvg())
                    .addField(constants.MAX_GC_TIME, currentSet.getMaxGcTime())
                    .build();

            points.getPoints().add(point);
        }
    }
}
