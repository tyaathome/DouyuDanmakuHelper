package com.tyaathome.douyudanmakuhelper.net.tcp.mina;

import com.tyaathome.douyudanmakuhelper.utils.MessageUtils;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.ByteOrder;

/**
 * Created by tyaathome on 2018/06/27.
 */
public class ClientCodecFactory implements ProtocolCodecFactory {
    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return new ProtocolEncoderAdapter() {
            @Override
            public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
                String result = (String) message;
                byte[] bytes = MessageUtils.sendMessageContent(result);
                IoBuffer buffer = IoBuffer.wrap(bytes);
                out.write(buffer);
                out.flush();
            }
        };
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
//        return new ProtocolDecoderAdapter() {
//            @Override
//            public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
//                byte[] bytes = in.array();
//                String string = new String(bytes);
//                int remaining = in.remaining();
//                if(remaining < 4) {
//                    return false;
//                }
//                System.out.println(string);
//            }
//        };
        return new CumulativeProtocolDecoder() {
            @Override
            protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
                in.order(ByteOrder.LITTLE_ENDIAN);
                byte[] bytes = in.array();
                String string = new String(bytes);
                int remaining = in.remaining();
                boolean flag = in.hasRemaining();
                if(in.remaining() < 4) {
                    return false;
                }
                if(in.remaining() > 1) {
                    in.mark();
                    int len = in.getInt()+4;
                    System.out.println(len);
                    if(len > in.remaining()) {
                        in.reset();
                        return false;
                    } else {
                        in.reset();
//                        int messageLength = len-8;
//                        byte[] messageBytes = new byte[messageLength];
//                        System.arraycopy(bytes, 12, messageBytes, 0, messageLength);
//                        IoBuffer buffer = IoBuffer.wrap(messageBytes);
//                        out.write(buffer);
                        int sumLen = len;//总长 = 包头+包体
                        byte[] packArr = new byte[sumLen];
                        in.get(packArr, 0, sumLen);
                        IoBuffer buffer = IoBuffer.allocate(sumLen);
                        buffer.put(packArr);
                        buffer.flip();
                        out.write(buffer);
                        return in.remaining() > 0;
                    }
                }
                return false;
            }
        };
    }
}
