package pro.sky.APISwaggerPostman.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class InfoController {
    Logger logger = LoggerFactory.getLogger(InfoController.class);
    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/port")
    public int getPortNumber() {
        return serverPort;
    }

    @GetMapping("/sumMillionWithParallel")
    public int sumMillionByParallel() {
        long time = System.currentTimeMillis();

        long sum = Stream.iterate(1, a -> a + 1)
                .limit(1000000)
                .parallel()
                .reduce(0, (a, b) -> a + b);

        time = System.currentTimeMillis() - time;
        System.out.printf("time %d \n", time);
        return (int) time;
    }
    //C parallel ухудшилось примерно в 2 раза
    @GetMapping("/sumMillion")
    public int sumMillion() {
        long time = System.currentTimeMillis();

        long sum = Stream.iterate(1, a -> a + 1)
                .limit(1000000)
                .reduce(0, (a, b) -> a + b);

        time = System.currentTimeMillis() - time;
        System.out.printf("time %d \n", time);
        return (int) time;
    }
}
