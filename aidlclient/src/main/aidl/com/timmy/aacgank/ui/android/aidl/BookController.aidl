// BookController.aidl
package com.timmy.aacgank.ui.android.aidl;
import com.timmy.aacgank.ui.android.aidl.Book;

// Declare any non-default types here with import statements

interface BookController {

    List<Book> getBookList();

    void addBookInOut(in Book book);
//    void addBookInOut(inout Book book);

}
