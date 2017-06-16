// IOnNewBookArrivedListener.aidl
package com.app.julie.oneproject.aidl;

// Declare any non-default types here with import statements
import com.app.julie.oneproject.aidl.Book;

interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNewBookArrived(in Book book);
}
