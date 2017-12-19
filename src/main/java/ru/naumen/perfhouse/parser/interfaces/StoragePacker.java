package ru.naumen.perfhouse.parser.interfaces;

import org.influxdb.dto.BatchPoints;
import ru.naumen.perfhouse.parser.interfaces.DataSet;

public interface StoragePacker<T extends DataSet> {
    void store(BatchPoints points, String currentDb, long currentKey,
                      T currentSet, boolean printLog);
}
