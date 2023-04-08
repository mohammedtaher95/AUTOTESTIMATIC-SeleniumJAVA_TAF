package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static elementactions.ElementActions.*;

public class ProductReviewPage{

    private WebDriver driver;

    By reviewTitleField = By.id("AddProductReview_Title");
    By reviewTextField = By.id("AddProductReview_ReviewText");
    By ratingRadioBtn = By.id("addproductrating_4");
    By submitBtn = By.cssSelector("button.button-1.write-product-review-button");
    By successMessage = By.cssSelector("div.result");
    By addedReviewTitle = By.cssSelector("div.review-title");

    public ProductReviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public ProductReviewPage fillReviewForm(String reviewTitle, String reviewText)
    {
        fillField(reviewTitleField, reviewTitle);
        fillField(reviewTextField, reviewText);
        clickButton(ratingRadioBtn);
        return this;
    }

    public ProductReviewPage clickOnSubmitButton()
    {
        clickButton(submitBtn);
        return this;
    }

    public ProductReviewPage verifyThatReviewShouldBeSubmittedSuccessfully(String success, String userMsg)
    {
        Assert.assertTrue(getElementText(successMessage).equalsIgnoreCase(success));
        Assert.assertEquals(userMsg, getElementText(addedReviewTitle));
        return this;
    }
}
