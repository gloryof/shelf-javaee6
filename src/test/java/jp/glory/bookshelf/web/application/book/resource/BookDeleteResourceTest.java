package jp.glory.bookshelf.web.application.book.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.repository.BookRepositoryStub;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.book.value.Price;
import jp.glory.bookshelf.domain.book.value.Title;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepositoryStub;
import jp.glory.bookshelf.domain.shelf.value.Name;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.view.bean.BookInfoBean;
import jp.glory.bookshelf.web.application.common.view.bean.ShelfInfoBean;
import jp.glory.bookshelf.web.application.shelf.view.ShelfView;
import jp.glory.bookshelf.web.tool.ResponseUrlAssert;
import jp.glory.bookshelf.web.tool.ResponseViewAssert;
import jp.glory.bookshelf.web.tool.ResponseViewExtractor;
import jp.glory.bookshelf.web.tool.ShelfInfoAssert;

import org.junit.Before;
import org.junit.Test;

public class BookDeleteResourceTest {

	private BookDeleteResource sut = null;

	private ShelfRepositoryStub shelfRepositoryStub = null;

	private BookRepositoryStub bookRepositoryStub = null;

	@Before
	public void setUp() {

		shelfRepositoryStub = new ShelfRepositoryStub();
		shelfRepositoryStub.addShelf(TestTools.createShelf());

		bookRepositoryStub = new BookRepositoryStub();
		bookRepositoryStub.addBook(TestTools.createBook01());
		bookRepositoryStub.addBook(TestTools.createBook02());
		bookRepositoryStub.addBook(TestTools.createBook03());
		bookRepositoryStub.addBook(TestTools.createBook04());
		bookRepositoryStub.addBook(TestTools.createBook05());

		sut = new BookDeleteResource(shelfRepositoryStub, bookRepositoryStub);
	}

	@Test
	public void 削除に成功した場合_パラメータで指定した本棚のページに遷移する() {

		final ShelfId parentShelfId = TestTools.createShelf().getShelfId();
		final String expectedUrl = ResourceUrlConst.SHELF + parentShelfId.getValue();

		final List<Long> bookIdList = new ArrayList<>();
		bookIdList.add(TestTools.createBook01().getBookId().getValue());
		bookIdList.add(TestTools.createBook03().getBookId().getValue());
		bookIdList.add(TestTools.createBook05().getBookId().getValue());

		final Response actualResponse = sut.deleteBooks(parentShelfId.getValue(), bookIdList);
		final ResponseUrlAssert urlAssert = new ResponseUrlAssert(actualResponse, expectedUrl);

		urlAssert.assertResponse();
	}

	@Test
	public void 削除に失敗した場合_エラーメッセージが設定され再表示される() {

		final Shelf expectedShelf = TestTools.createShelf();
		final ShelfId parentShelfId = expectedShelf.getShelfId();

		final List<Long> bookIdList = new ArrayList<>();
		bookIdList.add(TestTools.createBook01().getBookId().getValue());
		bookIdList.add(null);
		bookIdList.add(TestTools.createBook05().getBookId().getValue());

		final Response actualResponse = sut.deleteBooks(parentShelfId.getValue(), bookIdList);

		final ResponseViewAssert viewAssert = new ResponseViewAssert(actualResponse, ShelfView.class);
		viewAssert.assertResponse();

		final ResponseViewExtractor<ShelfView> viewExtractor = new ResponseViewExtractor<>(actualResponse);
		final ShelfView actualView = viewExtractor.extractView();

		final ShelfInfoBean actualShelfInfo = actualView.getShelf();
		final ShelfInfoAssert shelfInfoAssert = new ShelfInfoAssert(actualShelfInfo, expectedShelf);
		shelfInfoAssert.assertShelf();
		assertThat(actualShelfInfo.isChecked(), is(false));

		final List<ShelfInfoBean> moveShelfList = actualView.getMoveShelfList();
		assertThat(moveShelfList, is(not(nullValue())));
		assertThat(moveShelfList.isEmpty(), is(true));

		final List<BookInfoBean> bookInfoList = actualView.getBookList();
		assertThat(bookIdList, is(not(nullValue())));
		for (final BookInfoBean bookInfo : bookInfoList) {

			boolean expectedChecked = bookIdList.contains(bookInfo.getBookId());
			assertThat(bookInfo.isChecked(), is(expectedChecked));
		}
	}

	private static class TestTools {

		private static final ShelfId parentShelfId = new ShelfId(101L);

		private static Shelf createShelf() {

			final Shelf shelf = new Shelf(parentShelfId, createBookList());
			shelf.setName(new Name("本棚"));

			return shelf;
		}

		private static List<Book> createBookList() {

			final List<Book> bookList = new ArrayList<>();

			bookList.add(createBook01());
			bookList.add(createBook02());
			bookList.add(createBook03());
			bookList.add(createBook04());
			bookList.add(createBook05());

			return bookList;
		}

		private static Book createBook01() {

			final BookId bookId = new BookId(101001L);
			final Book book = new Book(bookId);
			book.setParentShelfId(parentShelfId);
			book.setIsbnCode(new IsbnCode("1234567890001"));
			book.setTitle(new Title("テスト01"));
			book.setPrice(new Price(1500));

			return book;
		}

		private static Book createBook02() {

			final BookId bookId = new BookId(101002L);
			final Book book = new Book(bookId);
			book.setParentShelfId(parentShelfId);
			book.setIsbnCode(new IsbnCode("1234567890002"));
			book.setTitle(new Title("テスト02"));
			book.setPrice(new Price(2500));

			return book;
		}

		private static Book createBook03() {

			final BookId bookId = new BookId(101003L);
			final Book book = new Book(bookId);
			book.setParentShelfId(parentShelfId);
			book.setIsbnCode(new IsbnCode("1234567890003"));
			book.setTitle(new Title("テスト03"));
			book.setPrice(new Price(3500));

			return book;
		}

		private static Book createBook04() {

			final BookId bookId = new BookId(101004L);
			final Book book = new Book(bookId);
			book.setParentShelfId(parentShelfId);
			book.setIsbnCode(new IsbnCode("1234567890004"));
			book.setTitle(new Title("テスト04"));
			book.setPrice(new Price(4500));

			return book;
		}

		private static Book createBook05() {

			final BookId bookId = new BookId(101005L);
			final Book book = new Book(bookId);
			book.setParentShelfId(parentShelfId);
			book.setIsbnCode(new IsbnCode("1234567890005"));
			book.setTitle(new Title("テスト05"));
			book.setPrice(new Price(5500));

			return book;
		}
	}
}
