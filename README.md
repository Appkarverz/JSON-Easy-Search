# JSON-Easy-Search
It is a java library, can be used to search any JSON object or string or any JSON key value easy with one line of code with worrying about JSON complexity and parsing of JSON. Just pass JSON object and key of value you need to find.

Sample Usage

//Step 1: get the JSON object.
JSONObject jsonObj = new JSONObject();

//Step 2: Call the function and it returns the value.
// if want to get a JSONArray for which key is "valuesList" then 
JSONArray jsonArray = (JSONArray)AkZ_JsonSearcher.getKeyValue(jsonObj, "valuesList");
//casting is important 

//if want to get a JSON String for which key is "stringValue" then 
JSONArray jsonArray = (String)AkZ_JsonSearcher.getKeyValue(jsonObj, "stringValue");

//if have multiple keys with same name. let name be "value" 
 ArrayList<Object> arrayList = AkZ_JsonSearcher.getAllKeyValues(jsonObj, key);
//in this case array list of all the values corresponding to the key is returned. each object need to casted to correct corresponding type.

