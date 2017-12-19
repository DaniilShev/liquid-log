package ru.naumen.perfhouse.plugins.top.parser;

import org.influxdb.dto.BatchPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.interfaces.StoragePacker;
import ru.naumen.perfhouse.parser.ParsingMode;

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
            influxDAO.storeTop(points, currentDb, currentKey, currentSet);
        }
    }
}
