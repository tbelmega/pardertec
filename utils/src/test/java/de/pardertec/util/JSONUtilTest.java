package de.pardertec.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class JSONUtilTest {


    @Test
    public void testContainsJSONContent1() throws JSONException {
        //arrange
        JSONObject bigJson = new JSONObject("{\"a\":\"b\"}");
        JSONObject smallJson = new JSONObject("{\"a\":\"b\"}");

        //act
        //assert
        assertTrue(JSONUtil.containsJSONContent(smallJson, bigJson));
        assertTrue(JSONUtil.containsJSONContent(bigJson, smallJson));
    }

    @Test
    public void testContainsJSONContent2() throws JSONException {
        //arrange
        JSONObject bigJson = new JSONObject("{\"a\":\"b\"}");
        JSONObject smallJson = new JSONObject("{\"b\":\"a\"}");

        //act
        //assert
        assertFalse(JSONUtil.containsJSONContent(smallJson, bigJson));
        assertFalse(JSONUtil.containsJSONContent(bigJson, smallJson));
    }

    @Test
    public void testContainsJSONContent3() throws JSONException {
        //arrange
        JSONObject bigJson = new JSONObject("{\"a\":\"b\",\"c\":\"d\"}");
        JSONObject smallJson = new JSONObject("{\"a\":\"b\"}");

        //act
        //assert
        assertTrue(JSONUtil.containsJSONContent(bigJson, smallJson));
    }

    @Test
    public void testContainsJSONContent4() throws JSONException {
        //arrange
        JSONObject bigJson = new JSONObject("{\"a\":\"b\"}");
        JSONObject smallJson = new JSONObject("{\"a\":\"b\",\"c\":\"d\"}");

        //act
        //assert
        assertFalse(JSONUtil.containsJSONContent(bigJson, smallJson));
    }

    @Test
    public void testContainsJSONContent5() throws JSONException {
        //arrange
        JSONObject bigJson = new JSONObject("{\"a\":1,\"c\":\"d\"}");
        JSONObject smallJson = new JSONObject("{\"a\":1}");

        //act
        //assert
        assertTrue(JSONUtil.containsJSONContent(bigJson, smallJson));
    }

    @Test
    public void testContainsJSONContent6() throws JSONException {
        //arrange
        JSONObject bigJson = new JSONObject("{\"a\":true,\"c\":\"d\"}");
        JSONObject smallJson = new JSONObject("{\"a\":true}");

        //act
        //assert
        assertTrue(JSONUtil.containsJSONContent(bigJson, smallJson));
    }

    @Test
    public void testContainsJSONContent7() throws JSONException {
        //arrange
        String nestedJson = "{\"b\":\"a\"}";
        JSONObject bigJson = new JSONObject("{\"a\":" + nestedJson + ",\"c\":\"d\"}");
        JSONObject smallJson = new JSONObject("{\"a\":" + nestedJson + "}");

        //act
        //assert
        assertTrue(JSONUtil.containsJSONContent(bigJson, smallJson));
    }

    @Test
    public void testContainsJSONContent11() throws JSONException {
        //arrange
        String nestedJson = "{\"b\":\"a\"}";
        String otherJson = "{\"b\":\"d\"}";
        JSONObject bigJson = new JSONObject("{\"a\":" + nestedJson + ",\"c\":\"d\"}");
        JSONObject smallJson = new JSONObject("{\"a\":" + otherJson + "}");

        //act
        //assert
        assertFalse(JSONUtil.containsJSONContent(bigJson, smallJson));
    }

    @Test
    public void testContainsJSONContent8() throws JSONException {
        //arrange
        String nestedJson = "[\"b\",\"a\"]";
        JSONObject bigJson = new JSONObject("{\"a\":" + nestedJson + ",\"c\":\"d\"}");
        JSONObject smallJson = new JSONObject("{\"a\":" + nestedJson + "}");

        //act
        //assert
        assertTrue(JSONUtil.containsJSONContent(bigJson, smallJson));
    }

    @Test
    public void testContainsJSONContent9() throws JSONException {
        //arrange
        String nestedJson = "[\"b\",\"a\"]";
        String smallerNestedJson = "[\"b\"]";
        JSONObject bigJson = new JSONObject("{\"a\":" + smallerNestedJson + ",\"c\":\"d\"}");
        JSONObject smallJson = new JSONObject("{\"a\":" + nestedJson + "}");

        //act
        //assert
        assertFalse(JSONUtil.containsJSONContent(bigJson, smallJson));
    }

    @Test
    public void testContainsJSONContent10() throws JSONException {
        //arrange
        String nestedJson = "[\"b\",\"a\"]";
        String smallerNestedJson = "[\"b\"]";
        JSONObject bigJson = new JSONObject("{\"a\":" + nestedJson + ",\"c\":\"d\"}");
        JSONObject smallJson = new JSONObject("{\"a\":" + smallerNestedJson + "}");

        //act
        //assert
        assertTrue(JSONUtil.containsJSONContent(bigJson, smallJson));
    }

}
