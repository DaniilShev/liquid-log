package ru.naumen.perfhouse.parser.interfaces;

import java.text.ParseException;

public interface TimeParser
{
    long parseTime(String line) throws ParseException;
}
