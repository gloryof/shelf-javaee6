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
import jp.glory.bookshelf.web.application.common.view.bean.ShelfInfoBean;
import jp.glory.bookshelf.web.tool.ShelfInfoAssert;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ShelfEditViewTest {

	private static ShelfEditView sut = null;

	public static class 共通設定のテスト {

		@Before
		public void setUp() {

			sut = new ShelfEditView(new Shelf(), false);
		}

		@Test
		public void getTitleでページタイトルが返却される() {

			assertThat(sut.getTitle(), is(PageTitle.SHELF_EDIT_VIEW));
		}

		@Test
		public void getContentNameでコンテンツ名が返却される() {

			assertThat(sut.getContentName(), is(ContentName.SHELF_EDIT_VIEW));
		}
	}

	public static class パラメータに本棚と更新モードを指定した場合 {

		@Before
		public void setUp() {

			sut = new ShelfEditView(TestTools.createShelf(), false);
		}

		@Test
		public void getShelfで本棚情報Beanが返却される() {

			TestTools.assertShelf(sut.getShelf(), TestTools.createShelf());
		}

		@Test
		public void isUpdateModeでパラメータに指定した更新モードの値が返却される() {

			assertThat(sut.isUpdateMode(), is(false));
		}
	}

	public static class パラメータの本棚情報BeanにNullを指定した場合 {

		@Before
		public void setUp() {

			sut = new ShelfEditView(null, false);
		}

		@Test
		public void getShelfで本棚情報Beanが返却される() {

			final ShelfInfoBean actualShelf = sut.getShelf();

			assertThat(actualShelf, is(not(nullValue())));
			assertThat(actualShelf.getName(), is(nullValue()));
			assertThat(actualShelf.getShelfId(), is(0L));
			assertThat(actualShelf.isChecked(), is(false));

		}

		@Test
		public void isUpdateModeでパラメータに指定した更新モードの値が返却される() {

			assertThat(sut.isUpdateMode(), is(false));
		}
	}

	private static class TestTools {

		private static Shelf createShelf() {

			final ShelfId shelfId = new ShelfId(10001L);

			final List<Book> bookList = new ArrayList<>();
			bookList.add(new Book(new BookId(20001L)));
			bookList.add(new Book(new BookId(20002L)));

			final Shelf shelf = new Shelf(shelfId, bookList);
			shelf.setName(new Name("テスト"));

			return shelf;
		}

		private static void assertShelf(final ShelfInfoBean actualShelf, final Shelf expectedShelf) {

			final ShelfInfoAssert infoAssert = new ShelfInfoAssert(actualShelf, expectedShelf);
			infoAssert.assertShelf();

			assertThat(actualShelf.isChecked(), is(false));
		}
	}
}
