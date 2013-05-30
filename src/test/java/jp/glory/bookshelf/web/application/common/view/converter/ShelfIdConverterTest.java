package jp.glory.bookshelf.web.application.common.view.converter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.shelf.value.ShelfId;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ShelfIdConverterTest {

	public static class convertShelfIdListのテスト {

		@Test
		public void Long型のIDリストを指定した場合_本棚IDに変換される() {

			final List<Long> expectedList = new ArrayList<>();
			expectedList.add(101L);
			expectedList.add(102L);
			expectedList.add(103L);

			final List<ShelfId> actualShelfIdList = ShelfIdConverter.convertToShelfIdList(expectedList);

			assertThat(actualShelfIdList, is(not(nullValue())));

			assertThat(actualShelfIdList.size(), is(expectedList.size()));

			for (int i = 0; i < expectedList.size(); i++) {

				final Long expectedShelfId = expectedList.get(i);
				final ShelfId actualShelfId = actualShelfIdList.get(i);

				assertThat(actualShelfId.getValue(), is(expectedShelfId));
			}
		}

		@Test
		public void 空のリストを指定した場合_空のリストが返却される() {

			final List<ShelfId> actualShelfIdList = ShelfIdConverter.convertToShelfIdList(new ArrayList<Long>());

			assertThat(actualShelfIdList, is(not(nullValue())));
			assertThat(actualShelfIdList.isEmpty(), is(true));
		}

		@Test
		public void Nullを指定した場合_空のリストが返却される() {

			final List<ShelfId> actualShelfIdList = ShelfIdConverter.convertToShelfIdList(null);

			assertThat(actualShelfIdList, is(not(nullValue())));
			assertThat(actualShelfIdList.isEmpty(), is(true));
		}
	}

	public static class convertToLongListのテスト {

		@Test
		public void 本棚IDリストを指定した場合_Long型に変換される() {

			final List<ShelfId> expectedShelfIdList = new ArrayList<>();
			expectedShelfIdList.add(new ShelfId(101L));
			expectedShelfIdList.add(new ShelfId(102L));
			expectedShelfIdList.add(new ShelfId(103L));

			final List<Long> actualShelfIdList = ShelfIdConverter.convertToLongList(expectedShelfIdList);

			assertThat(actualShelfIdList, is(not(nullValue())));
			assertThat(actualShelfIdList.size(), is(expectedShelfIdList.size()));

			for (int i = 0; i < expectedShelfIdList.size(); i++) {

				final ShelfId expectedShelfId = expectedShelfIdList.get(i);
				final Long actualShelfId = actualShelfIdList.get(i);

				assertThat(actualShelfId, is(expectedShelfId.getValue()));
			}
		}

		@Test
		public void 空のリストを指定した場合_空のリストが返却される() {

			final List<Long> actualShelfIdList = ShelfIdConverter.convertToLongList(new ArrayList<ShelfId>());

			assertThat(actualShelfIdList, is(not(nullValue())));
			assertThat(actualShelfIdList.isEmpty(), is(true));
		}

		@Test
		public void Nullを指定した場合_空のリストが返却される() {

			final List<Long> actualShelfIdList = ShelfIdConverter.convertToLongList(null);

			assertThat(actualShelfIdList, is(not(nullValue())));
			assertThat(actualShelfIdList.isEmpty(), is(true));
		}
	}
}
