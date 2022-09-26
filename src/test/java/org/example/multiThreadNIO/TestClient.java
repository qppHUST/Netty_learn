package org.example.multiThreadNIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * ClassName: TestClient
 * PackageName:org.example.multiThreadNIO
 * Description:
 * date: 2022/4/13 12:00
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class TestClient {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("localhost",8080));
        channel.write(Charset.defaultCharset().encode("0123"));
        System.in.read();
    }
}
