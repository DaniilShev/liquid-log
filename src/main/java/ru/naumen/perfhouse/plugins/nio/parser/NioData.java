package ru.naumen.perfhouse.plugins.nio.parser;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import ru.naumen.perfhouse.parser.interfaces.DataSet;

import static ru.naumen.perfhouse.parser.NumberUtils.getSafeDouble;
import static ru.naumen.perfhouse.parser.NumberUtils.roundToTwoPlaces;

/**
 * Created by doki on 22.10.16.
 */
public class NioData implements DataSet
{
    private DescriptiveStatistics timeStat = new DescriptiveStatistics();

    public void addValue(long time)
    {
        timeStat.addValue(time);
    }

    public boolean isNan()
    {
        return timeStat.getN() == 0;
    }

    public double getAvg()
    {
        return roundToTwoPlaces(getSafeDouble(timeStat.getMean()));
    }

    public double getMax()
    {
        return roundToTwoPlaces(getSafeDouble(timeStat.getMax()));
    }
}
