package by.pyshkodzianis.xmlxsdparsing.parser.builder;

import by.pyshkodzianis.xmlxsdparsing.entity.Bank;
import by.pyshkodzianis.xmlxsdparsing.exception.BanksXmlException;
import by.pyshkodzianis.xmlxsdparsing.parser.handler.BankErrorHandler;
import by.pyshkodzianis.xmlxsdparsing.parser.handler.BanksHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Set;

public class BanksSaxBuilder extends AbstractBanksBuilder {
    public static Logger logger = LogManager.getLogger();
    private BanksHandler handler = new BanksHandler();
    private XMLReader reader;

    public BanksSaxBuilder() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
        } catch (ParserConfigurationException | SAXException e) {
            logger.log(Level.ERROR, " parser configuration error");
        }
        reader.setErrorHandler(new BankErrorHandler());
        reader.setContentHandler(handler);
    }

    public BanksSaxBuilder(Set< Bank > banks) {
        super(banks);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
        } catch (ParserConfigurationException | SAXException e) {
            logger.log(Level.ERROR, " parser configuration error");
        }
        reader.setErrorHandler(new BankErrorHandler());
        reader.setContentHandler(handler);
    }

    @Override
    public void buildSetBanks(String filename) throws BanksXmlException {
        try {
            reader.parse(filename);
        } catch (IOException | SAXException e) {
            throw new BanksXmlException("parsing error" + e);
        }
        banks = handler.getBanks();
        logger.log(Level.INFO, "parsing result: " + getBanks());
    }

}