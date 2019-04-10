package ru.org.autotest;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.org.autotest.config.WrikeTestProperties;
import ru.org.autotest.utils.WrikeUtils;
import java.util.concurrent.TimeUnit;
import static junit.framework.TestCase.assertTrue;


public class WrikeTest {

    private static final String URL_WRIKE_MAIN = "http://www.wrike.com";
    private static final String URL_TWITTER_WRIKE = "https://twitter.com/wrike";
    private static final By GET_STARTED_BTN = By.xpath("//div[@class='wg-header__desktop']//button[@type='submit']");
    private static final By EMAIL_TEXT = By.xpath("//label[@class='modal-form-trial__label']/input");
    private static final String MASK_FOR_EMAIL = "@wriketask.qaa";
    private static final By CREATE_ACCOUNT_BTN = By.xpath("//label[@class='modal-form-trial__label']/button");
    private static final By ERROR_MESSAGE = By.xpath("//*[@id='modal-pro']/form/label[1]/span[contains(@class,'error--visible')]");
    private WebDriver driver;
    private static final By BTN1_QUESTION1 = By.xpath("//div[@data-code='interest_in_solution']/label[1]/button");
    private static final By BTN2_QUESTION1 = By.xpath("//div[@data-code='interest_in_solution']/label[2]/button");
    private static final By BTN1_QUESTION2 = By.xpath("//div[@data-code='team_members']/label[1]/button");
    private static final By BTN2_QUESTION2 = By.xpath("//div[@data-code='team_members']/label[2]/button");
    private static final By BTN3_QUESTION2 = By.xpath("//div[@data-code='team_members']/label[3]/button");
    private static final By BTN4_QUESTION2 = By.xpath("//div[@data-code='team_members']/label[4]/button");
    private static final By BTN5_QUESTION2 = By.xpath("//div[@data-code='team_members']/label[5]/button");
    private static final By BTN1_QUESTION3 = By.xpath("//div[@data-code='primary_business']/label[1]/button");
    private static final By BTN2_QUESTION3 = By.xpath("//div[@data-code='primary_business']/label[2]/button");
    private static final By BTN3_QUESTION3 = By.xpath("//div[@data-code='primary_business']/label[3]/button");
    private static final By TXT_QUESTION3 = By.xpath("//div[@data-code='primary_business']/label[3]//span/input");
    private static final By ANSWERS_SUBMIT_BTN = By.xpath("//form[@class='survey-form']/button");
    private static final By CHECK_SUBMISSION = By.xpath("//form[@class='survey-form']/button[contains(@class,'loading')]");
    private static final By RESEND_EMAIL_BTN = By.xpath("//div[@class='wg-grid']//p[3]/button");
    private static final By CHECK_RESENDING_EMAIL = By.xpath("//div[@class='wg-grid']//p[3]/button[contains(@class,'loading')]");
    private static final By ELEMENT_OF_TWITTER_URL = By.xpath("//div[contains(@class,'social')]//li[1]/a");
    private static final String ELEMENT_ATTRIBUTE_OF_TWITTER_URL = "href";

    @BeforeClass
    public static void initProperties() {
        System.setProperty("webdriver.chrome.driver", WrikeTestProperties.CHROME_DRIVER_PATH);
    }

    @Before
    public void init() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void close() {
        driver.quit();
    }

    @Test
    public void testWrike() {
        // open link wrike.com
        driver.get(URL_WRIKE_MAIN);

        // find and click button "get started for free"
        driver.findElement(GET_STARTED_BTN).click();

        // check enter email
        driver.findElement(EMAIL_TEXT).sendKeys(WrikeUtils.generateValidEmailName()+MASK_FOR_EMAIL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(CREATE_ACCOUNT_BTN).click();

        // check moving to the next page
        boolean nextPage;
        try {
            driver.findElement(ERROR_MESSAGE);
            nextPage = false;
        } catch (NoSuchElementException e) {
            nextPage = true;
        }
        assertTrue("Check your email", nextPage);

        // generate random answers
        int rndAnswersItem1 = WrikeUtils.getRandomAnswer(1, 2);
        switch (rndAnswersItem1) {
            case (1):
                driver.findElement(BTN1_QUESTION1).click();
                break;
            case (2):
                driver.findElement(BTN2_QUESTION1).click();
                break;
        }
        int rndAnswersItem2 = WrikeUtils.getRandomAnswer(1, 5);
        switch (rndAnswersItem2) {
            case (1):
                driver.findElement(BTN1_QUESTION2).click();
                break;
            case (2):
                driver.findElement(BTN2_QUESTION2).click();
                break;
            case (3):
                driver.findElement(BTN3_QUESTION2).click();
                break;
            case (4):
                driver.findElement(BTN4_QUESTION2).click();
                break;
            case (5):
                driver.findElement(BTN5_QUESTION2).click();
                break;
        }
        int rndAnswersItem3 = WrikeUtils.getRandomAnswer(1, 3);
        switch (rndAnswersItem3) {
            case (1):
                driver.findElement(BTN1_QUESTION3).click();
                break;
            case (2):
                driver.findElement(BTN2_QUESTION3).click();
                break;
            case (3):
                driver.findElement(BTN3_QUESTION3).click();
                String answer = WrikeUtils.generateAnswerText();
                driver.findElement(TXT_QUESTION3).sendKeys(answer);
                break;
        }
        driver.findElement(ANSWERS_SUBMIT_BTN).click();

        // check submitting results
        boolean answersSubmit;
        try {
            driver.findElement(CHECK_SUBMISSION);
            answersSubmit = true;
        } catch (NoSuchElementException e) {
            answersSubmit = false;
        }
        assertTrue("Select one of the options", answersSubmit);

        // click "Resend email" button
        driver.findElement(RESEND_EMAIL_BTN).click();

        // check resending email
        boolean resendEmail;
        try {
            driver.findElement(CHECK_RESENDING_EMAIL);
            resendEmail = true;
        } catch (NoSuchElementException e) {
            resendEmail = false;
        }
        assertTrue("Resending email is not valid", resendEmail);

        //check twitter URL
        String twitterURL = driver.findElement(ELEMENT_OF_TWITTER_URL).getAttribute(ELEMENT_ATTRIBUTE_OF_TWITTER_URL);
        boolean checkTwitterURL = twitterURL.equals(URL_TWITTER_WRIKE);
        assertTrue("Invalid link", checkTwitterURL);

    }
}

