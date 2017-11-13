package ru.naumen.perfhouse.influx;

import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.QueryResult;
import org.json.JSONObject;
import ru.naumen.perfhouse.parser.ActionDoneParser;
import ru.naumen.perfhouse.parser.ErrorParser;
import ru.naumen.perfhouse.parser.GCData;
import ru.naumen.perfhouse.parser.TopData;

import java.util.List;

public interface InfluxDAO {
    void connectToDB(String dbName);

    void destroy();

    QueryResult.Series executeQuery(String dbName, String query);

    List<String> getDbList();

    void init();

    BatchPoints startBatchPoints(String dbName);

    void storeActionsFromLog(BatchPoints batch, String dbName, long date,
                                    ActionDoneParser dones,
                                    ErrorParser errors);

    void storeFromJSon(BatchPoints batch, String dbName, JSONObject data);

    void storeGc(BatchPoints batch, String dbName, long date, GCData gc);

    void storeTop(BatchPoints batch, String dbName, long date, TopData data);

    void writeBatch(BatchPoints batch);
}
