package by.pyshkodzianis.xmlxsdparsing.parser.builder;

import by.pyshkodzianis.xmlxsdparsing.entity.Bank;
import by.pyshkodzianis.xmlxsdparsing.entity.CurrencyDeposit;
import by.pyshkodzianis.xmlxsdparsing.entity.Deposit;
import by.pyshkodzianis.xmlxsdparsing.entity.MetalDeposit;
import by.pyshkodzianis.xmlxsdparsing.entity.currency.Currency;
import by.pyshkodzianis.xmlxsdparsing.entity.currency.MetalCurrency;
import by.pyshkodzianis.xmlxsdparsing.exception.BanksXmlException;
import by.pyshkodzianis.xmlxsdparsing.parser.handler.BanksXmlTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;


public class BanksStaxBuilder extends AbstractBanksBuilder {
    public static Logger logger = LogManager.getLogger();
    private XMLInputFactory inputFactory;
    public final static String ATTRIBUTE_NAME = "name";
    public final static String ATTRIBUTE_COUNTRY = "country";
    public final static String DEFAULT_ATTRIBUTE_COUNTRY = "unknown";
    public final static String HYPHEN = "-";
    public final static String UNDERSCORE = "_";

    public BanksStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    public BanksStaxBuilder(Set< Bank > banks) {
        super(banks);
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildSetBanks(String filename) throws BanksXmlException {
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream = new FileInputStream(new File(filename))) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(BanksXmlTag.BANK.getValue())) {
                        Bank bank = buildBank(reader);
                        banks.add(bank);
                    }
                }
            }
            logger.log(Level.INFO, "parsing result: " + getBanks());
        } catch (XMLStreamException | FileNotFoundException e) {
            throw new BanksXmlException("parsing error" + e);
        } catch (IOException e) {
            throw new BanksXmlException(filename + "is not found" + e);
        }
    }

    private Bank buildBank(XMLStreamReader reader) throws XMLStreamException {
        Bank bank = new Bank();
        bank.setName(reader.getAttributeValue(null, ATTRIBUTE_NAME));
        bank.setCountry(reader.getAttributeValue(null, ATTRIBUTE_COUNTRY).isEmpty() ? DEFAULT_ATTRIBUTE_COUNTRY
                : reader.getAttributeValue(null, ATTRIBUTE_COUNTRY));
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    if (name.equals(BanksXmlTag.METAL_DEPOSIT.getValue())
                            || name.equals(BanksXmlTag.CURRENCY_DEPOSIT.getValue())) {
                        Deposit deposit = buildDeposit(reader, name);
                        bank.addDeposit(deposit);
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (name.equals(BanksXmlTag.BANK.getValue())) {
                        return bank;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag <bank>");
    }

    private Deposit buildDeposit(XMLStreamReader reader, String tagName) throws XMLStreamException {
        Deposit deposit = null;
        switch (tagName) {
            case "metal-deposit":
                deposit = new MetalDeposit();
                break;
            case "currency-deposit":
                deposit = new CurrencyDeposit();
                break;
        }
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (BanksXmlTag.valueOf(name.replace(HYPHEN, UNDERSCORE).toUpperCase())) {
                        case DEPOSITOR:
                            deposit.setDepositor(getXMLText(reader));
                            break;
                        case DEPOSIT_ID:
                            deposit.setDepositId(getXMLText(reader));
                            break;
                        case OPEN_DATE:
                            LocalDateTime openDate =LocalDateTime.parse(getXMLText(reader));
                            deposit.setOpenDate(openDate);
                            break;
                        case AMOUNT:
                            Double amount = Double.parseDouble(getXMLText(reader));
                            deposit.setAmount(amount);
                            break;
                        case PROFITABILITY:
                            Double profitability = Double.parseDouble(getXMLText(reader));
                            deposit.setProfitability(profitability);
                            break;
                        case TIME_CONSTRAINS:
                            deposit.setTimeConstraints(getXMLText(reader));

                        case METAL_CURRENCY:
                            MetalDeposit metalDeposit = (MetalDeposit) deposit;
                            MetalCurrency metalCurrency = MetalCurrency.valueOf(getXMLText(reader));
                            metalDeposit.setMetalCurrency(metalCurrency);
                            break;
                        case CURRENCY:
                            CurrencyDeposit currencyDeposit=(CurrencyDeposit) deposit;
                            Currency currency = Currency.valueOf(getXMLText(reader));
                            currencyDeposit.setCurrency(currency);
                            break;
                    }

                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (name.equals(BanksXmlTag.METAL_DEPOSIT.getValue())
                            || name.equals(BanksXmlTag.CURRENCY_DEPOSIT.getValue())) {
                        return deposit;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag " + tagName);
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}