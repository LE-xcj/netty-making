package com.xc.service;

import com.alibaba.fastjson.JSON;
import com.xc.constant.ConstantValue;
import com.xc.pojo.ChatProtocol;
import com.xc.pojo.dto.MessageDTO;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @author chujian
 * @ClassName ClientHandler
 * @Description 功能描述
 * @date 2019/5/14 19:11
 */
public class ClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        try {

            ChatProtocol protocol = (ChatProtocol) msg;
            String data = new String(protocol.getContent());

            MessageDTO dto = JSON.parseObject(data, MessageDTO.class);
            String source = dto.getSource();
            String content = dto.getContent();


            String pre = null;

            if (dto.getType() == ConstantValue.SINGLECHAT) {
                pre = "单聊" + source;
            } else {
                pre = "群聊" + source;
            }

            System.out.println(pre  + " : " + content);

        } finally {

            ReferenceCountUtil.release(msg);

        }



    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("断开连接.....");
        ctx.close();
    }
}
    