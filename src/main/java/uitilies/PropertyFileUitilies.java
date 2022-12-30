package uitilies;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUitilies {

public static String getValueKey(String key) throws Throwable {
		
	Properties config = new Properties();
	config.load(new FileInputStream("D:\\Prime Batch\\Mavan_MMT\\PropertyFile\\Environment.properties"));
	return config.getProperty(key);
	
	}
	
}
