package ru.naumen.perfhouse.tabs;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HistoryTabs {
    @Autowired
    private ListableBeanFactory beanFactory;

    private Map<String, String> tabs = new HashMap<>();

    private synchronized Map<String, String> calculateTabs() {
        if (tabs.size() == 0) {
            Map<String, Object> parserBeans = beanFactory.getBeansWithAnnotation(HistoryTab.class);

            for (Object parserBean : parserBeans.values()) {
                HistoryTab annotation = parserBean.getClass().getAnnotation(HistoryTab.class);

                tabs.put(annotation.name(), annotation.path());
            }
        }

        return tabs;
    }

    public Map<String, String> getTabs() {
        return calculateTabs();
    }
}
