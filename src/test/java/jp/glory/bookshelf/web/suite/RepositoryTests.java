package jp.glory.bookshelf.web.suite;

import jp.glory.bookshelf.web.infrastructure.repository.converter.BookConverterTest;
import jp.glory.bookshelf.web.infrastructure.repository.converter.ShelfConverterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		ShelfConverterTest.class, BookConverterTest.class
})
public class RepositoryTests {

}
