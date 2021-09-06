import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class ParserProductEmail {

    public static void main(String[] args) throws InterruptedException, IOException {
        System.setProperty("webdriver.chrome.driver", "D:/IdeaProjects/chromedriver.exe");

        ParserProductEmail parserProductEmail = new ParserProductEmail();

        List<String> links = parserProductEmail.parseLinks();

        parserProductEmail.parseEmail(links);
    }

    void parseEmail(List<String> links) throws FileNotFoundException {
        File file = new File("emails.txt");
        PrintWriter printWriter = new PrintWriter(file);

        ChromeDriver driver = new ChromeDriver();

        for (String s: links) {
            driver.get(s);
            driver.findElement(By.className("contacts")).click();
            WebElement element = driver.findElement(By.className("contacts_tabs_btns"));
            WebElement el = driver.findElement(with(By.tagName("span")).above(element));
            printWriter.println(el.getText());
        }
        printWriter.close();
        driver.quit();
    }

    List<String> parseLinks() throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://productcenter.ru/producers/catalog-produkty-pitaniia-45");

        driver.findElement(By.xpath("//*[@id=\"center\"]/div[1]/div/span[3]")).click(); ////*[@id="center"]/div[4]/div/a[7]

        List<String> links = new ArrayList<>();

        while (driver.findElement(By.className("show_more_btn")).isDisplayed()){
            driver.findElement(By.className("show_more_btn")).click();
            Thread.sleep(1000);
        }

        List<WebElement> classItemImages = driver.findElements(By.className("item_images"));
        for (WebElement e : classItemImages){
            links.add(e.findElement(By.className("img_list")).findElement(By.tagName("a")).getAttribute("href"));
        }
        driver.quit();
        /*System.out.println(links.size());
        for (String s : links){
            System.out.println(s);
        }*/
        return links;
    }
}
