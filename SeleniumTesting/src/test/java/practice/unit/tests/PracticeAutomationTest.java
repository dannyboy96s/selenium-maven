package practice.unit.tests;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;


import java.util.List;

public class PracticeAutomationTest {


    /**
     *
     * 
     * Go to Webstaurant site, search for 'stainless work table',
     * make sure all the product titles contains the keyword 'Table',
     * then select the last item and add it to the cart,
     * then finally remove it for the cart.
     *
     * @Exception Interrupted Exception
     *
     */
    @Test
    public void practiceTest() throws InterruptedException {

        // instantiate driver object as chrome driver
        WebDriver driver = new ChromeDriver();

        // navigate to base url
        final String url = "https://www.webstaurantstore.com/";
        driver.navigate().to(url);

        // target search bar element and send key aka type 'stainless work table'
        final String textToSearch = "stainless work table";
        driver.findElement(By.id("searchval")).sendKeys(textToSearch);

        // click the search button
        final String xpathSearchBtn = "//button[@value='Search']";
        driver.findElement(By.xpath(xpathSearchBtn)).click();

        // tricky.... there is a space in the class name (not sure that is intentional or not)
        // find all elements with class name of 'ag-item gtm-product ' and store them in a list
        // targeting all 'productBox' elements whose parent is 'product_listing'
        final String xpathProductBox = "//div[@class='ag-item gtm-product ']";
        List<WebElement> productBoxList = driver.findElements(By.xpath(xpathProductBox));

        final String textToMatch = "Table";

        for(int i = 0; i < productBoxList.size(); ++i) {
            // for each element, grab the description
            WebElement productBoxDesc = productBoxList.get(i).findElement(By.className("description"));

            // assert - check if all product box titles contain 'Title' keyword
            assertThat("Error message: does not contains keyword 'Table' ",productBoxDesc.getText(), containsString(textToMatch));

            if(productBoxDesc.getText().contains(textToMatch)) {
                System.out.println("Description contains 'Table'");
                System.out.println(i + "  desc: "+ productBoxDesc.getText());
            } else {
                System.out.println(i + "  Does not contain Title. Descriptions: " + productBoxDesc.getText());
            }

            // get the last element and add it to the cart
            if(i == productBoxList.size()-1) {
                final String xpathAddToCart = "//input[@class='btn btn-cart btn-small']";
                productBoxList.get(i).findElement(By.xpath(xpathAddToCart)).click();
            }
        }

        // switch iframe since model pops up after you add an item to cart - click 'View Cart' button
        //final String xpathModalPopup = "//button[text()='View Cart']";
        //driver.switchTo().activeElement().findElement(By.xpath(xpathModalPopup)).click();


        // sleep for 6 seconds, wait for modal pop up animation to disappear (im assuming the animation lasts 5 seconds)
        // then click the main cart button
        Thread.sleep(6000);
        final String xpathCartBtn = "//a[@class='menu-btn']";
        driver.findElement(By.xpath(xpathCartBtn)).click();

        // remove recently added item from cart
        final String xpathRemoveItem = "//a[@class='deleteCartItemButton close']";
        driver.findElement(By.xpath(xpathRemoveItem)).click();

        // close and quit the driver
        driver.close();
        driver.quit();

    }
}
