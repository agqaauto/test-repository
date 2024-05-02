import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:/Users/a.nurmukhamed/Documents/GitHub/test-repository/JavaAppiumAutomation/apks/org.wikipedia.apk");

        //driver = new AndroidDriver(new URL("http://192.168.100.30:4723/wd/hub"), capabilities);
        driver = new AndroidDriver(new URL("http://192.168.1.101:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void compareElementWithText() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "Cannot contains 'Search…'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Wiki",
                "Cannot find Search input",
                5
        );
        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Wiki",
                "We see unexpected value on search input ",
                15
        );
    }

    @Test
    public void testCancelSearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Wiki",
                "Cannot find Search input",
                5
        );

        WebElement results = waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot present any results of search",
                15
        );

        List<WebElement> resultElements = results.findElements(By.id("org.wikipedia:id/page_list_item_container"));

        Assert.assertNotNull(resultElements);
        Assert.assertTrue("Elements less than 2", resultElements.size() > 1);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        boolean resultsNotPresent = waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Result are not present on the page",
                15
        );

        Assert.assertTrue(resultsNotPresent);
    }

    @Test
    public void testCheckWordsInSearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find Search input",
                5
        );

        WebElement results = waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot present any results of search",
                15
        );

        List<WebElement> resultElements = results.findElements(By.id("org.wikipedia:id/page_list_item_title"));

        for (WebElement element : resultElements) {
            String element_text = element.getAttribute("text");
            Assert.assertTrue("Not contains expected text", element_text.toUpperCase().contains("JAVA"));
        }
    }

    @Test
    public void savingTwoArticles() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Test is failed because onboarding page is not skipped",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        String search_line = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                search_line,
                "Cannot find Search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id = 'org.wikipedia:id/page_list_item_description'][@text='Object-oriented programming language']"),
                "Cannot find needed article in search results",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot click to save button from menu 1",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.Button[@text='Add to list']"),
                "Cannot click to save button from menu 2",
                5
        );

        String name_of_folder = "Learning Programming";
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Name of this list']"),
                name_of_folder,
                "Cannot enter name of Test",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@text = 'OK']"),
                "Cannot press OK",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot navigate back on the Article",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='Java']"),
                "Cannot find needed article in search results",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot click to save button from menu 3",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.Button[@text='Add to list']"),
                "Cannot click to save button from menu 4",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
                "Cannot click to save button from menu 5",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.Button[@text='View list']"),
                "Cannot click to save button from menu 6",
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Caanot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5
        );

        String result_of_articles = "org.wikipedia:id/page_list_item_container";
        int element_count = getAmountOfElements(By.id(result_of_articles));

        Assert.assertTrue("Result of articles is not equals 1", element_count == 1);

        waitForElementAndClick(
                By.xpath("//*[@text = '" + search_line + "']"),
                "Cannot open the second article",
                15
        );

        String title_article = waitForElementAndGetAttribute(
                By.xpath("//*[@text='" + search_line + "']"),
                "text",
                "Title of article is not present",
                15
        );

        Assert.assertEquals("Articles not the same", search_line, title_article);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private void assertElementHasText(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        String textOfSearch = element.getAttribute("text");

        Assert.assertEquals(error_message, value, textOfSearch);
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }
}