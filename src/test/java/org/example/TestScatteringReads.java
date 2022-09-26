package org.example;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ClassName: TestScatteringReads
 * PackageName:org.example
 * Description:
 * date: 2022/4/9 15:33
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class TestScatteringReads {
    public static void main(String[] args) {
        try (FileChannel rw = new RandomAccessFile("data.txt", "rw").getChannel()) {
            ByteBuffer a = ByteBuffer.allocate(3);
            ByteBuffer b = ByteBuffer.allocate(3);
            ByteBuffer c = ByteBuffer.allocate(5);
            rw.read(new ByteBuffer[]{a,b,c});
            a.flip();b.flip();c.flip();
            byte[] array = a.array();
            byte[] array1 = b.array();
            byte[] array2 = c.array();
            for (byte b1 : array) {
                System.out.println(((char) b1));
            }
            for (byte b1 : array1) {
                System.out.println(((char) b1));
            }
            for (byte b1 : array2) {
                System.out.println(((char) b1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        };
    }
}
