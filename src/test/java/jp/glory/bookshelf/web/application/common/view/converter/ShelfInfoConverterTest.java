package jp.glory.bookshelf.web.application.common.view.converter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.value.Name;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.view.bean.ShelfInfoBean;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ShelfInfoConverterTest {

	public static class convertToViewInfoのテスト {

		@Test
		public void パラメータに指定した値が本棚情報Beanに変換され返却される() {

			final Shelf expectedShelf = TestTools.createShelf01();
			final boolean expectedChecked = true;

			final ShelfInfoBean shelfInfo = ShelfInfoConverter.convertToViewInfo(expectedShelf, expectedChecked);
			TestTools.assertShelf(shelfInfo, expectedShelf, expectedChecked);
		}

		@Test
		public void パラメータの選択値を指定しない場合_未選択状態の本棚情報Beanが返却される() {

			final Shelf expectedShelf = TestTools.createShelf01();
			final boolean expectedChecked = false;

			final ShelfInfoBean shelfInfo = ShelfInfoConverter.convertToViewInfo(expectedShelf);
			TestTools.assertShelf(shelfInfo, expectedShelf, expectedChecked);
		}

		@Test
		public void パラメータにNullを指定した場合_Nullが返却される() {

			assertThat(ShelfInfoConverter.convertToViewInfo(null, false), is(nullValue()));
		}
	}

	public static class convertToViewInfoListのテスト {

		@Test
		public void パラメータで指定したリストが全て変換され_選択済みリストに指定したIDのみが選択される() {

			final List<Shelf> shelfList = TestTools.createShelfList();
			final List<ShelfId> selectedShelfIdList = TestTools.createCheckedShelfIdList();

			final List<ShelfInfoBean> actualShelfInfoList = ShelfInfoConverter.convertToViewInfoList(shelfList,
					selectedShelfIdList);

			assertThat(actualShelfInfoList, is(not(nullValue())));
			assertThat(actualShelfInfoList.size(), is(shelfList.size()));

			for (int i = 0; i < shelfList.size(); i++) {

				final ShelfInfoBean actualShelfInfo = actualShelfInfoList.get(i);
				final Shelf expectedShelf = shelfList.get(i);
				final boolean isChecked = TestTools.isChecked(selectedShelfIdList, expectedShelf.getShelfId());

				TestTools.assertShelf(actualShelfInfo, expectedShelf, isChecked);
			}
		}

		@Test
		public void 本リストに空のリストを設定した場合_空のリストが返却される() {

			final List<ShelfInfoBean> actualShelfInfoList = ShelfInfoConverter.convertToViewInfoList(
					new ArrayList<Shelf>(), new ArrayList<ShelfId>());

			assertThat(actualShelfInfoList, is(not(nullValue())));
			assertThat(actualShelfInfoList.isEmpty(), is(true));
		}

		@Test
		public void 選択済みリストに空リストを指定した場合_全てが未選択になる() {

			final List<Shelf> shelfList = TestTools.createShelfList();
			final List<ShelfId> selectedShelfIdList = new ArrayList<>();

			final List<ShelfInfoBean> actualShelfInfoList = ShelfInfoConverter.convertToViewInfoList(shelfList,
					selectedShelfIdList);

			assertThat(actualShelfInfoList, is(not(nullValue())));
			assertThat(actualShelfInfoList.size(), is(shelfList.size()));

			for (int i = 0; i < shelfList.size(); i++) {

				final ShelfInfoBean actualShelfInfo = actualShelfInfoList.get(i);
				final Shelf expectedShelf = shelfList.get(i);

				TestTools.assertShelf(actualShelfInfo, expectedShelf, false);
			}
		}

		@Test
		public void 本リストにNullを指定した場合_Nullが返却される() {

			final List<ShelfInfoBean> actualShelfInfoList = ShelfInfoConverter.convertToViewInfoList(null, null);

			assertThat(actualShelfInfoList, is(nullValue()));

		}

		@Test
		public void 選択済みリストにNullを指定した場合_全てが未選択になる() {

			final List<Shelf> shelfList = TestTools.createShelfList();
			final List<ShelfId> selectedShelfIdList = null;

			final List<ShelfInfoBean> actualShelfInfoList = ShelfInfoConverter.convertToViewInfoList(shelfList,
					selectedShelfIdList);

			assertThat(actualShelfInfoList, is(not(nullValue())));
			assertThat(actualShelfInfoList.size(), is(shelfList.size()));

			for (int i = 0; i < shelfList.size(); i++) {

				final ShelfInfoBean actualShelfInfo = actualShelfInfoList.get(i);
				final Shelf expectedShelf = shelfList.get(i);

				TestTools.assertShelf(actualShelfInfo, expectedShelf, false);
			}
		}
	}

	private static class TestTools {

		private static void assertShelf(final ShelfInfoBean shelfInfo, final Shelf shelf, final boolean checkedValue) {

			assertThat(shelfInfo, is(not(nullValue())));

			assertThat(shelfInfo.getShelfId(), is(shelf.getShelfId().getValue()));
			assertThat(shelfInfo.getName(), is(shelf.getName().getValue()));
			assertThat(shelfInfo.isChecked(), is(checkedValue));
		}

		private static boolean isChecked(final List<ShelfId> shelfIdList, final ShelfId shelfId) {

			for (final ShelfId compareShelfId : shelfIdList) {

				if (shelfId.isSame(compareShelfId)) {

					return true;
				}
			}

			return false;
		}

		private static List<Shelf> createShelfList() {

			final List<Shelf> shelfList = new ArrayList<>();
			shelfList.add(createShelf01());
			shelfList.add(createShelf02());
			shelfList.add(createShelf03());

			return shelfList;
		}

		private static List<ShelfId> createCheckedShelfIdList() {

			final List<ShelfId> shelfIdList = new ArrayList<>();

			shelfIdList.add(createShelf01().getShelfId());
			shelfIdList.add(createShelf03().getShelfId());

			return shelfIdList;
		}

		private static Shelf createShelf01() {

			final ShelfId shelfId = new ShelfId(10001L);
			final Shelf shelf = new Shelf(shelfId, new ArrayList<Book>());
			shelf.setName(new Name("テスト１"));

			return shelf;
		}

		private static Shelf createShelf02() {

			final ShelfId shelfId = new ShelfId(20001L);
			final Shelf shelf = new Shelf(shelfId, new ArrayList<Book>());
			shelf.setName(new Name("テスト２"));

			return shelf;
		}

		private static Shelf createShelf03() {

			final ShelfId shelfId = new ShelfId(20001L);
			final Shelf shelf = new Shelf(shelfId, new ArrayList<Book>());
			shelf.setName(new Name("テスト２"));

			return shelf;
		}
	}
}
