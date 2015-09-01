package appkarverz.devapps.jsonsearch;

import java.util.ArrayList;

import org.json.JSONObject;

public class AkZ_KeysFinder {

	private int akzInt_curntPos;
	private int akzInt_openBracePos;
	private int akzInt_curntOpenQuotePos;
	private int akzInt_curntCloseQuotePos;
	private int akzInt_curntCommaPos;

	/**
	 * Finds the top level keys and returns them
	 * 
	 * @param akz_jsonObject
	 *            JSON object in which top level keys to be known
	 * @return Arraylist of keys at top level for the given JSON object.
	 */
	public ArrayList<String> findRootKeys(JSONObject akz_jsonObject) {

		ArrayList<String> akzArrayList_keys = new ArrayList<>();
		StringBuilder jsonStr = new StringBuilder(akz_jsonObject.toString());
		akzInt_openBracePos = jsonStr.indexOf("{");

		do {

			akzInt_curntOpenQuotePos = jsonStr.indexOf("\"", akzInt_curntPos);
			akzInt_curntCloseQuotePos = jsonStr.indexOf("\"", ++akzInt_curntOpenQuotePos);
			String strKey = jsonStr.substring(akzInt_curntOpenQuotePos, akzInt_curntCloseQuotePos);
			akzInt_curntPos = akzInt_curntCloseQuotePos + 2; // takes cursor to
																// start
																// position of
																// value.

			switch (jsonStr.charAt(akzInt_curntPos) + "") {
			case "\"":
				akzInt_curntCommaPos = jsonStr.indexOf(",", akzInt_curntPos);
				break;
			case "{":
			case "[":
				akzInt_curntCommaPos = jsonStr.indexOf(",",
						matchingBracePos(akzInt_curntPos, jsonStr.toString(), jsonStr.charAt(akzInt_curntPos) + ""));
				break;
			default:
				akzInt_curntCommaPos = jsonStr.indexOf(",", akzInt_curntPos);
				break;
			}

			akzArrayList_keys.add(strKey);
			akzInt_curntPos = akzInt_curntCommaPos;

		} while (akzInt_curntPos > 1);

		return akzArrayList_keys;
	}

	/**
	 * To get the matching closing brace of particular brace
	 * 
	 * @param openingPosBrace
	 *            position of brace for which we need find matching close brace.
	 * @param str
	 *            String in which we need to find the closing brace position.
	 * @param matchBracePattern
	 *            { or [.
	 * @return corresponding brace position.
	 */
	public int matchingBracePos(final int openingPosBrace, String str, String matchBracePattern) {

		int currentPos = openingPosBrace + 1;
		int noOfOpenedBraces = 0;
		String closeBracePattern = null;

		// setting closing brace according to input
		switch (matchBracePattern) {
		case "{":
			closeBracePattern = "}";
			break;
		case "[":
			closeBracePattern = "]";
			break;
		}

		String res = str.charAt(currentPos) + "";
		for (int index = currentPos; index <= str.length(); index++) {
			res = str.charAt(index) + "";
			if (res.equals(matchBracePattern) || res.equals(closeBracePattern)) {
				if (res.equals(closeBracePattern)) {
					if (noOfOpenedBraces == 0)
						return index;
					else
						noOfOpenedBraces--;
				} else
					noOfOpenedBraces++;
			}
		}
		return openingPosBrace;

	}

}
