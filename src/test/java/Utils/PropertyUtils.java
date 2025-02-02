package Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils
{
	public static Properties propertyLoader(String filepath)
	{
		Properties properties = new Properties();
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader(filepath));
			try {
				properties.load(reader);
				reader.close();
			}catch(IOException e)
			{
				e.printStackTrace();
				throw new RuntimeException("Failed to Load the Properties files " + filepath);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Properties File not found at " + filepath);
		}
		
		return properties;
	}
}
