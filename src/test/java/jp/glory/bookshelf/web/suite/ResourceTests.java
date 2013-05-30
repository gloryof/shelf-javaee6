package jp.glory.bookshelf.web.suite;

import jp.glory.bookshelf.web.application.book.resource.BookCreateResourceTest;
import jp.glory.bookshelf.web.application.book.resource.BookDeleteResourceTest;
import jp.glory.bookshelf.web.application.book.resource.BookEditResourceTest;
import jp.glory.bookshelf.web.application.book.resource.BookMoveResourceTest;
import jp.glory.bookshelf.web.application.book.resource.BookViewResourceTest;
import jp.glory.bookshelf.web.application.common.response.ResponseCreatorTest;
import jp.glory.bookshelf.web.application.shelf.resource.ShelfCreateResourceTest;
import jp.glory.bookshelf.web.application.shelf.resource.ShelfDeleteResourceTest;
import jp.glory.bookshelf.web.application.shelf.resource.ShelfEditResourceTest;
import jp.glory.bookshelf.web.application.shelf.resource.ShelfViewResourceTest;
import jp.glory.bookshelf.web.application.top.resource.TopViewResourceTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		BookEditResourceTest.class,
		BookViewResourceTest.class,
		BookCreateResourceTest.class,
		BookMoveResourceTest.class,
		BookDeleteResourceTest.class,
		ShelfEditResourceTest.class,
		ShelfViewResourceTest.class,
		ShelfCreateResourceTest.class,
		ShelfDeleteResourceTest.class,
		TopViewResourceTest.class,
		ResponseCreatorTest.class
})
public class ResourceTests {

}
