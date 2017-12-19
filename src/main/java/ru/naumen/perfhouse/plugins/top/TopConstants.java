package ru.naumen.perfhouse.plugins.top;

import com.google.common.collect.Lists;

import java.util.List;

import static ru.naumen.perfhouse.statdata.Constants.TIME;

public class TopConstants
{
    private TopConstants()
    {

    }

    public static final String AVG_LA = "avgLa";
    public static final String AVG_CPU = "avgCpu";
    public static final String AVG_MEM = "avgMem";
    public static final String MAX_LA = "maxLa";
    public static final String MAX_CPU = "maxCpu";
    public static final String MAX_MEM = "maxMem";

    static List<String> getProps()
    {
        return Lists.newArrayList(TIME, AVG_LA, AVG_CPU, AVG_MEM, MAX_LA, MAX_CPU, MAX_MEM);
    }
}