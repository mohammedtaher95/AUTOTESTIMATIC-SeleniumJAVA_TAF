package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static elementActions.ElementActions.*;

public class SearchPage{

    private WebDriver driver;

    By searchField = By.id("small-searchterms");
    By searchButton = By.cssSelector("button.button-1.search-box-button");
    By productResult = By.cssSelector("div.picture");
    List<WebElement> productList = FindElements(By.id("ui-id-1"));

    public SearchPage(WebDriver driver) {
        this.driver = driver;
    }


    public SearchPage ProductSearch(String ProductName)
    {
        Fill_in(searchField, ProductName);
        clickButton(searchButton);
        return this;
    }

    public ProductDetailsPage OpenProductPage() {
        clickButton(productResult);
        return new ProductDetailsPage(driver);
    }

    public ProductDetailsPage ProductSearchUsingAutoSuggest(String SearchText)
    {
        Fill_in(searchField, SearchText);
        clickButton((By) productList.get(0));
        return new ProductDetailsPage(driver);
    }
}
