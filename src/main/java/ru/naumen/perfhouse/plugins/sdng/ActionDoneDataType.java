package ru.naumen.perfhouse.plugins.sdng;

import ru.naumen.perfhouse.statdata.DataType;

import java.util.List;

public class ActionDoneDataType extends DataType {
    public List<String> getTypeProperties() {
        return ActionDoneConstants.getProps();
    }
}