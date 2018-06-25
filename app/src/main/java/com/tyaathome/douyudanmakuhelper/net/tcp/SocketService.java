package com.tyaathome.douyudanmakuhelper.net.tcp;

import android.content.Context;

import com.tyaathome.douyudanmakuhelper.impl.OnCall;
import com.tyaathome.douyudanmakuhelper.utils.MessageUtils;
import com.xuhao.android.libsocket.sdk.ConnectionInfo;
import com.xuhao.android.libsocket.sdk.OkSocket;
import com.xuhao.android.libsocket.sdk.OkSocketOptions;
import com.xuhao.android.libsocket.sdk.SocketActionAdapter;
import com.xuhao.android.libsocket.sdk.bean.ISendable;
import com.xuhao.android.libsocket.sdk.bean.OriginalData;
import com.xuhao.android.libsocket.sdk.connection.IConnectionManager;
import com.xuhao.android.libsocket.utils.BytesUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
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

    private void create() {
        connectionManager = OkSocket.open(connectionInfo);
        OkSocketOptions options = connectionManager.getOption();
        OkSocketOptions.Builder builder = new OkSocketOptions.Builder(options);
        builder.setReadByteOrder(ByteOrder.LITTLE_ENDIAN);
//        builder.setHeaderProtocol(new IHeaderProtocol() {
//            @Override
//            public int getHeaderLength() {
//                return 4;
//            }
//
//            @Override
//            public int getBodyLength(byte[] header, ByteOrder byteOrder) {
//                String string = new String(header);
//                ByteBuffer byteBuffer = ByteBuffer.allocate(4);
//                byteBuffer.put(header, 0, 4);
//                byte[] bytes2 = byteBuffer.array();
//                int a = byteArrayToInt(bytes2);
//                return a;
//            }
//        });
        connectionManager.option(builder.build());

    }

    public void connect(OnCall<ConnectionInfo> send, OnCall<String> receive) {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap((Function<Object, ObservableSource<Integer>>) o -> Observable.create(emitter -> {
                    String name = Thread.currentThread().getName();
                    if (connectionManager == null) {
                        create();
                    }
                    emitter.onComplete();
                }))

                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        String name = Thread.currentThread().getName();
                        connectionManager.connect();
                        connectionManager.registerReceiver(new SocketActionAdapter() {
                            @Override
                            public void onSocketConnectionSuccess(Context context, ConnectionInfo info, String action) {
                                super.onSocketConnectionSuccess(context, info, action);
                                if(send != null) {
                                    send.onCall(info);
                                }
                            }

                            @Override
                            public void onSocketReadResponse(Context context, ConnectionInfo info, String action, OriginalData data) {
                                super.onSocketReadResponse(context, info, action, data);
                                byte[] bytes = data.getHeadBytes();
                                byte[] bytes1 = data.getBodyBytes();
                                try {
                                    String string = new String(bytes, "UTF-8");
                                    String string1 = new String(bytes1, "UTF-8");
                                    int length = 0;
                                    if (ByteOrder.BIG_ENDIAN.toString().equals(ByteOrder.nativeOrder().toString())) {
                                        length = BytesUtils.bytesToInt2(bytes, 0);
                                    } else {
                                        length = BytesUtils.bytesToInt(bytes, 0);
                                    }
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(4);
                                    byteBuffer.put(bytes, 0, 4);
                                    byte[] bytes2 = byteBuffer.array();
                                    int a = byteArrayToInt(bytes2);

                                    byte[] result = byteMergerAll(bytes, bytes1);
                                String message = MessageUtils.receive(result);
                                System.out.println(message);
                                if(receive != null) {
                                    receive.onCall(message);
                                }
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });

    }

    public void disconnect() {
        if(connectionManager != null) {
            connectionManager.disconnect();
            connectionManager = null;
        }
    }

    public void send(String message) {
        OkSocket.open(connectionInfo).send(new SendData(message));
    }

    public int byteArrayToInt(byte[] b)
    {
        return   b[0] & 0xFF |
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
