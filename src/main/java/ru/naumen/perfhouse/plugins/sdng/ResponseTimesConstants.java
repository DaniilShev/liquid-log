package ru.naumen.perfhouse.plugins.sdng;

import com.google.common.collect.Lists;

import java.util.List;

import static ru.naumen.perfhouse.statdata.Constants.TIME;

public class ResponseTimesConstants {
    private ResponseTimesConstants()
    {
    }

    public static final String PERCENTILE50 = "percent50";
    public static final String PERCENTILE95 = "percent95";
    public static final String PERCENTILE99 = "percent99";
    public static final String PERCENTILE999 = "percent999";
    public static final String MAX = "max";
    public static final String COUNT = "count";
    public static final String ERRORS = "errors";
    public static final String MEAN = "mean";
    public static final String STDDEV = "stddev";

    static List<String> getProps()
    {
        return Lists.newArrayList(TIME, COUNT, ERRORS, MEAN, STDDEV, PERCENTILE50, PERCENTILE95, PERCENTILE99,
                PERCENTILE999, MAX);
    }
}
