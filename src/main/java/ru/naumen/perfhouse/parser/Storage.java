package ru.naumen.perfhouse.parser;

import org.influxdb.dto.BatchPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.influx.InfluxDAO;

@Service
@Scope("request")
class Storage {
    private String currentDb;
    private boolean printLog;
    private long currentKey;
    private DataSet currentSet;
    private BatchPoints points;
    private InfluxDAO influxDAO;
    private DataPacker dataPacker;
    private ParserFactory parserFactory;

    @Autowired
    Storage(InfluxDAO influxDAO) {
        this.influxDAO = influxDAO;
    }

    void init(ParserFactory parserFactory, DataPacker dataPacker, String dbName, boolean printLog) {
        this.parserFactory = parserFactory;
        this.dataPacker = dataPacker;

        currentDb = dbName.replaceAll("-", "_");
        this.printLog = printLog;

        influxDAO.connectToDB(currentDb);
        points = influxDAO.startBatchPoints(currentDb);
    }

    DataSet get(long key) {
        if (key != currentKey) {
            store();

            currentKey = key;
            currentSet = parserFactory.getDataSet();
        }

        return currentSet;
    }

    private void store() {
        if (currentSet == null) {
            return;
        }

        dataPacker.store(points, currentDb, currentKey, currentSet, printLog);
    }

    void close() {
        store();
        influxDAO.writeBatch(points);
    }
}
