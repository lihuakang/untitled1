package com.dl.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class GroupChatClient1 {
    //定义属性
    private final String HOST="127.0.0.1";
    private final int PORT=6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;
    //构造器   初始化
    public GroupChatClient1() throws IOException {
        selector=Selector.open();
        //连接服务器
        socketChannel=SocketChannel.open(new InetSocketAddress("127.0.0.1",PORT));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //将channel注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到username
        username=socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username+" si ok....");
    }

    //想服务器发送消息
    public void sendInfo(String info){
        info=username+" 说： "+info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //读取从服务器端回复的消息
    public void readInfo(){
        try {
            int readChannels=selector.select();
            if (readChannels>0){
                Iterator<SelectionKey> iterator=selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key=iterator.next();
                    if (key.isReadable()){
                        //得到相关的通道
                        SocketChannel sc= (SocketChannel) key.channel();
                        //得到一个Buffer
                        ByteBuffer buffer=ByteBuffer.allocate(1024);
                        //读取
                        sc.read(buffer);
                        //把读到的缓冲区的数据转成字符串
                        String msg=new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                }
                iterator.remove();
            }else {
                System.out.println("没有可用的通道");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient1 chatClient=new GroupChatClient1();
        //启动一个线程，每个3秒，读取从服务器发送数据
        new Thread(){
            public void run(){
                while (true){
                    chatClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //发送数据给服务器
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s=scanner.nextLine();
            chatClient.sendInfo(s);
        }
    }
}