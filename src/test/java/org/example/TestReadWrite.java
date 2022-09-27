package org.example;

import java.nio.ByteBuffer;

/**
 * ClassName: TestReadWrite
 * PackageName:org.example
 * Description:
 * date: 2022/4/9 13:49
 *
 * @author:  邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class TestReadWrite {
    public static void main(String[] args) {
        extracted();
    }




    private static void extracted() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put((byte) 0x61);
        System.out.println(byteBuffer.position());
        byteBuffer.put((byte) 0x62);
        System.out.println(byteBuffer.position());
        byteBuffer.put((byte) 0x63);
        byteBuffer.flip();
        byte[] array = byteBuffer.array();
        for (byte b : array) {
            System.out.println(b);
        }
    }
}
