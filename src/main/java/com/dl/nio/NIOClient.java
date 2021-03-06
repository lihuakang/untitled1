package com.dl.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws IOException {
        //得到一个网络通道
        SocketChannel socketChannel=SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务器端ip和端口
        InetSocketAddress inetSocketAddress=new InetSocketAddress("127.0.0.1",6666);
        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)){

            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要事件，客户端不会苏泽，可以做其他工作");
            }
        }

        //如果连接成功，就发送数据
        String str="你好，大花";
        ByteBuffer buffer=ByteBuffer.wrap(str.getBytes());
        //发送数据
        socketChannel.write(buffer);
        System.in.read();
    }
}
