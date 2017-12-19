package ru.naumen.perfhouse.plugins.gc;


import ru.naumen.perfhouse.statdata.DataType;

import java.util.List;

public class GCDataType extends DataType
{
    public List<String> getTypeProperties() {
        return GCConstants.getProps();
    }
}
