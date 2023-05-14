package io.github.kozub.inggreencode.atmservice;

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
class AtmServiceControllerIntegrationTest {

    private static Stream<Arguments> testingArgs() {
        return Stream.of(
                Arguments.of("atmservice/example_1_request.json", "atmservice/example_1_response.json", "provided-testcase1"),
                Arguments.of("atmservice/example_2_request.json", "atmservice/example_2_response.json", "provided-testcase2"),
                Arguments.of("atmservice/test-req-100.json", "atmservice/test-res-100.json", "generated-100"),
                Arguments.of("atmservice/test-req-1000.json", "atmservice/test-res-1000.json", "generated-1000"),
                Arguments.of("atmservice/test-req-10_000.json", "atmservice/test-res-10_000.json", "generated-10_000")
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
                .when().post("/atms/calculateOrder")
                .then()
                .statusCode(200)
                .body(is(jsonEquals(resource(resFile))));
    }
}