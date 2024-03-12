import Cookies.LoginWithCookies;

public class NewTest {
	
 WebDriver driver;	// Driver ban đầu => Mỗi class sẽ có driver được khai báo riêng (như ID đại diện cho class đó) 
  @BeforeTest
  public void Test() throws InterruptedException 
  {
	  LoginWithCookies Login = new LoginWithCookies();
	driver = Login.LoginCookies(driver); // return driver: trả về driver ban đầu về lại đây để tái chế sử dụng tiếp cho các hàm Test dưới   
  }
  
  @Test
  public void Update() 
  {
	    // Code what ever you want here    
  }
  	
}
