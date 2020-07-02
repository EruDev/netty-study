package com.eru.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * Created by eru on 2020/7/2.
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // HttpServerCodec 是Netty提供的编/解码器
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
        pipeline.addLast("MyTestHttpServerHandler", new TestServerHandler());
    }
}
