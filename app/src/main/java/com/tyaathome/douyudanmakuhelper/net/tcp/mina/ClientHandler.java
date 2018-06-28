package com.tyaathome.douyudanmakuhelper.net.tcp.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import io.reactivex.ObservableEmitter;

/**
 * Created by tyaathome on 2018/06/27.
 */
public class ClientHandler extends IoHandlerAdapter {

    private ObservableEmitter<String> emitter;

    public ClientHandler(ObservableEmitter<String> emitter) {
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
        int messageLength = bytes.length - 8;
        byte[] messageBytes = new byte[messageLength];
        System.arraycopy(bytes, 8, messageBytes, 0, messageLength);
        String result = new String(messageBytes);
        if(emitter != null && !emitter.isDisposed()) {
            emitter.onNext(result);
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
