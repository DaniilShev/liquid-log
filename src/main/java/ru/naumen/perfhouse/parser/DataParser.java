package ru.naumen.perfhouse.parser;

public interface DataParser<T extends DataSet>
{
    void parseLine(String line, T currentSet);
}
