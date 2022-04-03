package pyshkodzianis.xmlxsdparsing;



  import by.pyshkodzianis.xmlxsdparsing.ValidatorXml.ValidatorXml;
  import by.pyshkodzianis.xmlxsdparsing.exception.BanksXmlException;
  import org.apache.logging.log4j.LogManager;
  import org.apache.logging.log4j.Logger;
  import org.testng.Assert;
  import org.testng.annotations.AfterTest;
  import org.testng.annotations.BeforeTest;
  import org.testng.annotations.Test;


public class ValidatorXmlTest {
    private ValidatorXml validator;
    private static Logger logger = LogManager.getLogger();
    private final String VALID_XML = "./src/test/resources/TestBanks.xml";
    private final String EMPTY_XML = "./src/test/resources/EmptyTestBanks.xml";
    private final String XML_WRONG_PATH = "./src/test/resources/EmptyTestBank.xml";
    private final String WRONG_XML = "./src/test/resources/WrongTestBanks.xml";
    private final String XML_SCHEMA = "./src/test/resources/TestBanks.xsd";

    @BeforeTest
    public void before(){
        validator = new ValidatorXml();
        logger.trace("Start validator testing");
    }

    @Test
    public void testIsValid() throws BanksXmlException {
        boolean isValid = validator.isValid(VALID_XML, XML_SCHEMA);
        Assert.assertTrue(isValid);
    }

    @Test
    public void testIsNotValid() throws BanksXmlException{
        boolean isValid = validator.isValid(WRONG_XML, XML_SCHEMA);
        Assert.assertFalse(isValid);
    }

    @Test (expectedExceptions = BanksXmlException.class)
    public void testIsValidException() throws BanksXmlException{
        validator.isValid(XML_WRONG_PATH, XML_SCHEMA);
    }

    @Test
    public void testIsValidEmpty() throws BanksXmlException{
        boolean isValid = validator.isValid(EMPTY_XML, XML_SCHEMA);
        Assert.assertFalse(isValid);
    }


    @AfterTest
    public void after(){
        validator = null;
        logger.trace("Finish validator testing");
    }
}




























