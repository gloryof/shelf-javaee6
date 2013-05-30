package jp.glory.bookshelf.web.application.shelf.view;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.value.Name;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.constant.ContentName;
import jp.glory.bookshelf.web.application.common.constant.PageTitle;
import jp.glory.bookshelf.web.application.common.view.bean.BookInfoBean;
import jp.glory.bookshelf.web.application.common.view.bean.ShelfInfoBean;
import jp.glory.bookshelf.web.tool.ShelfInfoAssert;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ShelfViewTest {

	private static ShelfView sut = null;

	public static class 共通設定のテスト {

		@Before
		public void setUp() {

			sut = new ShelfView(new Shelf(), new ArrayList<Shelf>());
		}

		@Test
		public void getTitleでページタイトルが返却される() {

			assertThat(sut.getTitle(), is(PageTitle.SHELF_VIEW));
		}

		@Test
		public void getContentNameでコンテンツ名が返却される() {

			assertThat(sut.getContentName(), is(ContentName.SHELF_VIEW));
		}
	}

	public static class コンストラクタに本棚と移動対象本棚リストを指定した場合 {

		@Before
		public void setUp() {

			sut = new ShelfView(TestTools.createShelf(), TestTools.createMoveShelfList());
		}

		@Test
		public void getShelfで本リストが未選択状態の本棚が返却される() {

			final ShelfInfoBean actualShelf = sut.getShelf();

			final ShelfInfoAssert infoAssert = new ShelfInfoAssert(actualShelf, TestTools.createShelf());
			infoAssert.assertShelf();

			final List<ShelfInfoBean> actualShelfList = sut.getMoveShelfList();
			TestTools.assertMoveShelfList(actualShelfList, TestTools.createMoveShelfList());

			final List<BookInfoBean> bookList = sut.getBookList();
			TestTools.asssertBookList(bookList, new ArrayList<BookId>());
		}
	}

	public static class コンストラクタの本棚にNullを指定した場合 {

		@Before
		public void setUp() {

			sut = new ShelfView(null, TestTools.createMoveShelfList());
		}

		@Test
		public void getShelfで_本棚情報が未設定になり_移動対象本棚リストが作成される() {

			final ShelfInfoBean actualShelf = sut.getShelf();

			assertThat(actualShelf, is(not(nullValue())));
			assertThat(actualShelf.getShelfId(), is(0L));
			assertThat(actualShelf.getName(), is(nullValue()));

			final List<ShelfInfoBean> actualShelfList = sut.getMoveShelfList();
			TestTools.assertMoveShelfList(actualShelfList, TestTools.createMoveShelfList());

			final List<BookInfoBean> bookList = sut.getBookList();
			assertThat(bookList, is(not(nullValue())));
			assertThat(bookList.isEmpty(), is(true));
		}
	}

	public static class コンストラクタの本棚を設定し_移動対象リストに空リストを指定した場合 {

		@Before
		public void setUp() {

			sut = new ShelfView(TestTools.createShelf(), new ArrayList<Shelf>());
		}

		@Test
		public void getShelfで_本棚情報が値が設定され_移動対象本棚リストは空リストになる() {

			final ShelfInfoBean actualShelf = sut.getShelf();

			final ShelfInfoAssert infoAssert = new ShelfInfoAssert(actualShelf, TestTools.createShelf());
			infoAssert.assertShelf();

			final List<ShelfInfoBean> actualShelfList = sut.getMoveShelfList();
			assertThat(actualShelfList, is(not(nullValue())));
			assertThat(actualShelfList.isEmpty(), is(true));

			final List<BookInfoBean> bookList = sut.getBookList();
			TestTools.asssertBookList(bookList, new ArrayList<BookId>());
		}
	}

	public static class コンストラクタの本棚を設定し_移動対象リストにNullを指定した場合 {

		@Before
		public void setUp() {

			sut = new ShelfView(TestTools.createShelf(), null);
		}

		@Test
		public void getShelfで_本棚情報が値が設定され_移動対象本棚リストは空リストになる() {

			final ShelfInfoBean actualShelf = sut.getShelf();

			final ShelfInfoAssert infoAssert = new ShelfInfoAssert(actualShelf, TestTools.createShelf());
			infoAssert.assertShelf();

			final List<ShelfInfoBean> actualShelfList = sut.getMoveShelfList();
			assertThat(actualShelfList, is(not(nullValue())));
			assertThat(actualShelfList.isEmpty(), is(true));

			final List<BookInfoBean> bookList = sut.getBookList();
			TestTools.asssertBookList(bookList, new ArrayList<BookId>());
		}
	}

	public static class コンストラクタに本棚と移動対象本棚リストと選択済み本棚リストを指定した場合 {

		@Before
		public void setUp() {

			sut = new ShelfView(TestTools.createShelf(), TestTools.createMoveShelfList(),
					TestTools.createCheckedShelfIdList());
		}

		@Test
		public void getShelfで本リストが選択状態の本棚が返却される() {

			final ShelfInfoBean actualShelf = sut.getShelf();

			final ShelfInfoAssert infoAssert = new ShelfInfoAssert(actualShelf, TestTools.createShelf());
			infoAssert.assertShelf();

			final List<ShelfInfoBean> actualShelfList = sut.getMoveShelfList();
			TestTools.assertMoveShelfList(actualShelfList, TestTools.createMoveShelfList());

			final List<BookInfoBean> bookList = sut.getBookList();
			TestTools.asssertBookList(bookList, TestTools.createCheckedShelfIdList());
		}
	}

	private static class TestTools {

		private static void assertMoveShelfList(final List<ShelfInfoBean> actualShelfList,
				final List<Shelf> expectedShelfList) {

			assertThat(actualShelfList, is(not(nullValue())));
			assertThat(actualShelfList.size(), is(expectedShelfList.size()));

			for (int i = 0; i < actualShelfList.size(); i++) {

				final Shelf expectedListShelf = expectedShelfList.get(i);
				final ShelfInfoBean actualListShelf = actualShelfList.get(i);

				final ShelfInfoAssert infoAssert = new ShelfInfoAssert(actualListShelf, expectedListShelf);
				infoAssert.assertShelf();
			}

		}

		private static void asssertBookList(final List<BookInfoBean> bookList, final List<BookId> checkedBookIdList) {

			for (final BookInfoBean bookInfo : bookList) {

				final boolean isCheckedValue = isChecked(bookInfo.getBookId(), checkedBookIdList);
				assertThat(bookInfo.isChecked(), is(isCheckedValue));
			}
		}

		private static boolean isChecked(final Long actualBookId, final List<BookId> selectedBookIdList) {

			for (final BookId bookId : selectedBookIdList) {

				if (bookId.getValue() == actualBookId) {

					return true;
				}
			}

			return false;
		}

		private static List<Shelf> createMoveShelfList() {

			final List<Shelf> shelfList = new ArrayList<>();

			shelfList.add(creaeMoveListShelf01());
			shelfList.add(creaeMoveListShelf02());
			shelfList.add(creaeMoveListShelf03());

			return shelfList;
		}

		private static List<Book> createBookList() {

			final List<Book> bookList = new ArrayList<>();

			bookList.add(createBook01());
			bookList.add(createBook02());
			bookList.add(createBook03());

			return bookList;
		}

		private static List<BookId> createCheckedShelfIdList() {

			final List<BookId> bookIdList = new ArrayList<>();
			bookIdList.add(createBook01().getBookId());
			bookIdList.add(createBook03().getBookId());

			return bookIdList;
		}

		private static Shelf createShelf() {

			final ShelfId shelfId = new ShelfId(10001L);

			final Shelf shelf = new Shelf(shelfId, createBookList());
			shelf.setName(new Name("テスト"));

			return shelf;
		}

		private static Shelf creaeMoveListShelf01() {

			final ShelfId shelfId = new ShelfId(20001L);

			return new Shelf(shelfId, new ArrayList<Book>());
		}

		private static Shelf creaeMoveListShelf02() {

			final ShelfId shelfId = new ShelfId(20002L);

			return new Shelf(shelfId, new ArrayList<Book>());
		}

		private static Shelf creaeMoveListShelf03() {

			final ShelfId shelfId = new ShelfId(20003L);

			return new Shelf(shelfId, new ArrayList<Book>());
		}

		private static Book createBook01() {

			final BookId bookId = new BookId(101L);
			final Book book = new Book(bookId);

			return book;
		}

		private static Book createBook02() {

			final BookId bookId = new BookId(102L);
			final Book book = new Book(bookId);

			return book;
		}

		private static Book createBook03() {

			final BookId bookId = new BookId(103L);
			final Book book = new Book(bookId);

			return book;
		}
	}
}
