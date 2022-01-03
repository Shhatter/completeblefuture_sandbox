package com.example.completeblefuture;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
class CompleteblefutureSandboxApplicationTests {

    @Test
    void cocurrent() {

        ThreadPoolExecutor executor =
            (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            Thread.sleep(1000);
            log.info("lel 1 ");
            System.out.println(" 1");
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            log.info("lel 2 ");
            System.out.println(" 2");


            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            log.info("lel 3 ");
            System.out.println(" 3");

            return null;
        });

        assertEquals(2, executor.getPoolSize());
        assertEquals(1, executor.getQueue().size());
    }

}
