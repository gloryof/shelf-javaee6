package jp.glory.bookshelf.web.suite;

import jp.glory.bookshelf.web.application.book.view.BookCreateViewTest;
import jp.glory.bookshelf.web.application.book.view.BookEditViewTest;
import jp.glory.bookshelf.web.application.book.view.BookViewTest;
import jp.glory.bookshelf.web.application.common.view.ErrorViewTest;
import jp.glory.bookshelf.web.application.common.view.converter.ShelfInfoConverterTest;
import jp.glory.bookshelf.web.application.shelf.view.ShelfEditViewTest;
import jp.glory.bookshelf.web.application.shelf.view.ShelfViewTest;
import jp.glory.bookshelf.web.application.top.view.TopViewTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		TopViewTest.class,
		BookViewTest.class,
		BookEditViewTest.class,
		BookCreateViewTest.class,
		ShelfViewTest.class,
		ShelfEditViewTest.class,
		ShelfInfoConverterTest.class,
		ErrorViewTest.class
})
public class ViewTests {

}
