package ru.naumen.perfhouse.parser;

import static ru.naumen.perfhouse.parser.NumberUtils.getSafeDouble;
import static ru.naumen.perfhouse.parser.NumberUtils.roundToTwoPlaces;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;;

/**
 * Cpu usage data, acquired from top output
 * @author dkolmogortsev
 *
 */
public class TopData
{
    private DescriptiveStatistics loadAvgStat = new DescriptiveStatistics();
    private DescriptiveStatistics cpuStat = new DescriptiveStatistics();
    private DescriptiveStatistics memStat = new DescriptiveStatistics();

    public void addLa(double la)
    {
        loadAvgStat.addValue(la);
    }

    public void addCpu(double cpu)
    {
        cpuStat.addValue(cpu);
    }

    public void addMem(double mem)
    {
        memStat.addValue(mem);
    }

    public boolean isNan()
    {
        return loadAvgStat.getN() == 0 && cpuStat.getN() == 0 && memStat.getN() == 0;
    }

    public double getAvgLa()
    {
        return roundToTwoPlaces(getSafeDouble(loadAvgStat.getMean()));
    }

    public double getAvgCpuUsage()
    {
        return roundToTwoPlaces(getSafeDouble(cpuStat.getMean()));
    }

    public double getAvgMemUsage()
    {
        return roundToTwoPlaces(getSafeDouble(memStat.getMean()));
    }

    public double getMaxLa()
    {
        return roundToTwoPlaces(getSafeDouble(loadAvgStat.getMax()));
    }

    public double getMaxCpu()
    {
        return roundToTwoPlaces(getSafeDouble(cpuStat.getMax()));
    }

    public double getMaxMem()
    {
        return roundToTwoPlaces(getSafeDouble(memStat.getMax()));
    }
}