package Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


class Data {

	private String name;
	private String price;
	private String rating;

	public Data(String name, String price, String rating) {
		this.name = name;
		this.price = price;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public String getPrice() {
		return price;
	}

	public String getRating() {
		return rating;
	}
}

public class FlipkartTask {

	public static void main(String[] args) throws InterruptedException, IOException {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Punam\\Downloads\\chromedriver_win32\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		driver.get("https://www.flipkart.com/");

		driver.findElement(By.xpath("//button[@class='_2KpZ6l _2doB4z'] ")).click();
		driver.findElement(By.xpath("//input[@title='Search for products, brands and more']"))
				.sendKeys("apple mobiles");

		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[contains(text(),'Price -- Low to High')]")).click();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		Thread.sleep(3000);
		
		List<WebElement> names = driver.findElements(By.xpath("//div[@class='_4rR01T']"));
		List<WebElement> prices = driver.findElements(By.xpath("//div[@class='_30jeq3 _1_WHN1']"));
		List<WebElement> ratings = driver.findElements(By.xpath("//span[contains(text(),'Ratings')]"));

		
		ArrayList<Data> allData = new ArrayList<Data>();
		for (int i = 0; i < prices.size(); i++) {
			String str = prices.get(i).getText().substring(1).trim().replace(",", "");
			int temp = Integer.parseInt(str);
			if (temp <= 40000) {
				allData.add(new Data(names.get(i).getText(), prices.get(i).getText(), ratings.get(i).getText()));
			}
		}

		writeInCSV(allData);
		driver.quit();
	}

	static void writeInCSV(ArrayList<Data> allData) throws IOException {
		File file = new File("D:\\Data.csv");
		FileWriter writer = new FileWriter("D:\\Data.csv");

		for (int i = 0; i < allData.size(); i++) {

			writer.append("\"" + allData.get(i).getName() + "\"");
			writer.append(",");
			writer.append("\"" + allData.get(i).getPrice() + "\"");
			writer.append(",");
			writer.append("\"" + allData.get(i).getRating().split(" ")[0] + "\"");
			writer.append("\n");
		}

		writer.flush();
		writer.close();
	}

}
