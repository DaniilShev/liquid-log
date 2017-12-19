package ru.naumen.perfhouse.plugins.sdng.parser;

import org.influxdb.dto.BatchPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.interfaces.StoragePacker;
import ru.naumen.perfhouse.parser.ParsingMode;
import ru.naumen.perfhouse.plugins.sdng.parser.ActionDoneData;
import ru.naumen.perfhouse.plugins.sdng.parser.ErrorData;
import ru.naumen.perfhouse.plugins.sdng.parser.SdngData;

@Service
@ParsingMode(name="sdng")
public class SdngStoragePacker implements StoragePacker<SdngData> {
    private InfluxDAO influxDAO;

    @Autowired
    public SdngStoragePacker(InfluxDAO influxDAO) {
        this.influxDAO = influxDAO;
    }

    public void store(BatchPoints points, String currentDb, long currentKey,
                      SdngData currentSet, boolean printLog) {

        ActionDoneData dones = currentSet.getActionDone();
        dones.calculate();

        ErrorData erros = currentSet.getErrors();

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
