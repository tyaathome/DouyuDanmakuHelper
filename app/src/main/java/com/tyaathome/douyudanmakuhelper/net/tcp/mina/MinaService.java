package com.tyaathome.douyudanmakuhelper.net.tcp.mina;

import android.text.TextUtils;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

/**
 * Created by tyaathome on 2018/06/27.
 */
public class MinaService {

    private ClientHandler clientHandler;
    private NioSocketConnector socketConnector;
    private ConnectFuture connectFuture;
    private IoSession session;
    private String heartBeatMessage;

    private static final long TIMEOUT_INTERVAL = 10 * 1000;
    //30秒后超时
    private static final int IDELTIMEOUT = 10;
    //15秒发送一次心跳包
    private static final int HEARTBEATRATE = 10;
    private static final String SERVICE_ADDRESS = "openbarrage.douyutv.com";
    private static final int SERVICE_PORT = 8601;

    public MinaService() {
        init();
    }

    private void init() {
        if(clientHandler == null) {
            clientHandler = new ClientHandler();
        }
        if(socketConnector == null) {
            socketConnector = new NioSocketConnector();
        }
    }

    public void connect() {
        socketConnector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ClientCodecFactory()));
        socketConnector.getSessionConfig().setReadBufferSize(1024);
        socketConnector.setConnectTimeoutMillis(10 * 1000);
        socketConnector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, IDELTIMEOUT);
        if(!TextUtils.isEmpty(heartBeatMessage)) {
            KeepAliveFilter heartBeat = new KeepAliveFilter(new KeepAliveMessageFactoryImpl(""),
                    IdleStatus.BOTH_IDLE, KeepAliveRequestTimeoutHandler.CLOSE, 10, 60);
            heartBeat.setForwardEvent(true);
            heartBeat.setRequestInterval(HEARTBEATRATE);
            socketConnector.getFilterChain().addLast("heartbeat", heartBeat);
        }

        socketConnector.setHandler(clientHandler);
        connectFuture = socketConnector.connect(new InetSocketAddress(SERVICE_ADDRESS, SERVICE_PORT));
        connectFuture.awaitUninterruptibly();
        session = connectFuture.getSession();
        if(session != null && session.isConnected()) {
            System.out.println("连接成功！");
        } else {
            System.out.println("连接失败！");
        }
    }

    public void disconnect() {
        if (session != null) {
            session.closeNow();
            session = null;
        }
        if (connectFuture != null && connectFuture.isConnected()) {
            connectFuture.cancel();
            connectFuture = null;
        }
        if (socketConnector != null && !socketConnector.isDisposed()) {
            //清空里面注册的所以过滤器
            socketConnector.getFilterChain().clear();
            socketConnector.dispose();
            socketConnector = null;
        }
    }

    public void send(String message) {
        if(session != null && session.isConnected()) {
            session.write(message);
        }
    }

    public void setHeartBeat(String message) {
        heartBeatMessage = message;
    }

}
