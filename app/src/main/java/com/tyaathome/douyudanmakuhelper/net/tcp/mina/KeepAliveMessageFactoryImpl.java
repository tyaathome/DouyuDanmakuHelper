package com.tyaathome.douyudanmakuhelper.net.tcp.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

/**
 * Created by tyaathome on 2018/06/28.
 */
public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory {

    private String heartBeatMessage = "mrkl";

    KeepAliveMessageFactoryImpl(String message) {
        //heartBeatMessage = message;
    }

    @Override
    public boolean isRequest(IoSession session, Object message) {
        if(message instanceof String) {
            String m = (String) message;
            return m.contains(heartBeatMessage);
        }
        return false;
    }

    @Override
    public boolean isResponse(IoSession session, Object message) {
        if(message instanceof String) {
            String m = (String) message;
            return m.contains(heartBeatMessage);
        }
        return false;
    }

    @Override
    public Object getRequest(IoSession session) {
        if(session != null && session.isConnected()) {
            session.write(heartBeatMessage);
        }
        return heartBeatMessage;
    }

    @Override
    public Object getResponse(IoSession session, Object request) {
        if(session != null && session.isConnected()) {
            session.write(heartBeatMessage);
        }
        return heartBeatMessage;
        //return null;
    }
}
