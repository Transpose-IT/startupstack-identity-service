package dev.startupstack.tenants;

import static dev.startupstack.identityservice.Constants.TENANTS_URL;
import static dev.startupstack.identityservice.Constants.USERS_URL;
import static dev.startupstack.TestUtils.testUserAccessToken;
import static dev.startupstack.TestUtils.testAdminAccessToken;
import static dev.startupstack.TestUtils.invalidUserAccessToken;
import static dev.startupstack.TestUtils.testUserTenantID;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import dev.startupstack.TestUtils;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


/**
 * ObjectsTest
 */
@QuarkusTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class TenantKeycloakImplTest {

   

}