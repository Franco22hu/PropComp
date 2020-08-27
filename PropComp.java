import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

public class PropComp {
	
	static final int COLUMN_WIDTH = 36;
	
	public static void main(String[] args) throws Exception {

		// Checking files
		ArrayList<String> propertyFilenames = new ArrayList<>();
		
		for (String arg : args) {
			String fileName = arg;
			String extension = "";
			int i = fileName.lastIndexOf('.');
			if (i > 0) extension = fileName.substring(i + 1);
			if (extension.toLowerCase().equals("properties")) {
				propertyFilenames.add(arg);
			}
		}
		
		if (propertyFilenames.size() == 0) {
			System.out.println("No properties file found!");
			return;
		}
		
		System.out.println("Comparing properties files:");
		for (String filename : propertyFilenames) {
			System.out.println("  " + filename);
		}
		System.out.println();
		
		// Load properties
		ArrayList<Properties> properties = new ArrayList<>();
		
		for (String propertyFilename : propertyFilenames) {
			Properties prop = new Properties();
			InputStream input = new FileInputStream(propertyFilename);
			prop.load(input);
			input.close();
			properties.add(prop);
		}
		
		// Header
		ArrayList<String[]> fileNameArrays = new ArrayList<>();
		for (String filename : propertyFilenames) {
			fileNameArrays.add(breakString(filename, COLUMN_WIDTH));
		}
		int maxlines = 0;
		for (int i = 0; i < fileNameArrays.size(); i++)
			if (fileNameArrays.get(i).length > maxlines)
				maxlines = fileNameArrays.get(i).length;
		for (int i = 0; i < maxlines; i++) {
			String headerLine = "";
			if (i == 0) headerLine += formatString("Property name", COLUMN_WIDTH);
			else headerLine += formatString("", COLUMN_WIDTH);
			headerLine += "   ";
			for (String[] fileNameArray : fileNameArrays) {
				if (i < fileNameArray.length) headerLine += formatString(fileNameArray[i], COLUMN_WIDTH);
				else headerLine += formatString("", COLUMN_WIDTH);
				headerLine += "   ";
			}
			System.out.println(headerLine);
		}
		System.out.println();
		
		// Get all keys
		Set<Object> allKeys = new TreeSet<Object>();
		for (int i = 0; i < properties.size(); i++) {
			allKeys.addAll(properties.get(i).keySet());
		}

		// Print all
		for (Object object : allKeys) {
			String key = (String)object;
			String line = formatString(key, COLUMN_WIDTH) + " - ";

			for (int i = 0; i < properties.size(); i++) {
				line += formatString(properties.get(i).getProperty(key), COLUMN_WIDTH);
				if (i != properties.size() - 1)
					line += " - ";
			}
			System.out.println(line);
		}
	}
	
	/** Creates whitespace around a string to make it as the given length
		or cuts it if too long. Also replaces null value with missing. */
	public static String formatString(String string, int length) {
		
		if (string == null) return formatString("(missing)", length);
		
		if (string.equals("")) {
			for (int i = 0; i < length; i++) { string += " "; }
			return string;
		}
		
		if (string.length() == length) return string;
		
		if (string.length() > length) {
			return string.substring(0, length - 3) + "...";
			
		} else {
			int diff = length - string.length();
			int left = diff / 2;
			int right = diff / 2 + (diff % 2 != 0 ? 1 : 0);
			String result = "";
			for (int i = 0; i < left; i++) { result += " "; }
			result += string;
			for (int i = 0; i < right; i++) { result += " "; }
			return result;
		}
	}
	
	/** Breaks a long string into smaller parts by given length */
	public static String[] breakString(String string, int length) {
		
		return string.split("(?<=\\G.{" + length + "})");
	}
}
