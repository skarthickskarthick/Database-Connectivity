package E_Commerce.ShopCart;

import E_Commerce.ShopCart.Model.Order;
import E_Commerce.ShopCart.POJO.AppProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopCartApplication implements CommandLineRunner{

	@Autowired
	private AppProperties obj;// otherwise nullpointer exception
	@Value("${app.config.port}")
	int appPort;

	public static void main(String[] args)  {
		SpringApplication.run(ShopCartApplication.class, args);

	}
	@Override
	public void run(String... args) {
		obj.printConfig();
		System.out.println("using @Value port is: "+appPort );

	}

}
