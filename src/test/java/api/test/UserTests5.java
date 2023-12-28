package api.test;

import api.endpoints.UserEndPointUsingJSONObject;
import api.endpoints.UserEndPointWithProperties;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;

public class UserTests5 {
    Faker faker;
    File f;
    FileReader fr;
    JSONTokener jt;
    JSONObject data;
    String firstName; String lastName; String email;

    @BeforeClass
    public void setupData() throws FileNotFoundException {
        f = new File("testData/Userdata.json");
        fr = new FileReader(f);
        jt = new JSONTokener(fr);
        data = new JSONObject(jt);
        faker = new Faker();
    }

    @Test(priority=1)
    public void testPostUser()
    {
        Response response= UserEndPointUsingJSONObject.createUser(data);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test(priority=2)
    public void testGetUserByName()
    {
        Response response=UserEndPointUsingJSONObject.readUser(data.get("username").toString());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test(priority=3)
    public void testUpdateUserByName()
    {
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = faker.internet().safeEmailAddress();

        //update data using payload
        data.put("firstName", firstName);
        data.put("lastName", lastName);
        data.put("email", email);

        Response response=UserEndPointUsingJSONObject.updateUser(data.get("username").toString(), data);
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(),200);

        //Checking data after update
        Response responseAfterUpdate=UserEndPointWithProperties.readUser(data.get("username").toString());
        Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);
    }

    @Test(priority=4)
    public void testDeleteUserByName()
    {
        Response response=UserEndPointUsingJSONObject.deleteUser(data.get("username").toString());
        Assert.assertEquals(response.getStatusCode(),200);
    }
}