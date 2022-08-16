package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.SuperHeros;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.EndPoints;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.TestNgListener.userData;

public class MyStepdefs {
    ObjectMapper objectMapper=new ObjectMapper();
    JSONObject jsonObject;
    JsonPath jsonpath;
    SuperHeros superHero;
    Response response;
    SuperHeros responseHero;
    SuperHeros responseHero1;


    @Given("User Details")
    public void userDetails() {
        jsonObject=(JSONObject) userData.get("createUser");

    }

    @When("creating a User")
    public void creatingAUser() throws JsonProcessingException {
            superHero=new SuperHeros((String) jsonObject.get("name"), (String) jsonObject.get("superName"),
                    (String) jsonObject.get("profession"), (Long) jsonObject.get("age"),(Boolean)jsonObject.get("canFly"));
            response=given().body(superHero).post(EndPoints.userEndpoint)
                    .then().body(matchesJsonSchemaInClasspath("user_schema.json"))
                    .statusCode(201).extract().response();
            responseHero=objectMapper.readValue(response.asString(),SuperHeros.class);
        }
        


    @Then("User must Created")
    public void userMustCreated() {
        Assert.assertEquals(superHero.getName(),responseHero.getName());
        Assert.assertEquals(superHero.getSuperName(),responseHero.getSuperName());
        Assert.assertEquals(superHero.getProfession(),responseHero.getProfession());
        Assert.assertEquals(superHero.getAge(),responseHero.getAge());
        Assert.assertEquals(superHero.getCanFly(),responseHero.getCanFly());
    }

    @When("creating user with super name null")
    public void creatingUserWithSuperNameNull() {
        superHero=new SuperHeros((String) jsonObject.get("name"), null,
                (String) jsonObject.get("profession"), (Long) jsonObject.get("age"),(Boolean)jsonObject.get("canFly"));
        response=given().body(superHero).post(EndPoints.userEndpoint)
                .then()
                .statusCode(400).extract().response();
    }

