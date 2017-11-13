package ru.naumen.perfhouse.parser;

import org.influxdb.dto.BatchPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.influx.InfluxDAOInterface;

@Service
class Storage {
    private String currentDb;
    private boolean log;
    private long currentKey;
    private DataSet currentSet;
    private BatchPoints points;
    private InfluxDAOInterface influxDAO;

    @Autowired
    Storage(InfluxDAOInterface influxDAOInterface) {
        influxDAO = influxDAOInterface;
    }

    void init(String dbName, boolean printLog) {
        currentDb = dbName.replaceAll("-", "_");
        log = printLog;

        if (log)
        {
            System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");
        }

        influxDAO.connectToDB(currentDb);
        points = influxDAO.startBatchPoints(currentDb);
    }

    DataSet get(long key) {
        if (key != currentKey) {
            store();

            currentKey = key;
            currentSet = new DataSet();
        }

        return currentSet;
    }

    private void store() {
        if (currentSet == null) {
            return;
        }

        ActionDoneParser dones = currentSet.getActionsDone();
        dones.calculate();
        ErrorParser erros = currentSet.getErrors();

        if (log)
        {
            System.out.print(
                    String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n",
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

        GCData gc = currentSet.getGc();
        if (!gc.isNan())
        {
            influxDAO.storeGc(points, currentDb, currentKey, gc);
        }

        TopData cpuData = currentSet.getCpuData();
        if (!cpuData.isNan())
        {
            influxDAO.storeTop(points, currentDb, currentKey, cpuData);
        }
    }

    void save() {
        influxDAO.writeBatch(points);
    }
}
