package api.test;

import api.endpoints.UserEndPointUsingHashMap;
import api.endpoints.UserEndPointWithProperties;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

public class UserTests3 {
    Faker faker= new Faker();
    int id = faker.idNumber().hashCode();
    String username = faker.name().username();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = faker.internet().safeEmailAddress();
    String password = faker.internet().password(5, 10);
    String phone = faker.phoneNumber().cellPhone();
    HashMap data = new HashMap();


    @BeforeClass
    public void setupData() {
        data.put("id", id);
        data.put("username", username);
        data.put("firstName", firstName);
        data.put("lastName", lastName);
        data.put("email", email);
        data.put("password", password);
        data.put("phone", phone);
    }

    @Test(priority=1)
    public void testPostUser()
    {
        Response response= UserEndPointUsingHashMap.createUser(data);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test(priority=2)
    public void testGetUserByName()
    {
        Response response=UserEndPointUsingHashMap.readUser(data.get("username").toString());
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

        Response response=UserEndPointUsingHashMap.updateUser(data.get("username").toString(), data);
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(),200);

        //Checking data after update
        Response responseAfterupdate=UserEndPointWithProperties.readUser(data.get("username").toString());
        Assert.assertEquals(responseAfterupdate.getStatusCode(),200);
    }

    @Test(priority=4)
    public void testDeleteUserByName()
    {
        Response response=UserEndPointWithProperties.deleteUser(data.get("username").toString());
        Assert.assertEquals(response.getStatusCode(),200);
    }

}
