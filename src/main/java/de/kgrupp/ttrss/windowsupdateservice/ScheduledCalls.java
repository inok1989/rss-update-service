package de.kgrupp.ttrss.windowsupdateservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import de.kgrupp.inoksjavautils.transform.JsonUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;

@Component
@Log
public class ScheduledCalls {

    @Value("${de.kgrupp.ttrss.windowsupdateservice.url}")
    private String url;

    @Autowired
    public ScheduledCalls() {
        Unirest.setTimeouts(10000, 10000);
    }

    @Scheduled(fixedRate = 3_600_000)
    public void triggerUpdate() throws InterruptedException, ExecutionException {
        getResult().ifPresent(result -> {
            log.info(result.toString());
            if (0 < result.getNumUpdated()) {
                try {
                    triggerUpdate();
                } catch (ExecutionException e) {
                    log.log(Level.SEVERE, e, () -> "Failed with exception");
                } catch (InterruptedException e) {
                    log.log(Level.SEVERE, e, () -> "Interrupted execution");
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private Optional<Result> getResult() throws InterruptedException, ExecutionException {
        Optional<Result> result = Optional.empty();
        log.info(() -> "Call url: " + url);
        Future<HttpResponse<String>> stringFuture = Unirest.get(url).asStringAsync();
        for (long i = 0; i < 100; i++) {
            Thread.sleep(100);
            final long finalI = i;
            log.info(() -> "waiting " + (finalI * 100D) / 1000 + " seconds");
        }
        if (stringFuture.isDone()) {
            String response = stringFuture.get().getBody();
            result = Optional.ofNullable(JsonUtils.convertToObject(response, Result.class).orElseGet(() -> {
                log.severe(() -> "Conversion to Result failed.");
                return null;
            }));
        } else if (stringFuture.isCancelled()) {
            log.severe(() -> "Call is cancelled");
        } else {
            log.severe(() -> "Invalid state.");
        }
        return result;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
class Result {
    private String message;
    @JsonProperty("num_updated")
    private long numUpdated;
}
