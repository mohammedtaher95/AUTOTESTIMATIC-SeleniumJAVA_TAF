package pages;

import driverfactory.Webdriver;
import org.openqa.selenium.By;

public class SearchPage{

    private final Webdriver driver;

    By searchField = By.id("small-searchterms");
    By searchButton = By.cssSelector("button.button-1.search-box-button");
    By productResult = By.cssSelector("div.picture");
    By productList = By.id("ui-id-1");

    public SearchPage(Webdriver driver) {
        this.driver = driver;
    }


    public SearchPage productSearch(String productName)
    {
        driver.element().fillField(searchField, productName);
        driver.element().click(searchButton);
        return this;
    }

    public ProductDetailsPage openProductPage() {
        driver.element().click(productResult);
        return new ProductDetailsPage(driver);
    }

    public ProductDetailsPage productSearchUsingAutoSuggest(String searchText)
    {
        driver.element().fillField(searchField, searchText);
        driver.element().findElements(productList).get(0).click();
        return new ProductDetailsPage(driver);
    }
}
