package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * ClassName: HelloServer
 * PackageName:netty
 * Description:
 * date: 2022/4/13 16:41
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class HelloServer {
    public static void main(String[] args) {
        EventLoop eventLoop = new DefaultEventLoop();
        //服务器端启动器，负责组装组件
        new ServerBootstrap()
                //加入了一个组，即多个处理东西的帮手，比如包含了boss和worker
                .group(new NioEventLoopGroup())
                //Netty对原生的serverSocketChannel进行了一定的封装，改名为nio...
                //实际上选择了服务端的一种实现
                .channel(NioServerSocketChannel.class)
                //child就是worker的道理,boss用来建立连接，worker用来处理事务
                //负责添加对应事项的handler
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    //添加具体的handler
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        //我们在这里加了两个
                        //用于数据的解码
                        nioSocketChannel.pipeline().addLast(new StringDecoder());
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){ //自定义一个handler
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                //用于处理读事件的handler
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
