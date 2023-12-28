package api.test;

import api.endpoints.UserEndPoint;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests {
    Faker faker;
    User userPayLoad;
    public Logger logger;

    @BeforeClass
    public void setupData() {
        faker = new Faker();
        userPayLoad = new User();

        userPayLoad.setId(faker.idNumber().hashCode());
        userPayLoad.setUsername(faker.name().username());
        userPayLoad.setFirstName(faker.name().firstName());
        userPayLoad.setLastName(faker.name().lastName());
        userPayLoad.setEmail(faker.internet().safeEmailAddress());
        userPayLoad.setPassword(faker.internet().password(5, 10));
        userPayLoad.setPhone(faker.phoneNumber().cellPhone());

        logger = LogManager.getLogger(this.getClass());
        logger.debug("debugging.....");
    }

    @Test(priority = 1)
    public void testPostUser() {
        logger.info("***************** Creating user *******************");

        Response response = UserEndPoint.createUser(this.userPayLoad);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        logger.info("***************** User is created *******************");
    }

    @Test(priority = 2)
    public void testGetUserByName() {

        logger.info("***************** Reading User Info *******************");

        Response response = UserEndPoint.readUser(this.userPayLoad.getUsername());
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        logger.info("***************** User Info Displayed *******************");
    }

    @Test(priority = 3)
    public void testUpdateUserByName() {

        logger.info("***************** Updating User *******************");

        userPayLoad.setFirstName(faker.name().firstName());
        userPayLoad.setLastName(faker.name().lastName());
        userPayLoad.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndPoint.updateUser(this.userPayLoad.getUsername(), userPayLoad);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        logger.info("***************** User Updated *******************");

        //Checking data after update
        Response responseAfterUpdate = UserEndPoint.readUser(this.userPayLoad.getUsername());
        Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);

        logger.info("***************** User Info Displayed After Update *******************");
    }

    @Test(priority = 4)
    public void testDeleteUserByName() {

        logger.info("***************** Deleting User *******************");

        Response response = UserEndPoint.deleteUser(this.userPayLoad.getUsername());
        Assert.assertEquals(response.getStatusCode(), 200);

        logger.info("***************** User Deleted *******************");
    }
}