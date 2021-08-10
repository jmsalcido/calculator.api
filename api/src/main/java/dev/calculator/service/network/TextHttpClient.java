package dev.calculator.service.network;

import lombok.Builder;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Builder
public class TextHttpClient {

    private final String URL;

    public HttpResponse<String> send() {
        try {
            return send(buildHttpRequest());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> send(HttpRequest httpRequest) {
        try {
            return sendRequest(httpRequest);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest httpRequest) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build();

        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    private HttpRequest buildHttpRequest() throws URISyntaxException {
        return HttpRequest.newBuilder(new URI(URL))
                .GET()
                .build();
    }

}
