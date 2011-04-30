package info.jo32.EasyQandASite.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class PropertyLoader {

	public static String getProperty(String key) throws IOException {
		// InputStream in = new BufferedInputStream(new FileInputStream(path));
		Properties props = new Properties();
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("");
		String path = url.getPath();
		File pPath = new File(path);
		path = pPath.getParent();
		path = path + "\\web.properties";
		InputStream in = new FileInputStream(path);
		props.load(in);
		String value = props.getProperty(key, null);
		return value;
	}

	public static String getSQLstatement(String filename) throws IOException {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("");
		String path = url.getPath();
		File pPath = new File(path);
		path = pPath.getParent();
		path = path + "\\" + filename;
		BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(path)));
		StringBuffer sqlStatement = new StringBuffer();
		String line = in.readLine();
		while (line != null) {
			sqlStatement.append(line + "\n");
			line = in.readLine();
		}
		return sqlStatement.toString();
	}
}
