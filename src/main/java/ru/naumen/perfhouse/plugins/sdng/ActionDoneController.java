package ru.naumen.perfhouse.plugins.sdng;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.naumen.perfhouse.controllers.HistoryController;
import ru.naumen.perfhouse.tabs.HistoryTab;

import java.text.ParseException;

@Controller
@HistoryTab(name = "Performed actions", path = "/actions")
public class ActionDoneController extends HistoryController
{
    private static final String ACTIONS_VIEW = "history_actions";

    @RequestMapping(path = "/history/{client}/actions/{year}/{month}/{day}")
    public ModelAndView getByDay(@PathVariable("client") String client,
                                     @PathVariable(name = "year", required = false) int year,
                                     @PathVariable(name = "month", required = false) int month,
                                     @PathVariable(name = "day", required = false) int day) throws ParseException
    {
        return getDataAndViewByDate(client, new ActionDoneDataType(), year, month, day, ACTIONS_VIEW);
    }

    @RequestMapping(path = "/history/{client}/actions/{year}/{month}")
    public ModelAndView getByMonth(@PathVariable("client") String client,
                                       @PathVariable(name = "year", required = false) int year,
                                       @PathVariable(name = "month", required = false) int month) throws ParseException
    {
        return getDataAndViewByDate(client, new ActionDoneDataType(), year, month, 0, ACTIONS_VIEW, true);
    }

    @RequestMapping(path = "/history/{client}/actions")
    public ModelAndView getLast(@PathVariable("client") String client,
                                       @RequestParam(name = "count", defaultValue = "864") int count) throws ParseException
    {
        return getDataAndView(client, new ActionDoneDataType(), count, ACTIONS_VIEW);
    }

    @RequestMapping(path = "/history/{client}/custom/actions")
    public ModelAndView getCustom(@PathVariable("client") String client, @RequestParam("from") String from,
                                      @RequestParam("to") String to, @RequestParam("maxResults") int count) throws ParseException {
        return getDataAndViewCustom(client, new ActionDoneDataType(), from, to, count, ACTIONS_VIEW);
    }
}
