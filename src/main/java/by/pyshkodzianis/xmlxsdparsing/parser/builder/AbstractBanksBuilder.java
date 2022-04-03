package by.pyshkodzianis.xmlxsdparsing.parser.builder;


import by.pyshkodzianis.xmlxsdparsing.entity.Bank;
import by.pyshkodzianis.xmlxsdparsing.exception.BanksXmlException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractBanksBuilder {
    protected Set<Bank> banks;

    public AbstractBanksBuilder() {
        banks = new HashSet<Bank>();
    }

    public AbstractBanksBuilder(Set<Bank> banks) {
        this.banks = banks;
    }

    public Set<Bank> getBanks() {
        return banks;
    }

    public abstract void buildSetBanks(String filename) throws BanksXmlException;
}