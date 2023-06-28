package com.icarlosalbertojr.provider;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.icarlosalbertojr.provider.models.Car;
import com.icarlosalbertojr.provider.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("CarProvider")
@PactBroker
public class CarProviderContractTest {

    @MockBean
    private CarRepository carRepository;

    @BeforeEach
    void before() {
        MockitoAnnotations.openMocks(this);
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    public void pactVerificationProvider(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("has cars")
    public void shouldReturnCars(){
        Mockito.when(carRepository.findAll()).thenReturn(List.of(
                new Car(1, "Fiat", "Uno", 2006)
        ));
    }

    @State("has not cars")
    public void hasNoCarsInteraction(){
        Mockito.when(carRepository.findAll()).thenReturn(List.of());
    }


}
