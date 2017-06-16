package com.app.julie.oneproject.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {

    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();

    private AtomicBoolean mIsServiceDestory = new AtomicBoolean(false);

    private RemoteCallbackList<IOnNewBookArrivedListener> listeners = new RemoteCallbackList<>();

    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listeners.register(listener);
            int N = listeners.beginBroadcast();
            Log.i("tag", "listener size:" + N);
            listeners.finishBroadcast();
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listeners.unregister(listener);
            int N = listeners.beginBroadcast();
            Log.i("tag", "listener size:" + N);
            listeners.finishBroadcast();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book(1, "android"));
        bookList.add(new Book(2, "ios"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mIsServiceDestory.get()) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int id = bookList.size() + 1;
                    Book book = new Book(id, "new Book#" + id);
                    try {
                        onNewBookReceived(book);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void onNewBookReceived(Book book) throws RemoteException {
        bookList.add(book);
        Log.i("tag", "notify register listener");
        int N = listeners.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener l = listeners.getBroadcastItem(i);
            if (l != null) {
                l.onNewBookArrived(book);
            }
        }
        listeners.finishBroadcast();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestory.set(true);
        super.onDestroy();
    }
}
