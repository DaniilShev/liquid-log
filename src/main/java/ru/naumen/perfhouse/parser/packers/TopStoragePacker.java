package ru.naumen.perfhouse.parser.packers;

import org.influxdb.dto.BatchPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.DataPacker;
import ru.naumen.perfhouse.parser.data.TopData;
import ru.naumen.perfhouse.parser.sets.TopDataSet;

@Service
public class TopStoragePacker implements DataPacker<TopDataSet> {
    private InfluxDAO influxDAO;

    @Autowired
    TopStoragePacker(InfluxDAO influxDAO) {
        this.influxDAO = influxDAO;
    }

    public void store(BatchPoints points, String currentDb, long currentKey,
                      TopDataSet currentSet, boolean printLog) {
        TopData cpuData = currentSet.getData();
        if (!cpuData.isNan())
        {
            influxDAO.storeTop(points, currentDb, currentKey, cpuData);
        }
    }
}
