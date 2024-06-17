package test;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KupujemProdajemTests {
    
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("https://www.kupujemprodajem.com/");
    }

     @After
    public void afterTest() {
        // Прављење скриншота након извршеног теста
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
            String screenshotName = "screenshot_" + timestamp + ".png";
            FileUtils.copyFile(screenshot, new File("screenshots/" + screenshotName));
            System.out.println("Screenshot saved: " + screenshotName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void scenario1() throws InterruptedException {

        // Проналазимо дугме "Претражите детаљно" 
        WebElement pretraziteDetaljnoButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".Button_base__G3HTK.Button_big__vkHxv.ButtonAsLink_asLink__zkDGq.ButtonAsLink_isUnderline__bP9eS.ButtonAsLink_primary__falCH.InputSearch_buttonInner__7MjOU")));
        pretraziteDetaljnoButton.click();


        // Проналазимо "Категорију"
        WebElement categoryButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-categoryId-input")));
        categoryButton.click();

        // Опција "Odeća | Ženska"
        WebElement odevnaZenskaOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Odeća | Ženska']")));
        odevnaZenskaOption.click();
 
        // Одабир "Bluze"
        WebElement bluzeOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Bluze']")));
        bluzeOption.click();

        // Са ценом већом од 100
        WebElement priceFromInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("priceFrom")));
        priceFromInput.sendKeys("100");

        // Мени за валуту
        WebElement currencyButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-currency-input")));
        currencyButton.click();

        // Одабир "din"
        WebElement dinOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='din']")));
        dinOption.click();

        // Само са ценом
        WebElement hasPriceYesCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/div/div/div[2]/form/section/div/div[2]/div/div/div[1]/div[2]/span/div[2]/div[1]/span/label")));
        hasPriceYesCheckbox.click();

        // Мени за стање предмента
        WebElement conditionDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-condition-input")));
        conditionDropdown.click();

        // Одабир "Novo"
        conditionDropdown.sendKeys("Novo");
        conditionDropdown.sendKeys(Keys.ENTER);

        // Мени за стање предмента
        conditionDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-condition-input")));
        conditionDropdown.click();

        // Одабир "Kao novo (ne korišćeno)"
        conditionDropdown.sendKeys("Kao novo");
        conditionDropdown.sendKeys(Keys.ENTER);

        // Клик на дугме за претрагу
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".Button_base__G3HTK.Button_big__vkHxv.ButtonPrimaryBlue_primaryBlue__OjE4b")));
        searchButton.click();

        // Добијање резултата претраге
        Thread.sleep(10000);
        WebElement resultsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Grid_col-lg-16__SxfW0.Grid_col-xs__w58_v.Grid_col-sm__DsLxt.Grid_col-md__eg0dB.BreadcrumbHolder_breadcrumb__5NZIE")));
        Thread.sleep(2000);

        // Текстуални садржај елемента
        String resultsText = resultsElement.getText();

        // Извлачење броја резултата
        int numberOfResults = Integer.parseInt(resultsText.replaceAll("[^\\d]", ""));

        // Провера да ли је број резултата већи од 1000
        assertTrue("Očekivano više od 1000 rezultata, ali dobijeno: " + numberOfResults, numberOfResults > 1000);
    }

    @Test
    public void scenario2() throws InterruptedException {

        // Улази у најновије огласе
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".Link_link__2iGTE.Button_base__G3HTK.Button_inherit__XmtDg.Button_noPadding__Y_4U2.CategoryHeadlineItem_itemOutter__d_9_u.CategoryHeadlineItem_firstItem__4efxZ")));
        button.click();

        Thread.sleep(5000);

        // Проналази листу елемената која представља огласе
        WebElement adsList = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("AdItem_adOuterHolder__lACeh")));

        // Проналази први оглас у оквиру листе
        WebElement firstAd = adsList.findElement(By.tagName("a")); // Pretpostavljam da je link (anchor tag) prvi element unutar AdItem_adOuterHolder__lACeh
        firstAd.click();

        // Проналази дугме "Додај у адресар"
        WebElement button1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Dodaj u adresar']")));
        button1.click();
        
        Thread.sleep(5000);

         // Провера да ли је форма за логин видљива на страници
         WebElement loginForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("kp-portal")));
         assertTrue(loginForm.isDisplayed());

         Thread.sleep(5000);


    }


}