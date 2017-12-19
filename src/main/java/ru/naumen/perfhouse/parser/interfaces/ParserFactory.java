package ru.naumen.perfhouse.parser.interfaces;

public interface ParserFactory {
    DataSet getDataSet ();
    TimeParser getTimeParser(String logPath, String timeZone);
}
