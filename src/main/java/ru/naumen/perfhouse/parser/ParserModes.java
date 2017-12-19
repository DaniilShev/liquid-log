package ru.naumen.perfhouse.parser;

import org.apache.commons.lang3.tuple.MutableTriple;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.DataParser;
import ru.naumen.perfhouse.parser.interfaces.ParserFactory;
import ru.naumen.perfhouse.parser.interfaces.StoragePacker;

import java.util.*;

@Service
public class ParserModes {
    private Map<String, ParserEntity> modes = new HashMap<>();

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
                modes.put(modeName, new ParserEntity());
            }

            if (parserBean instanceof ParserFactory) {
                modes.get(modeName).setParserFactory((ParserFactory)parserBean);
                continue;
            }
            if (parserBean instanceof DataParser) {
                modes.get(modeName).setDataParser((DataParser)parserBean);
                continue;
            }
            if (parserBean instanceof StoragePacker) {
                modes.get(modeName).setStoragePacker((StoragePacker)parserBean);
            }
        }
    }

    private void removeIncompleteModes() {
        for(Iterator<Map.Entry<String, ParserEntity>> it = modes.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, ParserEntity> entry = it.next();

            if (!entry.getValue().isComplete()) {
                it.remove();

                System.out.println(String.format("%s parser mode is incomplete. Ignored!", entry.getKey()));
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
        return modes.get(mode).getParserFactory();
    }

    DataParser getDataParser(String mode) {
        return modes.get(mode).getDataParser();
    }

    StoragePacker getStoragePacker(String mode) {
        return modes.get(mode).getStoragePacker();
    }
}
