package de.kgrupp.rssupdateservice;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log
public class ScheduledCalls {

    @Value("${de.kgrupp.rss.update.service.url}")
    private String url;

    @Scheduled(fixedRate = 3_600_000)
    public void triggerUpdate() throws UnirestException {
        log.info(() -> "Call url: " +  url);
        HttpResponse<String> string = Unirest.get(url).asString();
        log.info(() -> "Response: " + string.getBody());
    }
}
