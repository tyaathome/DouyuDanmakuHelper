package com.tyaathome.douyudanmakuhelper.net.tcp;

import android.content.Context;
import android.util.Log;

import com.tyaathome.douyudanmakuhelper.impl.OnCall;
import com.tyaathome.douyudanmakuhelper.utils.MessageUtils;
import com.xuhao.android.libsocket.sdk.ConnectionInfo;
import com.xuhao.android.libsocket.sdk.OkSocket;
import com.xuhao.android.libsocket.sdk.OkSocketOptions;
import com.xuhao.android.libsocket.sdk.SocketActionAdapter;
import com.xuhao.android.libsocket.sdk.bean.ISendable;
import com.xuhao.android.libsocket.sdk.bean.OriginalData;
import com.xuhao.android.libsocket.sdk.connection.IConnectionManager;
import com.xuhao.android.libsocket.sdk.connection.interfacies.IAction;

import java.nio.ByteOrder;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SocketService {

    private static SocketService instance;
    private static final String address = "openbarrage.douyutv.com";
    private static final int port = 8601;
    private final ConnectionInfo connectionInfo = new ConnectionInfo(address, port);
    private IConnectionManager connectionManager;

    private SocketService() {

    }

    public static SocketService getInstance() {
        if (instance == null) {
            instance = new SocketService();
        }
        return instance;
    }

    private IConnectionManager create() {
        IConnectionManager manager = OkSocket.open(address, port);
        OkSocketOptions options = manager.getOption();
        OkSocketOptions.Builder builder = new OkSocketOptions.Builder(options);
        builder.setReadByteOrder(ByteOrder.LITTLE_ENDIAN);
        manager.option(builder.build());
        return manager;
    }

    public void connect(OnCall<ConnectionInfo> send, OnCall<MessageUtils.MessageBean> receive) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                String name = Thread.currentThread().getName();
                connectionManager = create();
                connectionManager.registerReceiver(new SocketActionAdapter() {
                    @Override
                    public void onSocketConnectionSuccess(Context context, ConnectionInfo info, String action) {
                        super.onSocketConnectionSuccess(context, info, action);
                        connectionManager = OkSocket.open(info);
                        if (info != null && connectionManager.isConnect()) {
                            Object[] params = {action, context, info};
                            emitter.onNext(params);
                        }
                    }

                    @Override
                    public void onSocketConnectionFailed(Context context, ConnectionInfo info, String action,
                                                         Exception e) {
                        super.onSocketConnectionFailed(context, info, action, e);
                        Object[] params = {action, context, info, e};
                        emitter.onNext(params);
                    }

                    @Override
                    public void onSocketReadResponse(Context context, ConnectionInfo info, String action,
                                                     OriginalData data) {
                        super.onSocketReadResponse(context, info, action, data);
                        Object[] params = {action, context, info, data};
                        emitter.onNext(params);
                    }

                    @Override
                    public void onSocketDisconnection(Context context, ConnectionInfo info, String action, Exception
                            e) {
                        super.onSocketDisconnection(context, info, action, e);
                        connectionManager = OkSocket.open(info);
                        Object[] params = {action, context, info, e};
                        emitter.onNext(params);
                        emitter.onComplete();
                    }
                });
                connectionManager.connect();
                //emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        String name = Thread.currentThread().getName();
                        if (o != null && o instanceof Object[]) {
                            Object[] params = (Object[]) o;
                            if (params.length > 0) {
                                if (params[0] instanceof String) {
                                    String action = (String) params[0];
                                    switch (action) {
                                        case IAction.ACTION_CONNECTION_SUCCESS:
                                            if(params.length > 2 && params[2] instanceof ConnectionInfo) {
                                                ConnectionInfo info = (ConnectionInfo) params[2];
                                                if (send != null) {
                                                    send.onCall(info);
                                                }
                                            }
                                            break;
                                        case IAction.ACTION_CONNECTION_FAILED:
                                            break;
                                        case IAction.ACTION_READ_COMPLETE:
                                            if (params.length > 3 && params[3] instanceof OriginalData) {
                                                OriginalData data = (OriginalData) params[3];
                                                byte[] result = byteMergerAll(data.getHeadBytes(), data
                                                        .getBodyBytes());
                                                MessageUtils.MessageBean info = MessageUtils.receive(result);
                                                if(info != null) {
                                                    String message = info.message;
                                                    Log.e("SocketService", message);
                                                    if(message.equals("error")) {
                                                        String str = new String(result);
                                                        Log.e("SocketService", "error : " + str);
                                                    }
                                                    if (receive != null) {
                                                        receive.onCall(info);
                                                    }
                                                }
                                            }
                                            break;
                                        case IAction.ACTION_DISCONNECTION:
                                            System.out.println("断开连接");
                                            if (params.length > 2 && params[2] instanceof ConnectionInfo) {
                                                ConnectionInfo info = (ConnectionInfo) params[2];
                                                boolean b = OkSocket.open(info).isDisconnecting();
                                                boolean b1 = OkSocket.open(info).isConnect();
                                            }
                                            break;
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void disconnect() {
        if (connectionManager != null) {
            connectionManager.disconnect();
            connectionManager = null;
        }
    }

    public void send(String message) {
        OkSocket.open(connectionInfo).send(new SendData(message));
    }

    public void send(ConnectionInfo connectionInfo, String message) {
        OkSocket.open(connectionInfo).send(new SendData(message));
    }

    public int byteArrayToInt(byte[] b) {
        return b[0] & 0xFF |
                (b[1] & 0xFF) << 8 |
                (b[2] & 0xFF) << 16 |
                (b[3] & 0xFF) << 24;

    }

    private byte[] byteMergerAll(byte[]... values) {
        int length_byte = 0;
        for (int i = 0; i < values.length; i++) {
            length_byte += values[i].length;
        }
        byte[] all_byte = new byte[length_byte];
        int countLength = 0;
        for (int i = 0; i < values.length; i++) {
            byte[] b = values[i];
            System.arraycopy(b, 0, all_byte, countLength, b.length);
            countLength += b.length;
        }
        return all_byte;
    }

    private class SendData implements ISendable {

        private String message;

        public SendData(String message) {
            this.message = message;
        }


        @Override
        public byte[] parse() {
            return MessageUtils.sendMessageContent(message);
        }
    }

}
