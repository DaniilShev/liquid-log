package ru.naumen.perfhouse.plugins.gc;

import com.google.common.collect.Lists;
import ru.naumen.perfhouse.statdata.Constants;

import java.util.List;

import static ru.naumen.perfhouse.statdata.Constants.TIME;

public class GCConstants
{
    private GCConstants()
    {
    }

    public static final String GCTIMES = "gcTimes";
    public static final String AVARAGE_GC_TIME = "avgGcTime";
    public static final String MAX_GC_TIME = "maxGcTime";

    static List<String> getProps()
    {
        return Lists.newArrayList(TIME, GCTIMES, AVARAGE_GC_TIME, MAX_GC_TIME);
    }
}