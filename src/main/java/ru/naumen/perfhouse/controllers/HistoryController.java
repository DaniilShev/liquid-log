package ru.naumen.perfhouse.controllers;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import ru.naumen.perfhouse.statdata.PluginConstants;
import ru.naumen.perfhouse.statdata.StatData;
import ru.naumen.perfhouse.statdata.StatDataService;
import ru.naumen.perfhouse.tabs.HistoryTabs;

/**
 * Created by doki on 23.10.16.
 */
abstract public class HistoryController
{
    @Autowired
    private StatDataService service;

    @Autowired
    private HistoryTabs tabs;

    private static final String NO_HISTORY_VIEW = "no_history";

    public abstract ModelAndView getByDay(String client, int year, int month, int day) throws ParseException;

    public abstract ModelAndView getByMonth(String client, int year, int month) throws ParseException;

    public abstract ModelAndView getLast(String client, int count) throws ParseException;

    public abstract ModelAndView getCustom(String client, String from, String to, int count) throws ParseException;

    protected ModelAndView getDataAndView(String client, PluginConstants constants, int count, String viewName)
            throws ParseException
    {
        ru.naumen.perfhouse.statdata.StatData data = service.getData(client, constants, count);
        if (data == null)
        {
            return new ModelAndView(NO_HISTORY_VIEW);
        }
        Map<String, Object> model = new HashMap<>(data.asModel());
        model.put("client", client);
        model.put("constants", constants);
        model.put("tabs", tabs.getTabs());

        return new ModelAndView(viewName, model, HttpStatus.OK);
    }

    protected ModelAndView getDataAndViewByDate(String client, PluginConstants type, int year, int month, int day,
                                                String viewName) throws ParseException
    {
        return getDataAndViewByDate(client, type, year, month, day, viewName, false);
    }

    protected ModelAndView getDataAndViewByDate(String client, PluginConstants constants, int year, int month, int day,
                                                String viewName, boolean compress) throws ParseException
    {
        ru.naumen.perfhouse.statdata.StatData dataDate = service.getDataDate(client, constants, year, month, day);
        if (dataDate == null)
        {
            return new ModelAndView(NO_HISTORY_VIEW);
        }

        dataDate = compress ? service.compress(dataDate, 3 * 60 * 24 / 5) : dataDate;
        Map<String, Object> model = new HashMap<>(dataDate.asModel());
        model.put("client", client);
        model.put("constants", constants);
        model.put("tabs", tabs.getTabs());
        model.put("year", year);
        model.put("month", month);
        model.put("day", day);
        return new ModelAndView(viewName, model, HttpStatus.OK);
    }

    protected ModelAndView getDataAndViewCustom(String client, PluginConstants constants, String from, String to, int maxResults,
                                                String viewName) throws ParseException
    {
        StatData data = service.getDataCustom(client, constants, from, to);
        if (data == null)
        {
            return new ModelAndView(NO_HISTORY_VIEW);
        }
        data = service.compress(data, maxResults);
        Map<String, Object> model = new HashMap<>(data.asModel());
        model.put("client", client);
        model.put("constants", constants);
        model.put("tabs", tabs.getTabs());
        model.put("custom", true);
        model.put("from", from);
        model.put("to", to);
        model.put("maxResults", maxResults);
        return new ModelAndView(viewName, model, HttpStatus.OK);
    }
}
