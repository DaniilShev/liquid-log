package ru.naumen.perfhouse.tabs;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HistoryTabs implements BeanPostProcessor {
    @Autowired
    private ListableBeanFactory beanFactory;

    private Map<String, String> tabs = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        HistoryTab annotation = bean.getClass().getAnnotation(HistoryTab.class);
        if (annotation != null) {
            tabs.put(annotation.name(), annotation.path());
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Map<String, String> getTabs() {
        return tabs;
    }
}
