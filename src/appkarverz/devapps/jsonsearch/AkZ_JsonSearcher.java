package appkarverz.devapps.jsonsearch;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AkZ_JsonSearcher {

	public AkZ_JsonSearcher() {
		super();

	}

	/**
	 * finds the value for corresponding key. Cast the returned object to
	 * correct object(JSON object, Array, string, boolean, number)
	 * 
	 * @param akz_JSONObj
	 *            JSON object in which we need to search the key value.
	 * @param akzStr_keyValueToFind
	 *            key of value we need.
	 * @return returns the object, and that is to be casted to corresponding
	 *         JSON object, array, string, boolean, number.
	 */
	public static Object getKeyValue(JSONObject akz_JSONObj, String akzStr_keyValueToFind) {

		ArrayList<String> arrayList_jsonTopLevelKeys = new AkZ_KeysFinder().findRootKeys(akz_JSONObj);

		if (arrayList_jsonTopLevelKeys.contains(akzStr_keyValueToFind)) {

			if (akz_JSONObj.get(akzStr_keyValueToFind).equals(null))
				return null;

			try {
				return akz_JSONObj.getBoolean(akzStr_keyValueToFind);
			} catch (JSONException e) {
				try {
					return akz_JSONObj.getJSONArray(akzStr_keyValueToFind);
				} catch (JSONException e2) {
					try {
						return akz_JSONObj.getJSONObject(akzStr_keyValueToFind);
					} catch (JSONException e3) {
						try {
							return akz_JSONObj.getString(akzStr_keyValueToFind);
						} catch (JSONException e4) {
							try {
								return akz_JSONObj.getDouble(akzStr_keyValueToFind);
							} catch (JSONException e5) {

							}
						}
					} // catch(e3)
				} // catch(e2)
			} // catch(e1)
		} // if

		// if we key is not found go deep into JSON.
		else {
			Iterator<String> iterator = arrayList_jsonTopLevelKeys.iterator();
			while (iterator.hasNext()) {
				String strPresentKey = iterator.next();
				try {
					JSONObject innerJsonObj = akz_JSONObj.getJSONObject(strPresentKey);

					try {
						Object res = getKeyValue(innerJsonObj, akzStr_keyValueToFind);
						res.equals(null);
						return res;
					} catch (NullPointerException n) {

					}
				} catch (JSONException e) {
					try {
						JSONArray jsonArray = akz_JSONObj.getJSONArray(strPresentKey);

						for (int index = 0; index <= jsonArray.length(); index++) {
							try {
								JSONObject ObjInsideArray = jsonArray.getJSONObject(index);
								Object res = getKeyValue(ObjInsideArray, akzStr_keyValueToFind); 
								try {
									res.equals(null);
									return res;
								} catch (NullPointerException n) {

								}
							} catch (JSONException e2) {

							} // catch(e2)
						} // for
					} catch (JSONException e1) {

					} // catch(e1)
				}

			}

		}

		return null;

	}

	/**
	 * Returns all the values for all the occurrences of keys in JSON object.
	 * 
	 * @param akz_jsonObj
	 *            Json object in which we need to find the value.
	 * @param akzStr_keyValueToFind
	 *            key for which we need to value
	 * @return arraylist of all the values present in given JSON object for
	 *         corresponding key.
	 */
	public static ArrayList<Object> getAllKeyValues(JSONObject akz_jsonObj, String akzStr_keyValueToFind) {

		ArrayList<Object> akzArrayList_resultsList = new ArrayList<>();
		ArrayList<String> jsonFirstRowKeys = new AkZ_KeysFinder().findRootKeys(akz_jsonObj);

		if (jsonFirstRowKeys.contains(akzStr_keyValueToFind))
			akzArrayList_resultsList.add(akz_jsonObj.get(akzStr_keyValueToFind));

		// now search deep inside JSON.
		Iterator<String> iterator = jsonFirstRowKeys.iterator();
		while (iterator.hasNext()) {
			String strPresentKey = iterator.next();
			try {
				JSONObject innerJsonObj = akz_jsonObj.getJSONObject(strPresentKey);
				Object res = getKeyValue(innerJsonObj, akzStr_keyValueToFind);
				try {
					if (!res.equals(null))
						akzArrayList_resultsList.add(res);
				} catch (NullPointerException n) {

				}
			} catch (JSONException e) {
				// if it is JsonArray
				try {
					JSONArray jsonArray = akz_jsonObj.getJSONArray(strPresentKey);
					for (int index = 0; index <= jsonArray.length(); index++) {
						try {
							JSONObject ObjInsideArray = jsonArray.getJSONObject(index);
							Object res = getKeyValue(ObjInsideArray, akzStr_keyValueToFind);
							try {
								if (!res.equals(null))
									akzArrayList_resultsList.add(res);
							} catch (NullPointerException n) {

							}
						} catch (JSONException e2) {

						} // catch(e2)
					} // for
				} catch (JSONException e1) {

				} // catch(e1)
			}

		}

		return akzArrayList_resultsList;

	}

}
