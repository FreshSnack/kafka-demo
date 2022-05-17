package net.snack.kafka;

import org.openjdk.jmh.annotations.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 0)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JMHTest {

    @Benchmark
    public void measureName() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("s");
    }

    @Benchmark
//    @BenchmarkMode(Mode.All)
    public void testJdkSerializable() throws Exception {
        String path = "/Users/dingmingxuan/IdeaProjects/kafka-demo/src/test/java/net/snack/kafka/test";
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path + UUID.randomUUID() + ".obj"));
        User user = new User();
        user.setName("Jack");
        user.setAge(20);
        user.setBirth(LocalDate.of(1990, 9, 14));
        user.setBirthTime(LocalDateTime.now());
        user.setEntryDate(new Date());
        user.setSalary(5000d);
        outputStream.writeObject(user);
//        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path + "user.ser"));
    }
}
