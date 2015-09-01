package appkarverz.devapps.jsonsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Sandeep Gurram, Raghavedra, Praneeth
 *
 */
public class Main {

	private final static String key = "id";

	// JSON object
	private static JSONObject akz_JSONobj = new JSONObject("{" + "\"pageInfo\": " + "{" + "\"pageName\": \"abc\","
			+ "\"pagePic\": \"link\"" + "}, " + "\"posts\": [" + "{	" + "\"post_id\": \"123456789012_123456789012\","
			+ "\"actor_id\": \"1234567890\"," + "\"picOfPersonWhoPosted\": \"link\","
			+ "\"nameOfPersonWhoPosted\": \"Jane Doe\"," + "\"message\": \"Sounds cool. Can't wait to see it!\","
			+ "\"likesCount\": \"2\"," + "\"comments\": []," + "\"timeOfPost\": \"1234567890\"" + "}" + "],"
			+ "\"timeOfPost\": \"sandeep\"" + "}");

	public static void main(String[] args) {

		try {
			// loading JSON from URL(for test purpose)
			akz_JSONobj = readJsonFromUrl("http://api.openweathermap.org/data/2.5/weather?q=india");
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}

		System.out.println("JSON -->" + akz_JSONobj);
		System.out.println("Key -->" + key);
		System.out.println("All Values -->" + AkZ_JsonSearcher.getAllKeyValues(akz_JSONobj, key).toString());
		System.out.println("Value -->" + AkZ_JsonSearcher.getKeyValue(akz_JSONobj, key).toString());

	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}
}
