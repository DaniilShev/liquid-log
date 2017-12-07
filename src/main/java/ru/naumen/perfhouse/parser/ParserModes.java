package ru.naumen.perfhouse.parser;

import org.apache.commons.lang3.tuple.MutableTriple;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.annotation.ParsingMode;

import java.util.*;

@Service
public class ParserModes {
    private Map<String, MutableTriple<ParserFactory, DataParser, StoragePacker>> modes = new HashMap<>();

    @Autowired
    public ParserModes(ListableBeanFactory beanFactory) {
        receiveModes(beanFactory);
        removeIncompleteModes();
    }

    private void receiveModes(ListableBeanFactory beanFactory) {
        Map<String, Object> parserBeans = beanFactory.getBeansWithAnnotation(ParsingMode.class);

        for (Object parserBean : parserBeans.values()) {
            String modeName = parserBean.getClass().getAnnotation(ParsingMode.class).name();

            if (!modes.containsKey(modeName)) {
                modes.put(modeName, new MutableTriple<>());
            }

            if (parserBean instanceof ParserFactory) {
                modes.get(modeName).setLeft((ParserFactory)parserBean);
                continue;
            }
            if (parserBean instanceof DataParser) {
                modes.get(modeName).setMiddle((DataParser)parserBean);
                continue;
            }
            if (parserBean instanceof StoragePacker) {
                modes.get(modeName).setRight((StoragePacker)parserBean);
            }
        }
    }

    private void removeIncompleteModes() {
        for(Iterator<Map.Entry<String, MutableTriple<ParserFactory, DataParser, StoragePacker>>> it = modes.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, MutableTriple<ParserFactory, DataParser, StoragePacker>> entry = it.next();

            String modeName = entry.getKey();
            MutableTriple<?,?,?> mode = entry.getValue();
            if (mode.getLeft() == null || mode.getMiddle() == null || mode.getRight() == null) {
                it.remove();

                System.out.println(String.format("%s parser mode is incomplete. Ignored!", modeName));
            }
        }
    }

    public List<String> getModes() {
        return new ArrayList<>(modes.keySet());
    }

    boolean hasMode(String mode) {
        return modes.containsKey(mode);
    }

    ParserFactory getParserFactory(String mode) {
        return modes.get(mode).getLeft();
    }

    DataParser getDataParser(String mode) {
        return modes.get(mode).getMiddle();
    }

    StoragePacker getStoragePacker(String mode) {
        return modes.get(mode).getRight();
    }
}
