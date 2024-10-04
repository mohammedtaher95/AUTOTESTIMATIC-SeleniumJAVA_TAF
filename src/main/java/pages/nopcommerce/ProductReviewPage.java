package pages.nopcommerce;

import driverfactory.webdriver.WebDriver;
import org.openqa.selenium.By;

public class ProductReviewPage {

    private final WebDriver driver;

    By reviewTitleField = By.id("AddProductReview_Title");
    By reviewTextField = By.id("AddProductReview_ReviewText");
    By ratingRadioBtn = By.id("addproductrating_4");
    By submitBtn = By.cssSelector("button.button-1.write-product-review-button");
    By successMessage = By.cssSelector("div.result");
    By addedReviewTitle = By.cssSelector("div.review-title");

    public ProductReviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public ProductReviewPage fillReviewForm(String reviewTitle, String reviewText) {
        driver.element().fillField(reviewTitleField, reviewTitle);
        driver.element().fillField(reviewTextField, reviewText);
        driver.element().click(ratingRadioBtn);
        return this;
    }

    public ProductReviewPage clickOnSubmitButton() {
        driver.element().click(submitBtn);
        return this;
    }

    public ProductReviewPage verifyThatReviewShouldBeSubmittedSuccessfully(String success,
                                                                           String userMsg) {
        driver.assertThat().element(successMessage).text().isEqualTo(success);
        driver.assertThat().element(addedReviewTitle).text().isEqualTo(userMsg);
        return this;
    }
}
