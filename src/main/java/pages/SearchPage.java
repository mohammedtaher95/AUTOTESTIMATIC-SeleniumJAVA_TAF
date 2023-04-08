package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static elementactions.ElementActions.*;

public class SearchPage{

    private WebDriver driver;

    By searchField = By.id("small-searchterms");
    By searchButton = By.cssSelector("button.button-1.search-box-button");
    By productResult = By.cssSelector("div.picture");
    List<WebElement> productList = findElements(By.id("ui-id-1"));

    public SearchPage(WebDriver driver) {
        this.driver = driver;
    }


    public SearchPage productSearch(String productName)
    {
        fillField(searchField, productName);
        clickButton(searchButton);
        return this;
    }

    public ProductDetailsPage openProductPage() {
        clickButton(productResult);
        return new ProductDetailsPage(driver);
    }

    public ProductDetailsPage productSearchUsingAutoSuggest(String searchText)
    {
        fillField(searchField, searchText);
        clickButton((By) productList.get(0));
        return new ProductDetailsPage(driver);
    }
}
