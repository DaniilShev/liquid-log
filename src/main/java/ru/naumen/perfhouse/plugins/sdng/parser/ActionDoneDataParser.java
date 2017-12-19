package ru.naumen.perfhouse.plugins.sdng.parser;

import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.DataParser;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by doki on 22.10.16.
 */
@Service
public class ActionDoneDataParser implements DataParser<SdngData> {
    private static final Pattern doneRegEx = Pattern.compile("Done\\((\\d+)\\): ?(.*?Action)");

    private static Set<String> EXCLUDED_ACTIONS = new HashSet<>();
    static {
        EXCLUDED_ACTIONS.add("EventAction".toLowerCase());
    }

    @Override
    public void parseLine(String line, SdngData currentSet) {
        Matcher matcher = doneRegEx.matcher(line);

        if (!matcher.find()) {
            return;
        }

        String actionInLowerCase = matcher.group(2).toLowerCase();
        if (EXCLUDED_ACTIONS.contains(actionInLowerCase)) {
            return;
        }

        ActionDoneData actionDoneData = currentSet.getActionDone();

        actionDoneData.getTimes().add(Integer.parseInt(matcher.group(1)));
        if (actionInLowerCase.equals("addobjectaction")) {
            actionDoneData.incAddObjectActions();
        } else if (actionInLowerCase.equals("editobjectaction")) {
            actionDoneData.incEditObjectsActions();
        } else if (actionInLowerCase.equals("getcatalogsaction")) {
            actionDoneData.incGetCatalogsActions();
        } else if (actionInLowerCase.matches("(?i)[a-zA-Z]+comment[a-zA-Z]+")) {
            actionDoneData.incCommentActions();
        } else if (!actionInLowerCase.contains("advlist")
                && actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+List[a-zA-Z]+"))

        {
            actionDoneData.incGetListActions();
        } else if (actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+Form[a-zA-Z]+")) {
            actionDoneData.incGetFormActions();
        } else if (actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+DtObject[a-zA-Z]+")) {
            actionDoneData.incGetDtObjectActions();
        } else if (actionInLowerCase.matches("(?i)[a-zA-Z]+search[a-zA-Z]+")) {
            actionDoneData.incSearchActions();
        }
    }
}