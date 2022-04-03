package pyshkodzianis.xmlxsdparsing;

import by.pyshkodzianis.xmlxsdparsing.entity.Bank;
import by.pyshkodzianis.xmlxsdparsing.exception.BanksXmlException;
import by.pyshkodzianis.xmlxsdparsing.parser.builder.BanksDomBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Set;

public class BanksDomBuilderTest {
    private static Logger logger = LogManager.getLogger();
    private BanksDomBuilder domBuilder;
    private final String PATH_TO_XML_FILE = "./src/test/resources/TestBanks.xml";
    private final String WRONG_PATH_TO_XML_FILE = "./src/test/resources/TestBank.xml";

    @BeforeTest
    public void before(){
        logger.info("Beginning BanksDomBuilder test");
        domBuilder = new BanksDomBuilder();
    }


    @Test(expectedExceptions = BanksXmlException.class)
    public void testBuildSetBanksException() throws BanksXmlException{
        domBuilder.buildSetBanks(WRONG_PATH_TO_XML_FILE);
    }

    @Test
    public void testIsNullInBank() throws BanksXmlException{
        boolean isNull = false;
        try {
            Set< Bank > banks = domBuilder.getBanks();
            for (Bank b : banks){
                b.hashCode();
            }
        } catch (NullPointerException e){
            isNull = true;
            throw new BanksXmlException("Filed in Bank can`t be initialize by null " + e.getMessage());
        }
        Assert.assertFalse(isNull);
    }

    @AfterTest
    public void after(){
        domBuilder = null;
        logger.info("Finish BanksDomBuilder testing");
    }
}