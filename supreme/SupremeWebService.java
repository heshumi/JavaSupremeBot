package supreme;

import com.google.common.annotations.Beta;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import user.UserPreferences;
import user.UserProfile;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;

public class SupremeWebService {
    private UserPreferences userPrefs;
    private UserProfile userProfile;

    public SupremeWebService(UserProfile userProfile, UserPreferences userPrefs) {
        this.userPrefs = userPrefs;
        this.userProfile = userProfile;
    }

    public void run() {
        System.setProperty("webdriver.chrome.driver", userPrefs.getChromeDriverPath());//"/home/denis/Documents/IdeaProjects/SupremeDesktop/chromedriver"
        ChromeOptions options = new ChromeOptions();

        String os = System.getProperty("os.name");
        String userHome = System.getProperty("user.home");
        System.out.println(userHome);
        System.out.println(os);
        String path;
        if (os.startsWith("Windows"))
            path = userHome + "\\AppData\\Local\\Google\\Chrome\\User Data\\";
        else if (os.startsWith("Linux"))
            path = userHome + "/.config/google-chrome/";
        else if (os.startsWith("Mac"))
            path = userHome + "/Library/Application Support/Google/Chrome/";
        else path = "wrong path name";
        System.out.println("Path: " + path);

        options.addArguments("user-data-dir=" + path); // /home/denis/Documents/IdeaProjects/SupremeDesktop/ works
        //https://www.supremenewyork.com/shop/all/tops_sweaters
        options.addArguments("--no-sandbox");
        ChromeDriver driver = new ChromeDriver(options);



//        while (true){
//            if (driver.getCurrentUrl().equals(userPrefs.getUrl()))
//                break;
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        driver.get(userPrefs.getUrl());

        boolean productPage = false;
        Queue<String> prefColors = userPrefs.getColors();

        WebElement container;
        List<WebElement> myNewItems=new ArrayList<>();
        long startTime=0;
        long endTime;
        while (true) {
            if (driver.findElements(By.id("container")).size() > 0) {
                container = driver.findElement(By.id("container")); //Сокращаем поле поиска ссылок

                 startTime=System.currentTimeMillis();
                List<WebElement> allItems=container.findElements(By.className("inner-article"));
                for (WebElement randItem:allItems)
                    if (randItem.findElements(By.partialLinkText(userPrefs.getCodeWord())).size()>0)
                        myNewItems.add(randItem);
                //long stopTime= System.currentTimeMillis();
                //System.out.println("Items are found in "+(stopTime-startTime)+" seconds with a new way");

                if (myNewItems.size() > 0)
                    break;
            }
            if (userPrefs.getReload()) {
                driver.navigate().refresh();
                System.out.println("refreshed");
                try {
                    Thread.sleep(userPrefs.getReloadTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //Если перешли с основной страницы (юзер сам выбрал айтем)
            System.out.println(driver.getCurrentUrl());
            if (!driver.getCurrentUrl().equals(userPrefs.getUrl())) {
                productPage = true;
                break;
            }
        }

        if (!productPage)
            selectItem(driver, myNewItems, prefColors);
        endTime=System.currentTimeMillis();
        System.out.println("Айтем выбран за "+(endTime-startTime)+" мс");

        //Перешли на страницу товара
        System.out.println("Перешли на страницу товара");

        //Ждем, пока страница загрузится
        List<WebElement> commit;
        do {
            commit = driver.findElements(By.id("add-remove-buttons"));
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (commit.size() == 0);

        //Выбираем размер
        Queue<String> preferredSizes = userPrefs.getSizes(); //Достаем очередь желаемых размеров
        selectSize(driver, preferredSizes, prefColors);
        //Заполняем профиль
        try {
            Thread.sleep(Long.parseLong(userPrefs.getCheckoutDelay()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Заполняем профиль");
        if (userPrefs.isFillInfo()) {
            WebElement name = driver.findElement(By.cssSelector("#order_billing_name"));
            driver.executeScript("document.getElementById('order_billing_name').value='" + userProfile.getFullName() + "';" +
                    "document.getElementById('order_email').value='" + userProfile.getEmail() + "';" +
                    "document.getElementById('order_tel').value='" + userProfile.getTel() + "';" +
                    "document.getElementById('bo').value='" + userProfile.getAddress() + "';" +
                    "document.getElementById('oba3').value='" + userProfile.getAddress2() + "';" +
                    "document.getElementById('order_billing_address_3').value='" + userProfile.getAddress3() + "';" +
                    "document.getElementById('order_billing_zip').value='" + userProfile.getPostcode() + "';" +
                    "document.getElementById('order_billing_city').value='" + userProfile.getCity() + "';" +
                    "document.getElementById('cnb').value='" + userProfile.getCardNumber() + "';" +
                    "document.getElementById('vval').value='" + userProfile.getCvv() + "';");
            //Выбрать тип карты, страну, дату, поставить галочку, нажать на кнопку
            String countryIndex;
            switch (userProfile.getCountry()) {
                case "RUSSIA":
                    countryIndex = "28";
                    break;
                default:
                    countryIndex = "28";
            }
            String cardIndex;
            switch (userProfile.getCard()) {
                case "Visa":
                    cardIndex = "0";
                    break;
                case "American Express":
                    cardIndex = "1";
                    break;
                case "Mastercard":
                    cardIndex = "2";
                    break;
                case "Solo":
                    cardIndex = "3";
                    break;
                case "PayPal":
                    cardIndex = "4";
                    break;
                default:
                    cardIndex = "0";
            }
            int monthIndex = Integer.parseInt(userProfile.getMonth()) - 1;
            int yearIndex = Integer.parseInt(userProfile.getYear()) - 2018;
            driver.executeScript("document.getElementById('order_billing_country').selectedIndex = '" + countryIndex + "';" +
                    "document.getElementById('credit_card_type').selectedIndex = '" + cardIndex + "';" +
                    "document.getElementById('credit_card_month').selectedIndex = '" + monthIndex + "';" +
                    "document.getElementById('credit_card_year').selectedIndex = '" + yearIndex + "';");
            //Галочка и кнопка
            if (userPrefs.isTickAndPress()) {
                driver.executeScript("document.getElementsByClassName('checkbox').checked=true");
                driver.executeScript("document.getElementById('order_terms').click();");
                Actions action1 = new Actions(driver);
                action1.moveToElement(driver.findElement(By.className("terms"))).build().perform();
                driver.findElement(By.className("terms")).click();
                driver.findElement(By.name("commit")).click();
            }
        }


    }


    private void selectItem(WebDriver driver, List<WebElement> items, Queue<String> colors) {
        while (true) {
            String color = colors.poll();
            if (color != null) //Если в поле "цвет" что-то ввели
                //Выбираем цвет, который хочет юзер
                for (int i = 0; i < items.size(); i++) {

                    if (items.get(i).findElements(By.linkText(color)).size() > 0) {//Если в текущем айтеме есть такой цвет
                        //Навести курсор на текущий айтем
                        Actions action1 = new Actions(driver);
                        action1.moveToElement(items.get(i)).build().perform();
                        //Если появился div (Если солд-аут)
                        if (items.get(i).findElements(By.tagName("div")).size() > 0) {
                            items.remove(i);
                            break;
                        } else {
                            items.get(i).click();
                            break;
                        }
                    }
                }
            else
            //Если очередь цветов пустая (в поле "цвет" ничего не ввели либо все желаемые цвета перебрали)
            {
                //Если юзер попросил выбрать любой цвет
                if (userPrefs.isAnyColor()) {
                    //Нажимаем на любой айтем, который не солд-ауд
                    for (WebElement item : items) {
                        Actions action1 = new Actions(driver);
                        action1.moveToElement(item).build().perform();
                        //Если не появился div (Если не солд-аут)
                        if (item.findElements(By.tagName("div")).size() == 0) {
                            item.click();
                            break;
                        }
                    }
                    break;
                } else {
                    System.out.println("Все желаемые цвета закончились");
                    //Ждем перехода на страницу товаров (Юзер выберет цвет из оставшихся сам)
                    while (true) {
                        if (driver.getCurrentUrl().equals(userPrefs.getUrl())) { //Если он все еще на главной странице
                            try {
                                driver.navigate().refresh();
                                Thread.sleep(userPrefs.getReloadTime()); //Ждем, пока куда-нибудь не тыкнет
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else break;
                    }
                    break;
                }
            }
            //Если на странице товара - break
            if (!driver.getCurrentUrl().equals(userPrefs.getUrl()))
                break;
        }
    }

    private void selectSize(WebDriver driver, Queue<String> preferredSizes, Queue<String> prefColors) {
        Select selectBox = new Select(driver.findElement(By.id("size")));
        List<WebElement> sizeOptions = driver.findElements(By.tagName("option"));
        if (sizeOptions.size() == 0) //Если солд-аут
            selectDifferentItem(driver, prefColors); //Перебераем остальные цвета
        while (true) {
            String preferredSize = preferredSizes.poll(); //Достаем из очереди 1 размер
            if (preferredSize != null)
                //Выбираем размер, который хочет пользователь
                for (WebElement option : sizeOptions) {
                    if (preferredSize.equals(option.getText())) {
                        //Берем размер
                        selectBox.selectByVisibleText(preferredSize);
                        driver.findElement(By.name("commit")).click();
                        while (driver.findElements(By.linkText("checkout now")).size() == 0) {
                            try {
                                Thread.sleep(40);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        driver.findElement(By.linkText("checkout now")).click();
                        break;
                    }
                }
            else {
                //Если юзер попоросил выбрать любой размер
                if (userPrefs.isAnySize()) {
                    //Кладем любой размер в корзину
                    driver.findElement(By.name("commit")).click();
                    while (driver.findElements(By.linkText("checkout now")).size() == 0) {
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    driver.findElement(By.linkText("checkout now")).click();
                    break;
                } else {
                    System.out.println("Все желаемые размеры распроданы");
                    //Перебераем оставшиеся предпочтительные цвета
                    selectDifferentItem(driver, prefColors);
                    break;
                }
            }
            //Если на странице профиля
            if (driver.getCurrentUrl().equals("https://www.supremenewyork.com/checkout"))
                break;
        }
    }

    @Beta
    private void selectDifferentItem(WebDriver driver, Queue<String> prefColors) {
        //Ждем, пока юзер сам не выберет из оставшихся. (Временное решение, пока не написал код перебора оставшихся айтемов)
        while (driver.findElements(By.linkText("checkout now")).size() == 0) {
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        driver.findElement(By.linkText("checkout now")).click();
    }

}
