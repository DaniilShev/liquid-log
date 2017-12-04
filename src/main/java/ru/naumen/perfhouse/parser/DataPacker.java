package ru.naumen.perfhouse.parser;

import org.influxdb.dto.BatchPoints;

public interface DataPacker<T extends DataSet> {
    void store(BatchPoints points, String currentDb, long currentKey,
                      T currentSet, boolean printLog);
}
