package pages.nopcommerce;

import driverfactory.webdriver.WebDriver;
import org.openqa.selenium.By;

public class ProductDetailsPage{

    private final WebDriver driver;

    By productNameBreadCrumb = By.cssSelector("strong.current-item");
    By emailButton = By.cssSelector("button.button-2.email-a-friend-button");
    By productPriceLabel = By.id("price-value-4");
    By reviewHyperlink = By.xpath("//a[@href=\"/productreviews/4\"][2]");

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public ProductDetailsPage checkThatProductPageShouldBeDisplayed(String productName){
        driver.assertThat().element(productNameBreadCrumb).text().isEqualTo(productName);
        return this;
    }

    public EmailFriendPage emailFriend()
    {
        driver.element().click(emailButton);
        return new EmailFriendPage(driver);
    }


    public ProductReviewPage addReview()
    {
        driver.element().click(reviewHyperlink);
        return new ProductReviewPage(driver);
    }

    public ProductDetailsPage checkCurrency(String currency)
    {
        driver.assertThat().element(productPriceLabel).text().contains(currency);
        return this;
    }


}
