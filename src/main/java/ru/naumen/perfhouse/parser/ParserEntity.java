package ru.naumen.perfhouse.parser;

import ru.naumen.perfhouse.parser.interfaces.DataParser;
import ru.naumen.perfhouse.parser.interfaces.ParserFactory;
import ru.naumen.perfhouse.parser.interfaces.StoragePacker;

public class ParserEntity {
    private ParserFactory parserFactory;
    private DataParser dataParser;
    private StoragePacker storagePacker;

    void setParserFactory(ParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }

    void setDataParser(DataParser dataParser) {
        this.dataParser = dataParser;
    }

    void setStoragePacker(StoragePacker storagePacker) {
        this.storagePacker = storagePacker;
    }

    boolean isComplete() {
        return this.parserFactory != null && this.dataParser != null && this.storagePacker != null;
    }

    public ParserFactory getParserFactory() {
        return parserFactory;
    }

    public DataParser getDataParser() {
        return dataParser;
    }

    public StoragePacker getStoragePacker() {
        return storagePacker;
    }
}
