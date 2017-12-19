package ru.naumen.perfhouse.parser.interfaces;

public interface DataParser<T extends DataSet>
{
    void parseLine(String line, T currentSet);
}