    @Then("message thrown")
    public void messageThrown() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Super Name is required");
    }

    @When("creating a user with age is zero")
    public void creatingAUserWithAgeIsZero(){
        superHero=new SuperHeros((String) jsonObject.get("name"), (String) jsonObject.get("superName"),
                (String) jsonObject.get("profession"),0,(Boolean)jsonObject.get("canFly"));
        response=given().body(superHero).post(EndPoints.userEndpoint)
                .then()
                .statusCode(400).extract().response();
    }

    @Then("message thrown age is required")
    public void messageThrownAgeIsRequired() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Age is required");

    }

    @When("creating user with name null")
    public void creatingUserWithNameNull() {
        superHero=new SuperHeros(null, (String) jsonObject.get("superName"),
                (String) jsonObject.get("profession"), (Long) jsonObject.get("age"),(Boolean)jsonObject.get("canFly"));
        response=given().body(superHero).post(EndPoints.userEndpoint)
                .then()
                .statusCode(400).extract().response();
    }

    @Then("message thrown name is required")
    public void messageThrownNameIsRequired() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Name is required");
    }

    @When("creating user with profession null")
    public void creatingUserWithProfessionNull() {
        superHero=new SuperHeros((String) jsonObject.get("name"), (String) jsonObject.get("superName"),
                null, (Long) jsonObject.get("age"),(Boolean)jsonObject.get("canFly"));
        response=given().body(superHero).post(EndPoints.userEndpoint)
                .then()
                .statusCode(400).extract().response();
    }

    @Then("message thrown profession is required")
    public void messageThrownProfessionIsRequired() {
        jsonpath = new JsonPath(response.asString());
        Assert.assertEquals(jsonpath.getString("message"),"Profession is required");

    }

    @When("creating user with blank canFly")
    public void creatingUserWithBlankCanFly() throws JsonProcessingException {
        superHero=new SuperHeros((String) jsonObject.get("name"), (String) jsonObject.get("superName"),
                (String) jsonObject.get("profession"), (Long) jsonObject.get("age"),(Boolean)jsonObject.get("canFly"));
        if(superHero.getCanFly()==true)
        {
            superHero.setCanFly(false);
        }
        response=given().body(superHero).post(EndPoints.userEndpoint)
                .then()
                .statusCode(201).extract().response();
        responseHero=objectMapper.readValue(response.asString(),SuperHeros.class);

    }


    @And("updating profession")
    public void updatingProfession() throws JsonProcessingException {
        jsonObject=(JSONObject)userData.get("updateUser");
        superHero.setProfession((String) jsonObject.get("profession"));
        response=given().body(superHero).when().put(EndPoints.userEndpoint+"/"+responseHero.getId())
                .then().statusCode(200).extract().response();
        responseHero1=objectMapper.readValue(response.asString(),SuperHeros.class);
    }

    @Then("profession must be updated")
    public void professionMustBeUpdated() {
        Assert.assertEquals(responseHero.getId(),responseHero1.getId());
        Assert.assertEquals(superHero.getProfession(),responseHero1.getProfession());
    }

    @And("updating age")
    public void updatingAge() throws JsonProcessingException {
        jsonObject=(JSONObject)userData.get("updateUser");
        superHero.setAge((Long) jsonObject.get("age"));
        response=given().body(superHero).when().put(EndPoints.userEndpoint+"/"+responseHero.getId())
                .then().statusCode(200).extract().response();
        responseHero1=objectMapper.readValue(response.asString(),SuperHeros.class);

    }

    @Then("age must be updated")
    public void ageMustBeUpdated() {
        Assert.assertEquals(responseHero.getId(),responseHero1.getId());
        Assert.assertEquals(superHero.getAge(),responseHero1.getAge());
    }

    @And("updating name")
    public void updatingName() throws JsonProcessingException {
        jsonObject=(JSONObject)userData.get("updateUser");
        superHero.setName((String) jsonObject.get("name"));
        response=given().body(superHero).when().put(EndPoints.userEndpoint+"/"+responseHero.getId())
                .then().statusCode(200).extract().response();
        responseHero1=objectMapper.readValue(response.asString(),SuperHeros.class);
    }

    @Then("name must be updated")
    public void nameMustBeUpdated() {
        Assert.assertEquals(responseHero.getId(),responseHero1.getId());
        Assert.assertEquals(superHero.getName(),responseHero1.getName());
    }

    @And("updating super name")
    public void updatingSuperName() throws JsonProcessingException {
        jsonObject=(JSONObject)userData.get("updateUser");
        superHero.setName((String) jsonObject.get("superName"));
        response=given().body(superHero).when().put(EndPoints.userEndpoint+"/"+responseHero.getId())
                .then().statusCode(200).extract().response();
        responseHero1=objectMapper.readValue(response.asString(),SuperHeros.class);
    }

    @Then("super name must be updated")
    public void superNameMustBeUpdated() {
        Assert.assertEquals(responseHero.getId(),responseHero1.getId());
        Assert.assertEquals(superHero.getName(),responseHero1.getName());
    }

    @And("updating canFly")
    public void updatingCanFly() throws JsonProcessingException {
        jsonObject=(JSONObject)userData.get("updateUser");
        superHero.setCanFly((Boolean) jsonObject.get("canFly"));
        response=given().body(superHero).when().put(EndPoints.userEndpoint+"/"+responseHero.getId())
                .then().statusCode(200).extract().response();
        responseHero1=objectMapper.readValue(response.asString(),SuperHeros.class);

    }

    @Then("canFly must be updated")
    public void canflyMustBeUpdated() {
        Assert.assertEquals(responseHero.getId(),responseHero1.getId());
        Assert.assertEquals(superHero.getName(),responseHero1.getName());

    }

    @Then("List of user must be displayed")
    public void listOfUserMustBeDisplayed() {
        given().when().get(EndPoints.userEndpoint).then().statusCode(200).extract().response();
    }

    @Then("user with particular ID must be displayed")
    public void userWithParticularIDMustBeDisplayed() {
        given().when().get(EndPoints.userEndpoint+"/"+responseHero.getId()).then()
                .statusCode(200).extract().response();
    }

    @Then("Deleting user")
    public void deletingUser() {
        given().when().delete(EndPoints.userEndpoint+"/"+responseHero.getId()).
                then().statusCode(200).extract().response();
    }

    @Then("Deleting Unknown user")
    public void deletingUnknownUser() {
        given().when().delete(EndPoints.userEndpoint+"/"+25).
                then().statusCode(200).extract().response();
    }
}
