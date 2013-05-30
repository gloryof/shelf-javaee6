package jp.glory.bookshelf.web.suite;

import jp.glory.bookshelf.web.application.common.exception.mapper.SystemExceptionMapperTest;
import jp.glory.bookshelf.web.application.common.view.converter.BookIdConverterTest;
import jp.glory.bookshelf.web.application.common.view.converter.BookInfoConverterTest;
import jp.glory.bookshelf.web.application.common.view.converter.ShelfIdConverterTest;
import jp.glory.bookshelf.web.application.common.view.converter.ShelfInfoConverterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		SystemExceptionMapperTest.class,
		ShelfInfoConverterTest.class,
		ShelfIdConverterTest.class,
		BookInfoConverterTest.class,
		BookIdConverterTest.class
})
public class CommonTests {

}
