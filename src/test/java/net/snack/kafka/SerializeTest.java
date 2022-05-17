package net.snack.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class SerializeTest {

    @Test
    public void testString() {
        String s = "中";
        byte[] bytes = s.getBytes(StandardCharsets.ISO_8859_1);
        for (byte b : bytes) {
            System.out.println((char) b + "->" + (int) b);
        }
    }

    @Test
    public void testChar() {
        char c = '中';
        System.out.println((int) c);
        System.out.println(0x4E2D);

        byte[] bytes = getBytes(new char[]{c});
        for (byte b : bytes) {
            System.out.println((char) b + "->" + (int) b);
        }
    }

    private byte[] getBytes(char[] chars) {
        Charset cs = StandardCharsets.UTF_8;
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }

    @Test
    public void convertUTF8() {
        System.out.println(0x800);
        System.out.println(0xFFFF);
    }

    @Test
    public void testConvert() {
        String s = "中";
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        for (byte b : bytes) {
            System.out.print((int) b + " ");
        }
        System.out.println("0x4E2D");
        System.out.println(0x4E2D);
        System.out.println(Integer.toBinaryString(0x4E2D));
    }

    @Test
    public void testSerialize() throws IOException {
        String path = "/Users/dingmingxuan/IdeaProjects/kafka-demo/src/test/java/net/snack/kafka/";
        User user = new User();
        user.setName("Jack");
        user.setAge(20);
        user.setBirth(LocalDate.of(1990, 9, 14));
        user.setBirthTime(LocalDateTime.now());
        user.setEntryDate(new Date());
        user.setSalary(5000d);
        byte[] serialize = SerializationUtils.serialize(user);
        IOUtils.write(serialize,
                new FileOutputStream(path + "user.obj"));

        FileWriter writer = new FileWriter(path + "user.txt");
        IOUtils.write(serialize,
                writer,
                StandardCharsets.UTF_8);
        writer.flush();

        writer = new FileWriter(path + "user.json");
        String s = new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .writeValueAsString(user);
        IOUtils.write(s, writer);
        writer.flush();
    }

    @Test
    public void testDeserialize() throws IOException {
        String path = "/Users/dingmingxuan/IdeaProjects/kafka-demo/src/test/java/net/snack/kafka/";
        Object deserialize = SerializationUtils.deserialize(new FileInputStream(path + "user.obj"));
        System.out.println(deserialize);
        User user = new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .readValue(new File(path + "user.json"), User.class);
        System.out.println(user);
    }

    @Test
    public void testJdkSerialize() throws IOException, ClassNotFoundException {
        String path = "/Users/dingmingxuan/IdeaProjects/kafka-demo/src/test/java/net/snack/kafka/";
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path + "user.ser"));
        User user = new User();
        user.setName("Jack");
        user.setAge(20);
        user.setBirth(LocalDate.of(1990, 9, 14));
        user.setBirthTime(LocalDateTime.now());
        user.setEntryDate(new Date());
        user.setSalary(5000d);
        outputStream.writeObject(user);
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path + "user.ser"));
        Object o = inputStream.readObject();
        System.out.println(o);
    }

}
