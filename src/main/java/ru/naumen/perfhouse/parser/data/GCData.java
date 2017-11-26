package ru.naumen.perfhouse.parser.data;

import static ru.naumen.perfhouse.parser.NumberUtils.getSafeDouble;
import static ru.naumen.perfhouse.parser.NumberUtils.roundToTwoPlaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import ru.naumen.perfhouse.parser.DataSet;

public class GCData
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
