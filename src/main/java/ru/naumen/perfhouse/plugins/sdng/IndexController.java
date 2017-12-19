package ru.naumen.perfhouse.plugins.sdng;

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
@HistoryTab(name = "Responses", path = "")
public class IndexController extends HistoryController
{
    private static final String HISTORY_VIEW = "history";

    @Autowired
    private ResponseTimesConstants constants;

    @RequestMapping(path = "/history/{client}/{year}/{month}/{day}")
    public ModelAndView getByDay(@PathVariable("client") String client,
                                   @PathVariable(name = "year", required = false) int year,
                                   @PathVariable(name = "month", required = false) int month,
                                   @PathVariable(name = "day", required = false) int day) throws ParseException
    {
        return getDataAndViewByDate(client, constants, year, month, day, HISTORY_VIEW);
    }

    @RequestMapping(path = "/history/{client}/{year}/{month}")
    public ModelAndView getByMonth(@PathVariable("client") String client,
                                     @PathVariable(name = "year", required = false) int year,
                                     @PathVariable(name = "month", required = false) int month) throws ParseException
    {
        return getDataAndViewByDate(client, constants, year, month, 0, HISTORY_VIEW, true);
    }

    @RequestMapping(path = "/history/{client}")
    public ModelAndView getLast(@PathVariable("client") String client,
                                     @RequestParam(name = "count", defaultValue = "864") int count) throws ParseException
    {
        return getDataAndView(client, constants, count, HISTORY_VIEW);
    }

    @RequestMapping(path = "/history/{client}/custom")
    public ModelAndView getCustom(@PathVariable("client") String client, @RequestParam("from") String from,
                                    @RequestParam("to") String to, @RequestParam("maxResults") int maxResults) throws ParseException
    {
        return getDataAndViewCustom(client, constants, from, to, maxResults, HISTORY_VIEW);
    }
}
