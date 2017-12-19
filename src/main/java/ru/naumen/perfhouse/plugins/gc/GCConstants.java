package ru.naumen.perfhouse.plugins.gc;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.statdata.PluginConstants;

import java.util.List;

import static ru.naumen.perfhouse.statdata.Constants.TIME;

@Component
public class GCConstants implements PluginConstants
{

    public final String GCTIMES = "gcTimes";
    public final String AVARAGE_GC_TIME = "avgGcTime";
    public final String MAX_GC_TIME = "maxGcTime";

    public List<String> getProps()
    {
        return Lists.newArrayList(TIME, GCTIMES, AVARAGE_GC_TIME, MAX_GC_TIME);
    }
}