package org.example;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ClassName: TestBuffer
 * PackageName:org.example
 * Description:
 * date: 2022/4/9 13:10
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@Slf4j
public class TestBuffer {

    @Test
    public void a(){
        System.out.println("aaa");
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            //System.out.println("Sasasa");
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            while(true) {
                int read = channel.read(byteBuffer);
                log.info("读取到的字节数为：{}",read);
                if (read == -1){
                    break;
                }
                System.out.println(read);
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    byte b = byteBuffer.get();
                    System.out.println(((char) b));
                }
                byteBuffer.clear();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
