package dev.calculator.service.operations;

import dev.calculator.model.ServiceEntry;
import dev.calculator.model.ServiceType;
import dev.calculator.service.network.TextHttpClient;
import org.springframework.stereotype.Component;

@Component
public class RandomStringOperation implements Operation {

    public static final String URL = "https://www.random.org/strings/?num=1&" +
            "len=16&" +
            "digits=on&" +
            "upperalpha=on&" +
            "loweralpha=on&" +
            "unique=on&" +
            "format=plain&" +
            "rnd=new";

    @Override
    public String execute(ServiceEntry serviceEntry) {
        return getRandomString();
    }

    private String getRandomString() {
        return buildHttpClient().send().body().trim();
    }

    TextHttpClient buildHttpClient() {
        return TextHttpClient.builder()
                .URL(URL)
                .build();
    }

    @Override
    public ServiceType getServiceType() {
        return ServiceType.RANDOM_STRING;
    }
}
