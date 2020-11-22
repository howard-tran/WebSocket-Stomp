package com.helper;

import com.App;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;

public class PropertyHelper {

	public static Optional<String> GetProperty(String property) throws Exception {
		Properties prop = new Properties();

		InputStream inputStream = App.class.getResourceAsStream("/project.properties");
		prop.load(inputStream);

		if (prop.containsKey(property)) {
			return Optional.of(prop.getProperty(property));
		} else {
			return Optional.empty();
		}
	}

	/**
	 *
	 * @return set of hash key <div>
	 *         <p>
	 *         connection (connection string)
	 *         </p>
	 *         <p>
	 *         database (database name)
	 *         </p>
	 *         <p>
	 *         host (host ip or server alias)
	 *         </p>
	 *         <p>
	 *         port (host port)
	 *         </p>
	 *         </div>
	 * @throws Exception
	 */
	public static HashMap<String, String> GetMongoDBChat() throws Exception {
		HashMap<String, String> res = new HashMap<String, String>();
		res.put("connection", GetProperty("mongodb-connection").get());
		res.put("database", GetProperty("mongodb-database").get());
		res.put("host", GetProperty("mongodb-host").get());
		res.put("port", GetProperty("mongodb-port").get());

		return res;
	}
}
