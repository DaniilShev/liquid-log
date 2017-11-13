package ru.naumen.perfhouse.parser;

public interface DataParser
{
    void parseLine(String line, DataSet currentSet);
}
