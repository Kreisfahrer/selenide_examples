package org.selenide.examples.gmail;

import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.DOM.$;
import static com.codeborne.selenide.DOM.waitUntil;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.Keys.ENTER;

public class NewMailSpec extends GmailTests {
  @Test
  public void userCanComposeEmail() {
    $(byText("COMPOSE")).click();
    $(By.name("to")).type("andrei.solntsev@gmail.com");
    $(By.name("subject")).type("Agile Saturday test!");

    getWebDriver().switchTo().frame(getWebDriver().findElement(By.className("editable")));
//    getWebDriver().switchTo().activeElement();

    $("body").type("Hello braza!");
    $("body").sendKeys(ENTER);

    getWebDriver().switchTo().defaultContent();
    $(byText("Send")).click();

    $(byText("Your message has been sent"));
    $(byText("Undo")).click();
    $(byText("Sending has been undone.")).should(appear);

    $(".editable").should(appear);
    getWebDriver().switchTo().frame(getWebDriver().findElement(By.className("editable")));
    $("body").sendKeys("This is Agile Saturday");
    $("body").sendKeys(ENTER);

    getWebDriver().switchTo().defaultContent();
    $(byText("Send")).click();
    $(withText("Your message has been sent.")).should(appear);
    waitUntil(byText("Undo"), disappears, 10000);
  }

  @Test
  public void userCanSeeSentEmails() {
    $(byText("Sent Mail")).click();
    $(byText("Agile Saturday test!")).shouldBe(visible);
  }
}