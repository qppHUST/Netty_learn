package org.example.NIO;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * ClassName: TestNio
 * PackageName:org.example.NIO
 * Description:
 * date: 2022/4/9 17:01
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@Slf4j
public class Server {
    private static void split(ByteBuffer source){
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if(source.get(i) == '\n'){
                int length = i+1-source.position();
                System.out.println(source.position());
                System.out.println(length);
                ByteBuffer target = ByteBuffer.allocate(length);
                for(int j = 0;j<length;j++){
                    target.put(source.get());
                }
                printBuffer(target);
            }
        }
        source.compact();
    }
    private static void printBuffer(ByteBuffer buffer){
        String s = new String(buffer.array());
        System.out.println("接收到的信息为:"+s);
    }
    public static void main(String[] args) throws IOException {
        //先设置一个selector
        Selector selector = Selector.open();
        //声明缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(16);
        //声明服务端管道
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定服务端到8080端口
        ssc.bind(new InetSocketAddress(8080));
        //打开非阻塞模式,否则会在等待连接时发生阻塞
        ssc.configureBlocking(false);
        //绑定服务器到selector,交给selector管理
        SelectionKey registerKey = ssc.register(selector, 0, null);
        registerKey.interestOps(SelectionKey.OP_ACCEPT);
        System.out.println("selector上收到绑定的管道:"+registerKey);

        while (true){
            //这是一个阻塞方法
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey nextKey = iterator.next();
                iterator.remove();
                if(nextKey.isAcceptable()){
                    //检测到发生的事件是一个连接事件,强制类型转换的原因是连接事件一定是服务器引起的
                    ServerSocketChannel channel = (ServerSocketChannel) nextKey.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey register = sc.register(selector, 0, buffer);
                    register.interestOps(SelectionKey.OP_READ);
                    System.out.println("服务器:"+nextKey+"监听到了:"+sc+"连接到服务器");
                }else if(nextKey.isReadable()){
                    //是一个读写事件
                    try {
                        SocketChannel channel = (SocketChannel) nextKey.channel();
                        //从key中得到其附件
                        ByteBuffer attachment = (ByteBuffer) nextKey.attachment();
                        int read = channel.read(attachment);
                        if(read == -1){
                            //没读到东西，把这个key从keySet中删除
                            nextKey.cancel();
                        }else {
                            split(attachment);
                            if(attachment.position() == attachment.limit()){
                                //缓冲区不够大,就扩容
                                ByteBuffer newByteBuffer = ByteBuffer.allocate(attachment.capacity()*2);
                                attachment.flip();
                                newByteBuffer.put(attachment);//把老的东西放到这个新的里面来
                                nextKey.attach(newByteBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        nextKey.cancel();
                    }
                }
            }

        }
    }
}
