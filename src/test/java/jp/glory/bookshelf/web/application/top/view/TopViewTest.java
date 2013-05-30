package jp.glory.bookshelf.web.application.top.view;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
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
public class TopViewTest {

	private static TopView sut = null;

	public static class 共通設定のテスト {

		@Before
		public void setUp() {

			sut = new TopView(new ArrayList<Shelf>());
		}

		@Test
		public void getTitleでページタイトルが返却される() {

			assertThat(sut.getTitle(), is(PageTitle.TOP));
		}

		@Test
		public void getContentNameでコンテンツ名が返却される() {

			assertThat(sut.getContentName(), is(ContentName.TOP));
		}
	}

	public static class コンストラクタにパラメータ一つ_空リストを指定した場合 {

		@Before
		public void setUp() {

			sut = new TopView(new ArrayList<Shelf>());
		}

		@Test
		public void getShelfListで空のリストが返却される() {

			assertThat(sut.getShelfInfoList().isEmpty(), is(true));
		}

	}

	public static class コンストラクタにパラメータ一つ_Nullを指定した場合 {

		@Before
		public void setUp() {

			sut = new TopView(null);
		}

		@Test
		public void getShelfListで空のリストが返却される() {

			assertThat(sut.getShelfInfoList().isEmpty(), is(true));
		}

	}

	public static class コンストラクタにパラメータ一つ_本棚リストを指定した場合 {

		@Before
		public void setUp() {

			sut = new TopView(TestTools.createAllList());
		}

		@Test
		public void getShelfListで未選択状態の本棚情報リストが返却される() {

			final List<ShelfInfoBean> actualList = sut.getShelfInfoList();
			final List<Shelf> expectedList = TestTools.createAllList();

			final int actualSize = actualList.size();
			assertThat(actualSize, is(expectedList.size()));

			for (int i = 0; i < actualSize; i++) {

				final ShelfInfoBean actualShelf = actualList.get(i);
				final Shelf expectedShelf = expectedList.get(i);

				final ShelfInfoAssert infoAssert = new ShelfInfoAssert(actualShelf, expectedShelf);
				infoAssert.assertShelf();
				assertThat(actualShelf.isChecked(), is(false));
			}
		}
	}

	public static class コンストラクタにパラメータ二つ_本棚リストと空リストを指定した場合 {

		@Before
		public void setUp() {

			sut = new TopView(TestTools.createAllList(), new ArrayList<ShelfId>());
		}

		@Test
		public void getShelfListで未選択状態の本棚情報リストが返却される() {

			final List<ShelfInfoBean> actualList = sut.getShelfInfoList();
			final List<Shelf> expectedList = TestTools.createAllList();

			final int actualSize = actualList.size();
			assertThat(actualSize, is(expectedList.size()));

			for (int i = 0; i < actualSize; i++) {

				final ShelfInfoBean actualShelf = actualList.get(i);
				final Shelf expectedShelf = expectedList.get(i);

				final ShelfInfoAssert infoAssert = new ShelfInfoAssert(actualShelf, expectedShelf);
				infoAssert.assertShelf();
				assertThat(actualShelf.isChecked(), is(false));
			}
		}
	}

	public static class コンストラクタにパラメータ二つ_本棚リストと本棚IDリストを指定した場合 {

		@Before
		public void setUp() {

			sut = new TopView(TestTools.createAllList(), TestTools.createSelectedShelfIdList());
		}

		@Test
		public void getShelfListで選択状態の本棚情報リストが返却される() {

			final List<ShelfInfoBean> actualList = sut.getShelfInfoList();
			final List<Shelf> expectedList = TestTools.createAllList();
			final List<Long> selectedList = TestTools.convertToLongList(TestTools.createSelectedShelfIdList());

			final int actualSize = actualList.size();
			assertThat(actualSize, is(expectedList.size()));

			for (int i = 0; i < actualSize; i++) {

				final ShelfInfoBean actualShelf = actualList.get(i);
				final Shelf expectedShelf = expectedList.get(i);

				final ShelfInfoAssert infoAssert = new ShelfInfoAssert(actualShelf, expectedShelf);
				infoAssert.assertShelf();

				final boolean isChecked = selectedList.contains(actualShelf.getShelfId());
				assertThat(actualShelf.isChecked(), is(isChecked));
			}
		}
	}

	public static class コンストラクタにパラメータ二つ_本棚リストとNullの本棚IDリストを指定した場合 {

		@Before
		public void setUp() {

			sut = new TopView(TestTools.createAllList(), null);
		}

		@Test
		public void getShelfListで未選択状態の本棚情報リストが返却される() {

			final List<ShelfInfoBean> actualList = sut.getShelfInfoList();
			final List<Shelf> expectedList = TestTools.createAllList();

			final int actualSize = actualList.size();
			assertThat(actualSize, is(expectedList.size()));

			for (int i = 0; i < actualSize; i++) {

				final ShelfInfoBean actualShelf = actualList.get(i);
				final Shelf expectedShelf = expectedList.get(i);

				final ShelfInfoAssert infoAssert = new ShelfInfoAssert(actualShelf, expectedShelf);
				infoAssert.assertShelf();
				assertThat(actualShelf.isChecked(), is(false));
			}
		}

	}

	public static class getCreatingShelfIdメソッド {

		@Test
		public void 新規作成時の本棚IDが返却される() {

			final TopView sut = new TopView(new ArrayList<Shelf>());
			final ShelfId actualShelfId = sut.getCreatingShelfId();
			final ShelfId expectedShelfId = ShelfId.getUnclassifiedShelfsId();

			assertThat(actualShelfId, is(not(nullValue())));
			assertThat(actualShelfId.isSame(expectedShelfId), is(true));
		}
	}

	private static class TestTools {

		private static List<Shelf> createAllList() {

			final List<Shelf> expectedList = new ArrayList<>();
			expectedList.add(createShelf01());
			expectedList.add(createShelf02());
			expectedList.add(createShelf03());

			return expectedList;
		}

		private static List<ShelfId> createSelectedShelfIdList() {

			final List<ShelfId> shelfIdList = new ArrayList<>();

			shelfIdList.add(createShelf01().getShelfId());
			shelfIdList.add(createShelf02().getShelfId());

			return shelfIdList;
		}

		private static List<Long> convertToLongList(final List<ShelfId> shelfIdList) {

			final List<Long> longList = new ArrayList<>();

			for (final ShelfId shelfId : shelfIdList) {

				longList.add(shelfId.getValue());
			}

			return longList;
		}

		private static Shelf createShelf01() {

			final ShelfId shelfId = new ShelfId(100100L);
			final Shelf shelf = new Shelf(shelfId, new ArrayList<Book>());
			shelf.setName(new Name("テスト０１"));

			return shelf;
		}

		private static Shelf createShelf02() {

			final ShelfId shelfId = new ShelfId(100200L);
			final Shelf shelf = new Shelf(shelfId, new ArrayList<Book>());
			shelf.setName(new Name("テスト０２"));

			return shelf;
		}

		private static Shelf createShelf03() {

			final ShelfId shelfId = new ShelfId(100300L);
			final Shelf shelf = new Shelf(shelfId, new ArrayList<Book>());
			shelf.setName(new Name("テスト０３"));

			return shelf;
		}
	}
}
