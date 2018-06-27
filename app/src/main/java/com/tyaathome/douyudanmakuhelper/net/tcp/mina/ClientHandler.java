package com.tyaathome.douyudanmakuhelper.net.tcp.mina;

import android.util.Log;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by tyaathome on 2018/06/27.
 */
public class ClientHandler extends IoHandlerAdapter {

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        IoBuffer buffer = (IoBuffer) message;
        byte[] bytes = buffer.array();
        String result = new String(bytes);
        Log.e("ClientHandler", result);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
    }
}
