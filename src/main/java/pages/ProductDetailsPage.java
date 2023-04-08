package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static elementactions.ElementActions.*;

public class ProductDetailsPage{

    private WebDriver driver;

    By productNameBreadCrumb = By.cssSelector("strong.current-item");
    By emailButton = By.cssSelector("button.button-2.email-a-friend-button");
    By productPriceLabel = By.id("price-value-4");
    By reviewHyperlink = By.xpath("//a[@href=\"/productreviews/4\"][2]");

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public ProductDetailsPage checkThatProductPageShouldBeDisplayed(String productName){
        Assert.assertTrue(getElementText(productNameBreadCrumb).equalsIgnoreCase(productName));
        return this;
    }

    public EmailFriendPage emailFriend()
    {
        clickButton(emailButton);
        return new EmailFriendPage(driver);
    }


    public ProductReviewPage addReview()
    {
        clickButton(reviewHyperlink);
        return new ProductReviewPage(driver);
    }

    public ProductDetailsPage checkCurrency(String currency)
    {
        Assert.assertTrue(getElementText(productPriceLabel).contains(currency));
        return this;
    }


}
