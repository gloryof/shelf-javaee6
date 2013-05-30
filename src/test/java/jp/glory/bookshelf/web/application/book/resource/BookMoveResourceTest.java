package jp.glory.bookshelf.web.application.book.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepositoryStub;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.view.bean.ShelfInfoBean;
import jp.glory.bookshelf.web.application.shelf.view.ShelfView;
import jp.glory.bookshelf.web.tool.ResponseUrlAssert;
import jp.glory.bookshelf.web.tool.ResponseViewAssert;
import jp.glory.bookshelf.web.tool.ResponseViewExtractor;
import jp.glory.bookshelf.web.tool.ShelfInfoAssert;

import org.junit.Before;
import org.junit.Test;

public class BookMoveResourceTest {

	private BookMoveResource sut = null;

	private ShelfRepositoryStub stub = null;

	@Before
	public void setUp() {

		stub = new ShelfRepositoryStub();
		stub.addShelf(createFromShlf());
		stub.addShelf(createToShlf());

		sut = new BookMoveResource(stub);
	}

	@Test
	public void 正常終了した場合_fromの本棚のURLが返却される() {

		final long fromShelIdParam = createFromShlf().getShelfId().getValue();
		final long toShelfIdParam = createToShlf().getShelfId().getValue();

		final List<Long> bookIdList = new ArrayList<>();
		final List<Book> moveBookList = createMoveBookList();
		for (final Book book : moveBookList) {

			bookIdList.add(book.getBookId().getValue());
		}

		final Response response = sut.move(fromShelIdParam, toShelfIdParam, bookIdList);

		final String expectedUrl = ResourceUrlConst.SHELF + fromShelIdParam;

		final ResponseUrlAssert urlAssert = new ResponseUrlAssert(response, expectedUrl);
		urlAssert.assertResponse();
	}

	@Test
	public void ドメインチェックでエラーになった場合_fromの本棚のURLが返却される() {

		final Shelf expectedShelf = createFromShlf();
		final long fromShelIdParam = expectedShelf.getShelfId().getValue();
		final long toShelfIdParam = createToShlf().getShelfId().getValue();
		final List<Long> bookIdList = new ArrayList<>();

		final Response response = sut.move(fromShelIdParam, toShelfIdParam, bookIdList);

		final ResponseViewAssert viewAssert = new ResponseViewAssert(response, ShelfView.class);
		viewAssert.assertResponse();

		final ResponseViewExtractor<ShelfView> viewExtractor = new ResponseViewExtractor<>(response);
		final ShelfView actualView = viewExtractor.extractView();
		assertThat(actualView, is(not(nullValue())));

		final ShelfInfoBean actualShelf = actualView.getShelf();

		final ShelfInfoAssert shelfInfoAssert = new ShelfInfoAssert(actualShelf, expectedShelf);
		shelfInfoAssert.assertShelf();
	}

	private Shelf createFromShlf() {

		final ShelfId shelfId = new ShelfId(1L);
		final List<Book> bookList = createFromBookList();

		return new Shelf(shelfId, bookList);
	}

	private Shelf createToShlf() {

		final ShelfId shelfId = new ShelfId(2L);
		final List<Book> bookList = createToBookList();

		return new Shelf(shelfId, bookList);
	}

	private List<Book> createFromBookList() {

		final List<Book> bookList = createMoveBookList();

		bookList.add(new Book(new BookId(10001L)));
		bookList.add(new Book(new BookId(10003L)));
		bookList.add(new Book(new BookId(10005L)));

		return bookList;
	}

	private List<Book> createToBookList() {

		final List<Book> bookList = new ArrayList<>();

		bookList.add(new Book(new BookId(20001L)));
		bookList.add(new Book(new BookId(20003L)));
		bookList.add(new Book(new BookId(20005L)));

		return bookList;
	}

	private List<Book> createMoveBookList() {

		final List<Book> bookList = new ArrayList<>();

		bookList.add(new Book(new BookId(10002L)));
		bookList.add(new Book(new BookId(10004L)));

		return bookList;
	}
}
