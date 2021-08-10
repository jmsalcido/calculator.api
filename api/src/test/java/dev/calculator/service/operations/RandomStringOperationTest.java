package dev.calculator.service.operations;

import dev.calculator.model.ServiceEntry;
import dev.calculator.service.network.TextHttpClient;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RandomStringOperationTest {

    @Test
    public void test__scenario() {
        @SuppressWarnings("unchecked")
        HttpResponse<String> httpResponseMock = mock(HttpResponse.class);
        when(httpResponseMock.body()).thenReturn("random string from internet");

        TextHttpClient textHttpClientMock = mock(TextHttpClient.class);
        when(textHttpClientMock.send()).thenReturn(httpResponseMock);

        ServiceEntry serviceEntry = ServiceEntry.builder()
                .build();

        var subject = new RandomStringOperation();
        RandomStringOperation spiedSubject = spy(subject);

        when(spiedSubject.buildHttpClient()).thenReturn(textHttpClientMock);

        String expected = spiedSubject.execute(serviceEntry);
        assertEquals(expected, "random string from internet");
    }

}