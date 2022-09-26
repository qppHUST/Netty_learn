package netty.test;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ClassName: TestByteBuffer
 * PackageName:netty.test
 * Description:
 * date: 2022/9/18 20:15
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@Slf4j(topic = "LoggingHandler")
public class TestByteBuffer {
    public static void main(String[] args) {
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            System.out.println(111);
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while(true){
                int read = channel.read(buffer);
                log.debug("长度是:{}",read);
                if(read == -1){
                    break;
                }
                buffer.flip();
                while(buffer.hasRemaining()){
                    char c = (char) buffer.get();
                    log.debug("数据是{}",c);
                }
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
