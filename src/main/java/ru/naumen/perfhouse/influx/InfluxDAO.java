package ru.naumen.perfhouse.influx;

import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.QueryResult;
import org.json.JSONObject;
import ru.naumen.perfhouse.plugins.gc.parser.GCData;
import ru.naumen.perfhouse.plugins.sdng.parser.ActionDoneData;
import ru.naumen.perfhouse.plugins.sdng.parser.ErrorData;
import ru.naumen.perfhouse.plugins.top.parser.TopData;

import java.util.List;

public interface InfluxDAO {
    void connectToDB(String dbName);

    void destroy();

    QueryResult.Series executeQuery(String dbName, String query);

    List<String> getDbList();

    void init();

    BatchPoints startBatchPoints(String dbName);

    void storeFromJSon(BatchPoints batch, String dbName, JSONObject data);

    void writeBatch(BatchPoints batch);
}
