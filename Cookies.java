    WebDriver driver; 
	 // Hàm này được gọi chạy đầu tiên để lấy cookies cho các lần Login sau 
	 // Sau khi chạy hàm này 1 lần thì các lần sau không cần chạy nữa 
	  @BeforeTest
	  public void GetAllCookies() throws IOException
	  {
		// Đường dẫn để chạy trình duyệt trên chrome   
		System.setProperty("webdriver.chrome.driver", "c:\\chromedriver.exe");
		// Khai báo driver để sử dụng 
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		// Truy cập đường link website 
		driver.navigate().to("https://cntttest.vanlanguni.edu.vn:18081/Phancong02/Account/Login");
		
		// Skip qua bước unsafe 
		driver.findElement(By.id("details-button")).click();
		driver.findElement(By.id("proceed-link")).click();
		driver.findElement(By.xpath("//button[@id='OpenIdConnect']")).click();		
		// Bạn cần thực hiện đăng nhập bằng tay tại đây
		System.out.println("Mong quý vị thực hiện xác thực thủ công theo website ở lần đăng nhập đầu tiên này");
		
		
       // Chờ cho phần tử sau khi đăng nhập thành công
       WebDriverWait wait = new WebDriverWait(driver, 600);
       wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='user-status']")));
				
       // Lấy danh sách cookie sau khi đăng nhập
       Set<Cookie> cookies = driver.manage().getCookies();
               
       // Đưa vào save file cookies 
       try {
       	FileWriter fileWriter = new FileWriter("cookies.txt");
       	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
       	// Xóa sạch data trong file để chuận bị viết đè data mới vào 
       	File file = new File("cookies.txt");
           boolean fileExists = file.exists();

           if (fileExists) {
               // Kiểm tra xem file có dữ liệu hay không
               if (file.length() > 0) {
                   // Xóa nội dung trong file cookies.txt nếu có dữ liệu
                   file.delete();
               }
           }      
           // Thêm cookies mới kiếm được vào file này 
       	for (Cookie cookie : cookies) {
       		bufferedWriter.write(cookie.getName() + ";" + cookie.getValue());
       		bufferedWriter.newLine();
       	}
       	bufferedWriter.close();
       	fileWriter.close();
       	} catch (IOException e) {
       		e.printStackTrace();
       	}
       
       driver.quit();
	  }


// -----------------------------------------------------------------------------------------------------------------------------

// Hàm này sẽ được import qua các class khác khi cần Login những lần sau để viết Test Case 
	 // Hàm này sẽ không chạy ở Class này. Nó chỉ được chạy khi được import qua packge class khác 
	  public WebDriver LoginCookies(WebDriver driver) throws InterruptedException 
	  {
		   
			  	System.setProperty("webdriver.chrome.driver", "c:\\chromedriver.exe");
		  		//System.setProperty("webdriver.chrome.driver", webdriverPath);
		  		// Khai báo driver để sử dụng 
				driver = new ChromeDriver();
	    		driver.manage().window().maximize();
				// Truy cập đường link website 
				driver.navigate().to("https://cntttest.vanlanguni.edu.vn:18081/Phancong02/Account/Login");
				
				// Chờ cho đến khi user đi qua lớp bảo mật unsafe thì mới add Cookies vào 
				driver.findElement(By.id("details-button")).click();
				driver.findElement(By.id("proceed-link")).click();
				
				
				WebDriverWait wait = new WebDriverWait(driver, 600);
		        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("socialLoginList")));
		        
				// Load cookies from file => Login to where ?
			    try {
			        FileReader fileReader = new FileReader("cookies.txt");
			        BufferedReader bufferedReader = new BufferedReader(fileReader);
			        String line;
			        while ((line = bufferedReader.readLine()) != null) {
			            String[] cookieParts = line.split(";");
			            Cookie cookie = new Cookie(cookieParts[0], cookieParts[1]);
			            driver.manage().addCookie(cookie);
			        }
			        
			        bufferedReader.close();
			        fileReader.close();
			    } catch (IOException e) {
			        e.printStackTrace();
			        
			    }
			    
			    // Truy cập vào trang web muốn Login => Login to here after cookies being loaded  
			    driver.navigate().to("https://cntttest.vanlanguni.edu.vn:18081/Phancong02/Account/Login"); // Update with your website URL+
			    return driver;

		        
	  }


