package com.icarlosalbertojr.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
public class CarsConsumerContractTest {

    @Pact(provider = "CarProvider", consumer = "CarConsumer")
    public V4Pact shouldReturnCars(PactDslWithProvider provider) {
        var body = PactDslJsonArray.arrayMinLike(1)
                .integerType("id")
                .stringType("brand")
                .stringType("model")
                .integerType("release_year")
                .closeObject();


        return provider
                .given("has cars")
                .uponReceiving("CarProvider get all cars")
                    .path("/cars")
                    .method("GET")
                .willRespondWith()
                    .status(200)
                    .body(body)
                    .headers(Map.of("Content-Type", "application/json"))
                .toPact(V4Pact.class);
    }

    @Pact(provider = "CarProvider", consumer = "CarConsumer")
    public V4Pact shouldReturnNoCars(PactDslWithProvider provider) {
               return provider
                .given("has not cars")
                .uponReceiving("CarProvider get all cars")
                    .path("/cars")
                    .method("GET")
                .willRespondWith()
                    .status(200)
                    .body(new PactDslJsonArray())
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "shouldReturnCars")
    public void shouldReturnCarsTest(MockServer mockServer) {
        var response = new RestTemplate().getForEntity(mockServer.getUrl() + "/cars", Car[].class);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertInstanceOf(Car[].class, response.getBody());
        Assertions.assertTrue(response.getBody().length > 0);
    }

    @Test
    @PactTestFor(pactMethod = "shouldReturnNoCars")
    public void shouldReturnNoCarsTest(MockServer mockServer) {
        var response = new RestTemplate().getForEntity(mockServer.getUrl() + "/cars", Car[].class);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertInstanceOf(Car[].class, response.getBody());
        Assertions.assertTrue(response.getBody().length == 0);
    }

    public record Car (Integer id, String brand, String model, Integer release_year){}

}
