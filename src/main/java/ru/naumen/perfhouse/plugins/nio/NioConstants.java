package ru.naumen.perfhouse.plugins.nio;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.statdata.PluginConstants;

import java.util.List;

import static ru.naumen.perfhouse.statdata.Constants.TIME;

@Component
public class NioConstants implements PluginConstants {
    public final String AVG_TIME = "avgTime";
    public final String MAX_TIME = "maxTime";

    public List<String> getProps() {
        return Lists.newArrayList(TIME, AVG_TIME, MAX_TIME);
    }
}
