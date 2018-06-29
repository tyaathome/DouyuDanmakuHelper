package com.tyaathome.douyudanmakuhelper.net.tcp.mina;

import android.util.Log;

import com.tyaathome.douyudanmakuhelper.utils.MessageUtils;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.Map;

import io.reactivex.ObservableEmitter;

/**
 * Created by tyaathome on 2018/06/27.
 */
public class ClientHandler extends IoHandlerAdapter {

    private ObservableEmitter<Map<String, String>> emitter;

    ClientHandler(ObservableEmitter<Map<String, String>> emitter) {
        this.emitter = emitter;
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        IoBuffer buffer = (IoBuffer) message;
        byte[] bytes = buffer.array();
        // messageLength = 总长度 - 头部8位 - 尾部'\0'一位
        int messageLength = bytes.length - 8 -1;
        byte[] messageBytes = new byte[messageLength];
        System.arraycopy(bytes, 8, messageBytes, 0, messageLength);
        String result = new String(messageBytes);
        Log.e("messageReceived", result);
        if(emitter != null && !emitter.isDisposed()) {
            emitter.onNext(MessageUtils.receive(result));
        }
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        if(emitter != null && !emitter.isDisposed()) {
            emitter.onError(cause);
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }


}
