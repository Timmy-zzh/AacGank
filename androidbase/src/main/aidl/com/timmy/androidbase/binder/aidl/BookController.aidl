// BookController.aidl
package com.timmy.androidbase.binder.aidl;
import com.timmy.androidbase.binder.aidl.Book;

// Declare any non-default types here with import statements

interface BookController {

    List<Book> getBookList();

    void addBookInOut(in Book book);
//    void addBookInOut(inout Book book);

}
