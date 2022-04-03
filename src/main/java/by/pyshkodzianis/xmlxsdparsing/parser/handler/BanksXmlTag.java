package by.pyshkodzianis.xmlxsdparsing.parser.handler;


public enum BanksXmlTag {
    BANKS("banks"),
    BANK("bank"),
    DEPOSITOR("depositor"),
    DEPOSIT_ID("deposit-id"),
    OPEN_DATE("open-date"),
    TIME_CONSTRAINS("time-constrains"),
    METAL_DEPOSIT("metal-deposit"),
    CURRENCY_DEPOSIT("currency-deposit"),
    AMOUNT("amount"),
    PROFITABILITY("profitability"),
    METAL_CURRENCY("metal-currency"),
    CURRENCY("currency");


    private String value;

    BanksXmlTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}