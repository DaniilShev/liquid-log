package ru.naumen.perfhouse.plugins.top;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.statdata.PluginConstants;

import java.util.List;

import static ru.naumen.perfhouse.statdata.Constants.TIME;

@Component
public class TopConstants implements PluginConstants
{

    public final String AVG_LA = "avgLa";
    public final String AVG_CPU = "avgCpu";
    public final String AVG_MEM = "avgMem";
    public final String MAX_LA = "maxLa";
    public final String MAX_CPU = "maxCpu";
    public final String MAX_MEM = "maxMem";

    public List<String> getProps()
    {
        return Lists.newArrayList(TIME, AVG_LA, AVG_CPU, AVG_MEM, MAX_LA, MAX_CPU, MAX_MEM);
    }
}