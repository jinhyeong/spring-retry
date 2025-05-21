package com.example.springretry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class MyRetryableService {

    private static final Logger logger = LoggerFactory.getLogger(MyRetryableService.class);
    private int counter = 0;

    // This method will be retried 3 times with a 1-second delay between retries.
    // It will throw a RuntimeException for the first 2 calls.
    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    public String serviceMethod(String param) {
        counter++;
        logger.info("MyRetryableService.serviceMethod called at {} with param: {}, attempt: {}", LocalTime.now(), param, counter);

        if (counter < 3) { // Fail for the first 2 attempts
            logger.warn("Simulating failure for attempt {}", counter);
            throw new RuntimeException("Simulated temporary error");
        }

        logger.info("Succeeded on attempt {}", counter);
        return "Successfully processed: " + param + " on attempt " + counter;
    }

    // This method will be called if all retry attempts for serviceMethod fail.
    @Recover
    public String recover(RuntimeException e, String param) {
        logger.error("All retry attempts failed for param: {}. Recovering...", param, e);
        return "Recovered from failure while processing: " + param + ". Error: " + e.getMessage();
    }

    // Helper method to reset the counter for testing purposes
    public void resetCounter() {
        this.counter = 0;
    }

    public int getCounter() {
        return this.counter;
    }
}
