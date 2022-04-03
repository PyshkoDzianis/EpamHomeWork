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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;


public class BanksDomBuilder extends AbstractBanksBuilder {
    public static Logger logger = LogManager.getLogger();
    private DocumentBuilder docBuilder;
    public final static String ATTRIBUTE_NAME = "name";
    public final static String ATTRIBUTE_COUNTRY = "country";
    public final static String DEFAULT_ATTRIBUTE_COUNTRY = "Belarus";

    public BanksDomBuilder() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, " parser configuration error");
        }
    }

    public BanksDomBuilder(Set<Bank> banks) {
        super(banks);
    }

    @Override
    public void buildSetBanks(String filename) throws BanksXmlException {
        Document doc;
        try {
            doc = docBuilder.parse(filename);
            Element root = doc.getDocumentElement();
            NodeList banksList = root.getElementsByTagName(BanksXmlTag.BANK.getValue());
            for (int i = 0; i < banksList.getLength(); i++) {
                Element bankElement = (Element) banksList.item(i);
                Bank bank = buildBank(bankElement);
                banks.add(bank);
            }
            logger.log(Level.INFO, "parsing result: " + getBanks());
        } catch (IOException | SAXException e) {
            throw new BanksXmlException("parsing error" + e);
        }
    }

    private Bank buildBank(Element bankElement) {
        Bank bank = new Bank();
        bank.setName(bankElement.getAttribute(ATTRIBUTE_NAME));
        bank.setCountry(bankElement.getAttribute(ATTRIBUTE_COUNTRY).isEmpty() ? DEFAULT_ATTRIBUTE_COUNTRY
                : bankElement.getAttribute(ATTRIBUTE_COUNTRY));

        NodeList metalDepositsList = bankElement.getElementsByTagName(BanksXmlTag. METAL_DEPOSIT.getValue());
        for (int i = 0; i < metalDepositsList.getLength(); i++) {
            Element metalDepositElement = (Element) metalDepositsList.item(i);
            Deposit metalDeposit = buildDeposit(metalDepositElement, BanksXmlTag. METAL_DEPOSIT.getValue());
            bank.addDeposit(metalDeposit);
        }

        NodeList currencyDepositList = bankElement.getElementsByTagName(BanksXmlTag. CURRENCY_DEPOSIT.getValue());
        for (int i = 0; i < currencyDepositList.getLength(); i++) {
            Element currencyDepositElement = (Element) currencyDepositList.item(i);
            Deposit currencyDeposit = buildDeposit(currencyDepositElement,
                    BanksXmlTag. CURRENCY_DEPOSIT.getValue());
            bank.addDeposit(currencyDeposit);
        }
        return bank;
    }

    private Deposit buildDeposit(Element depositElement, String tagName) {
        Deposit deposit = null;
        switch (tagName) {
            case "metal-deposit":
                deposit = new MetalDeposit();
                break;
            case "currency-deposit":
                deposit = new CurrencyDeposit();
                break;
        }
        deposit.setDepositor(getElementTextContent(depositElement, BanksXmlTag.DEPOSITOR.getValue()));
        deposit.setDepositId(getElementTextContent(depositElement, BanksXmlTag.DEPOSIT_ID.getValue()));
        LocalDateTime openDate = LocalDateTime
                .parse(getElementTextContent(depositElement, BanksXmlTag.OPEN_DATE.getValue()));
        deposit.setOpenDate(openDate);
        deposit.setTimeConstraints(getElementTextContent(depositElement, BanksXmlTag.TIME_CONSTRAINS.getValue()));
        Double amount = Double.parseDouble(getElementTextContent(depositElement, BanksXmlTag.AMOUNT.getValue()));
        deposit.setAmount(amount);
        Double profitability = Double
                .parseDouble(getElementTextContent(depositElement, BanksXmlTag.PROFITABILITY.getValue()));
        deposit.setProfitability(profitability);


        if (deposit instanceof MetalDeposit) {
            MetalDeposit metalDeposit = (MetalDeposit) deposit;
           ((MetalDeposit) deposit).setMetalCurrency(MetalCurrency.valueOf(getElementTextContent(depositElement
           ,BanksXmlTag.METAL_CURRENCY.getValue())));
        }
        if (deposit instanceof CurrencyDeposit){
          CurrencyDeposit currencyDeposit=(CurrencyDeposit) deposit;
           ((CurrencyDeposit) deposit).setCurrency(Currency.valueOf(getElementTextContent(depositElement
                   , BanksXmlTag.CURRENCY.getValue())));


                }

                return deposit;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }
}