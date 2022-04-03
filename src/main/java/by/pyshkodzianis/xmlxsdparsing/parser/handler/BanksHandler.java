package by.pyshkodzianis.xmlxsdparsing.parser.handler;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import by.pyshkodzianis.xmlxsdparsing.entity.Bank;
import by.pyshkodzianis.xmlxsdparsing.entity.CurrencyDeposit;
import by.pyshkodzianis.xmlxsdparsing.entity.Deposit;
import by.pyshkodzianis.xmlxsdparsing.entity.MetalDeposit;
import by.pyshkodzianis.xmlxsdparsing.entity.currency.Currency;
import by.pyshkodzianis.xmlxsdparsing.entity.currency.MetalCurrency;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


public class BanksHandler extends DefaultHandler {
    public static Logger logger = LogManager.getLogger();
    private Set< Bank > banks;
    private Bank currentBank;
    private Deposit currentDeposit;
    private BanksXmlTag currentXmlTag;
    private EnumSet<BanksXmlTag> withText;
    private static final String ELEMENT_BANK = "bank";
    private static final String ELEMENT_METAL_DEPOSIT = "metal-deposit";
    private static final String ELEMENT_CURRENCY_DEPOSIT = "currency-deposit";
    public final static String DEFAULT_ATTRIBUTE_COUNTRY = "Belarus";
    public final static String HYPHEN = "-";
    public final static String UNDERSCORE = "_";

    public BanksHandler() {
        banks = new HashSet<Bank>();
        withText = EnumSet.range(BanksXmlTag.DEPOSITOR, BanksXmlTag.CURRENCY);
    }

    public Set<Bank> getBanks() {
        return banks;
    }

    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        switch (qName) {
            case ELEMENT_BANK:
                currentBank = buildBank(attrs);
                break;
            case ELEMENT_METAL_DEPOSIT:
                currentDeposit = new MetalDeposit();
                break;
            case ELEMENT_CURRENCY_DEPOSIT:
                currentDeposit = new CurrencyDeposit();
            default:
                BanksXmlTag temp = BanksXmlTag.valueOf(qName.replace(HYPHEN, UNDERSCORE).toUpperCase());
                if (withText.contains(temp)) {
                    currentXmlTag = temp;
                }
        }
    }

    public void endElement(String uri, String localName, String qName) {
        if (ELEMENT_BANK.equals(qName)) {
            banks.add(currentBank);
        } else if (ELEMENT_METAL_DEPOSIT.equals(qName) || ELEMENT_CURRENCY_DEPOSIT.equals(qName)) {
            currentBank.addDeposit(currentDeposit);
        }
    }

    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).strip();
        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case DEPOSITOR:
                    currentDeposit.setDepositor(data);
                    break;
                case DEPOSIT_ID:
                    currentDeposit.setDepositId(data);
                    break;
                case OPEN_DATE:
                    LocalDateTime openDate = LocalDateTime.parse(data);
                    currentDeposit.setOpenDate(openDate);
                    break;

                case AMOUNT:
                    Double amount = Double.parseDouble(data);
                    currentDeposit.setAmount(amount);
                    break;
                case PROFITABILITY:
                    Double profitability = Double.parseDouble(data);
                    currentDeposit.setProfitability(profitability);
                    break;
                case TIME_CONSTRAINS:
                    currentDeposit.setTimeConstraints(data);
                    break;
                case METAL_CURRENCY:
                    MetalDeposit metalDeposit = (MetalDeposit) currentDeposit;
                    MetalCurrency metalCurrency = MetalCurrency.valueOf(data);
                    metalDeposit.setMetalCurrency(metalCurrency);
                    break;
                case CURRENCY:
                    CurrencyDeposit currencyDeposit=(CurrencyDeposit) currentDeposit;
                    Currency currency = Currency.valueOf(data);
                    currencyDeposit.setCurrency(currency);
                    break;
                default:
                    logger.log(Level.ERROR, "enum constant not present");
            }
        }
        currentXmlTag = null;
    }

    private Bank buildBank(Attributes attrs) {
        Bank bank = new Bank();
        if (attrs.getLength()== 1) {
            bank.setName(attrs.getValue(0));
            bank.setCountry(DEFAULT_ATTRIBUTE_COUNTRY);
        } else {
            int numberAttribute = 0;
            while (numberAttribute < attrs.getLength()) {
                switch (attrs.getLocalName(numberAttribute)) {
                    case "country":
                        bank.setCountry(attrs.getValue(numberAttribute));
                        break;
                    case "name":
                        bank.setName(attrs.getValue(numberAttribute));
                        break;
                }
                numberAttribute++;
            }
        }
        return bank;
    }
}