package io.github.kozub.inggreencode.transactions;

import io.github.kozub.inggreencode.Utils;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.Matcher;
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
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TransactionsControllerIntegrationTest {

    private static Stream<Arguments> testingPairs() {
        return Stream.of(
                Arguments.of("transactions/0_request.json", "transactions/0_response.json", "provided-testcase"),
                Arguments.of("transactions/test-req1.json", "transactions/test-res1.json", "10 transactions"),
                Arguments.of("transactions/test-req-1000.json", "transactions/test-res-1000.json", "1000 transactions"),
                Arguments.of("transactions/test-req-10_000.json", "transactions/test-res-10_000.json", "10_000 transactions"),
                Arguments.of("transactions/test-req-100_000.json", "transactions/test-res-100_000.json", "100_000 transactions")
        );
    }

    @DisplayName("Should send post request and check expected response.")
    @ParameterizedTest(name = "{index} - {2}")
    @MethodSource("testingPairs")
    void shouldTestTransactionEndpoint(String reqFile, String resFile, String desc) throws Exception {
        var request = loadFile(reqFile);

        given()
                .header("Content-Type", "application/json")
                .body(request)
                .when().post("/transactions/report")
                .then()
                .statusCode(200)
                .body(is(jsonEquals(resource(resFile))));
    }
}