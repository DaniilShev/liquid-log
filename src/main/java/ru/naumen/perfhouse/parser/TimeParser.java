package ru.naumen.perfhouse.parser;

import java.text.ParseException;

public interface TimeParser
{
    long parseTime(String line) throws ParseException;
}
