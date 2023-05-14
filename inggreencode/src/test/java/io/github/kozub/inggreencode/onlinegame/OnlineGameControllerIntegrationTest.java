package io.github.kozub.inggreencode.onlinegame;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.github.kozub.inggreencode.Utils.loadFile;
import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static net.javacrumbs.jsonunit.core.util.ResourceUtils.resource;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class OnlineGameControllerIntegrationTest {

    private static Stream<Arguments> testingArgs() {
        return Stream.of(
                Arguments.of("onlinegame/example_request.json", "onlinegame/example_response.json", "provided-testcase1")
//                Arguments.of("onlinegame/request-1000.json", "onlinegame/example_response.json", "provided-testcase2")
        );
    }

    @DisplayName("Should send post request and check expected response.")
    @ParameterizedTest(name = "{index} - {2}")
    @MethodSource("testingArgs")
    void shouldTestTransactionEndpoint(String reqFile, String resFile, String desc) throws Exception {
        var request = loadFile(reqFile);

        given()
                .header("Content-Type", "application/json")
                .body(request)
                .when().post("/onlinegame/calculate")
                .then()
                .statusCode(200)
                .body(is(jsonEquals(resource(resFile))));
    }
}