package ru.naumen.perfhouse.plugins.top;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.naumen.perfhouse.controllers.HistoryController;
import ru.naumen.perfhouse.tabs.HistoryTab;

import java.text.ParseException;

@Controller
@HistoryTab(name = "Top data", path = "/top")
public class TopController extends HistoryController
{
    private static final String CPU_VIEW = "history_top";

    @Autowired
    private TopConstants constants;

    @RequestMapping(path = "/history/{client}/top/{year}/{month}/{day}")
    public ModelAndView getByDay(@PathVariable("client") String client,
                                 @PathVariable(name = "year", required = false) int year,
                                 @PathVariable(name = "month", required = false) int month,
                                 @PathVariable(name = "day", required = false) int day) throws ParseException
    {
        return getDataAndViewByDate(client, constants, year, month, day, CPU_VIEW);
    }

    @RequestMapping(path = "/history/{client}/top/{year}/{month}")
    public ModelAndView getByMonth(@PathVariable("client") String client,
                                   @PathVariable(name = "year", required = false) int year,
                                   @PathVariable(name = "month", required = false) int month) throws ParseException
    {
        return getDataAndViewByDate(client, constants, year, month, 0, CPU_VIEW, true);
    }

    @RequestMapping(path = "/history/{client}/top")
    public ModelAndView getLast(@PathVariable("client") String client,
                                   @RequestParam(name = "count", defaultValue = "864") int count) throws ParseException
    {
        return getDataAndView(client, constants, count, CPU_VIEW);
    }

    @RequestMapping(path = "/history/{client}/custom/top")
    public ModelAndView getCustom(@PathVariable("client") String client, @RequestParam("from") String from,
                                  @RequestParam("to") String to, @RequestParam("maxResults") int count) throws ParseException
    {
        return getDataAndViewCustom(client, constants, from, to, count, CPU_VIEW);
    }
}
