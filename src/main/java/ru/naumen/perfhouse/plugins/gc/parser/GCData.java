package ru.naumen.perfhouse.plugins.gc.parser;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import ru.naumen.perfhouse.parser.interfaces.DataSet;

import static ru.naumen.perfhouse.parser.NumberUtils.getSafeDouble;
import static ru.naumen.perfhouse.parser.NumberUtils.roundToTwoPlaces;

public class GCData implements DataSet
{
    private DescriptiveStatistics ds = new DescriptiveStatistics();

    public void addValue(double value)
    {
        ds.addValue(value);
    }

    public double getCalculatedAvg()
    {
        return roundToTwoPlaces(getSafeDouble(ds.getMean()));
    }

    public long getGcTimes()
    {
        return ds.getN();
    }

    public double getMaxGcTime()
    {
        return roundToTwoPlaces(getSafeDouble(ds.getMax()));
    }

    public boolean isNan()
    {
        return getGcTimes() == 0;
    }
}
