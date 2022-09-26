package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Scanner;

/**
 * ClassName: HelloClient
 * PackageName:netty
 * Description:
 * date: 2022/4/13 16:59
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        //初始化器
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        //对信息进行编码
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                      }
                })
                .connect("localhost", 8080);
        Channel channel = channelFuture.sync().channel();
        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入");
            String input = null;
            while (!(input = scanner.nextLine()).equals("exit")){
                System.out.println("what your input is "+input);
                channel.writeAndFlush(input);
                System.out.println("done");
            }
            System.out.println("byebye");
            channel.close();
        },"thread-input").start();

        ChannelFuture channelFuture1 = channel.closeFuture();

        channelFuture1.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("当前线程是:"+Thread.currentThread().getName());
                group.shutdownGracefully();
            }
        });
    }
}
