package ru.naumen.perfhouse.plugins.sdng;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.statdata.PluginConstants;

import java.util.List;

import static ru.naumen.perfhouse.statdata.Constants.TIME;

@Component
public class ResponseTimesConstants implements PluginConstants
{
    public final String PERCENTILE50 = "percent50";
    public final String PERCENTILE95 = "percent95";
    public final String PERCENTILE99 = "percent99";
    public final String PERCENTILE999 = "percent999";
    public final String MAX = "max";
    public final String COUNT = "count";
    public final String ERRORS = "errors";
    public final String MEAN = "mean";
    public final String STDDEV = "stddev";

    public List<String> getProps()
    {
        return Lists.newArrayList(TIME, COUNT, ERRORS, MEAN, STDDEV, PERCENTILE50, PERCENTILE95, PERCENTILE99,
                PERCENTILE999, MAX);
    }
}
