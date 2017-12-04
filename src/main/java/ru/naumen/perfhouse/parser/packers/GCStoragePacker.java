package ru.naumen.perfhouse.parser.packers;

import org.influxdb.dto.BatchPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.DataPacker;
import ru.naumen.perfhouse.parser.data.GCData;
import ru.naumen.perfhouse.parser.sets.GCDataSet;

@Service
public class GCStoragePacker implements DataPacker<GCDataSet> {
    private InfluxDAO influxDAO;

    @Autowired
    GCStoragePacker(InfluxDAO influxDAO) {
        this.influxDAO = influxDAO;
    }

    public void store(BatchPoints points, String currentDb, long currentKey,
                      GCDataSet currentSet, boolean printLog) {
        GCData gc = currentSet.getData();
        if (!gc.isNan())
        {
            influxDAO.storeGc(points, currentDb, currentKey, gc);
        }
    }
}
