package com.eru.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by eru on 2020/7/4.
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            String action = null;
            switch (event.state()){
                case WRITER_IDLE:
                    action = "写空闲";
                    break;
                case READER_IDLE:
                    action = "读空闲";
                    break;
                case ALL_IDLE:
                    action = "读写空闲";
                    break;
            }
            System.err.println(action);
        }
    }
}
