package ru.naumen.perfhouse.parser.packers;

import org.influxdb.dto.BatchPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.DataPacker;
import ru.naumen.perfhouse.parser.Tuple;
import ru.naumen.perfhouse.parser.data.ActionDoneData;
import ru.naumen.perfhouse.parser.data.ErrorData;
import ru.naumen.perfhouse.parser.sets.SdngDataSet;

@Service
public class SdngStoragePacker implements DataPacker<SdngDataSet> {
    private InfluxDAO influxDAO;

    @Autowired
    SdngStoragePacker(InfluxDAO influxDAO) {
        this.influxDAO = influxDAO;
    }

    public void store(BatchPoints points, String currentDb, long currentKey,
                 SdngDataSet currentSet, boolean printLog) {
        Tuple<ActionDoneData, ErrorData> data = currentSet.getData();

        ActionDoneData dones = data.getX();
        dones.calculate();

        ErrorData erros = data.getY();

        if (printLog)
        {
            if (points.getPoints().size() == 0) {
                System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");
            }

            System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n",
                    currentKey, dones.getCount(), dones.getMin(),
                    dones.getMean(), dones.getStddev(),
                    dones.getPercent50(), dones.getPercent95(),
                    dones.getPercent99(), dones.getPercent999(),
                    dones.getMax(), erros.getErrorCount()));
        }

        if (!dones.isNan())
        {
            influxDAO.storeActionsFromLog(points, currentDb, currentKey, dones, erros);
        }
    }
}
