package ru.naumen.perfhouse.parser.packers;

import org.influxdb.dto.BatchPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.StoragePacker;
import ru.naumen.perfhouse.parser.annotation.ParsingMode;
import ru.naumen.perfhouse.parser.data.GCData;

@Service
@ParsingMode(name="gc")
public class GCStoragePacker implements StoragePacker<GCData> {
    private InfluxDAO influxDAO;

    @Autowired
    public GCStoragePacker(InfluxDAO influxDAO) {
        this.influxDAO = influxDAO;
    }

    public void store(BatchPoints points, String currentDb, long currentKey,
                      GCData currentSet, boolean printLog) {
        if (!currentSet.isNan())
        {
            influxDAO.storeGc(points, currentDb, currentKey, currentSet);
        }
    }
}
