package ru.naumen.perfhouse.parser;

public interface ParserFactory {
    DataSet getDataSet ();
    TimeParser getTimeParser(String logPath, String timeZone);
}
