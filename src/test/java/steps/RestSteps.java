package steps;

import com.jayway.jsonpath.JsonPath;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.testng.Assert;

import java.io.IOException;

public class RestSteps {

    public HttpResponse getRequest(String server, String func, String params) throws IOException, UnirestException {
        return Unirest.get("https://"+ server +"/"+ func +"?"+ params).header("accept", "application/json").asJson();
    }

    public int getStatusCode(HttpResponse response){
        return response.getStatus();
    }

    public String getBody(HttpResponse response){
        return response.getBody().toString();
    }

    public void compare(String json, String path, String exepted){
        Assert.assertEquals(JsonPath.read(json, path).toString(), exepted);
    }
}