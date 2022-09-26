package org.example.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * ClassName: Client
 * PackageName:org.example.NIO
 * Description:
 * date: 2022/4/9 17:12
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel open = SocketChannel.open();
        open.connect(new InetSocketAddress("localhost",8080));
        open.write(Charset.defaultCharset().encode("hhhhhhhhhhhhhhhhh\nsasasa\nss\n"));
        System.in.read();
    }
}
