package net.snack.kafka;

import org.junit.Test;

import java.time.Duration;

public class KafkaTest {

    @Test
    public void testGetPart() {
        long l = Duration.ofHours(1).toMillis();
        System.out.println(l);
    }
}
