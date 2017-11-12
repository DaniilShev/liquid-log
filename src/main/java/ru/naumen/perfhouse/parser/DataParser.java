package ru.naumen.perfhouse.parser;

public interface DataParser
{
    void setCurrentSet(DataSet dataSet);
    void parseLine(String line);
}
