package org.example.AIO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * ClassName: TestAaio
 * PackageName:org.example.AIO
 * Description:
 * date: 2022/4/13 16:26
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class TestAaio {
    public static void main(String[] args) {
        try {
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("data.txt"), StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(16);
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {

                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
