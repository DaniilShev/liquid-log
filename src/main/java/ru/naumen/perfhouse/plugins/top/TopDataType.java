package ru.naumen.perfhouse.plugins.top;

import ru.naumen.perfhouse.statdata.DataType;

import java.util.List;

public class TopDataType extends DataType {
    public List<String> getTypeProperties() {
        return TopConstants.getProps();
    }
}
