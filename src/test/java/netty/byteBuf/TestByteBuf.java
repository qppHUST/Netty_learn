package netty.byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.charset.StandardCharsets;

/**
 * ClassName: TestByteBuf
 * PackageName:netty.byteBuf
 * Description:
 * date: 2022/4/15 15:19
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class TestByteBuf {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeBytes("heiheihei".getBytes(StandardCharsets.UTF_8));
        buf.slice();
        buf.release();
    }
}
