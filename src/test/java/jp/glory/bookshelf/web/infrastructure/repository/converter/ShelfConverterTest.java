package jp.glory.bookshelf.web.infrastructure.repository.converter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.value.Name;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.infrastructure.repository.bean.ShelfsTableBean;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ShelfConverterTest {

	public static class convertToEntityのテスト {

		@Test
		public void パラメータで指定されたテーブルBeanがエンティティに変換される() {

			final ShelfsTableBean expecetedTableBean = createShelfsTableBean();
			final Shelf actualEntity = ShelfConverter.convertToEntity(expecetedTableBean);

			assertEntity(actualEntity, expecetedTableBean);
		}

		@Test
		public void パラメータがNullの場合_Nullが返却される() {

			assertThat(ShelfConverter.convertToEntity(null), is(nullValue()));
		}

		private ShelfsTableBean createShelfsTableBean() {

			final ShelfsTableBean tableBean = new ShelfsTableBean();

			tableBean.setShelfId(1234);
			tableBean.setName("タイトル");
			tableBean.setDeleteFlag("0");

			return tableBean;
		}
	}

	public static class convertToTableBeanのテスト {

		@Test
		public void パラメータで指定されたエンティティがテーブルBeanに変換される() {

			final Shelf expectedEntity = createEntity();
			final ShelfsTableBean actualTableBean = ShelfConverter.convertToTableBean(expectedEntity);

			assertTableBean(actualTableBean, expectedEntity);
		}

		@Test
		public void エンティティがNullの場合() {

			assertThat(ShelfConverter.convertToTableBean(null), is(nullValue()));
		}

		private Shelf createEntity() {

			final ShelfId shelfId = new ShelfId(1234L);
			final Shelf shelf = new Shelf(shelfId, new ArrayList<Book>());

			shelf.setName(new Name("テスト"));

			return shelf;
		}
	}

	public static class convertToTableBeanListのテスト {

		@Test
		public void パラメータで指定したテーブルBeanリストがエンティティリストに変換される() {

			final List<ShelfsTableBean> expectedList = createShelfList();
			final List<Shelf> actualList = ShelfConverter.convertToEntityList(expectedList);

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.size(), is(expectedList.size()));

			for (int i = 0; i < actualList.size(); i++) {

				final ShelfsTableBean expectedTableBean = expectedList.get(i);
				final Shelf actualEntity = actualList.get(i);

				assertEntity(actualEntity, expectedTableBean);
			}
		}

		@Test
		public void パラメータで指定したリストが空の場合_空のリストが返却される() {

			final List<ShelfsTableBean> expectedList = new ArrayList<>();
			final List<Shelf> actualList = ShelfConverter.convertToEntityList(expectedList);

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.size(), is(0));
		}

		@Test
		public void パラメータNullの場合_Nullが返却される() {

			assertThat(ShelfConverter.convertToEntityList(null), is(nullValue()));
		}

		private List<ShelfsTableBean> createShelfList() {

			final List<ShelfsTableBean> returnList = new ArrayList<>();

			for (int i = 0; i < returnList.size(); i++) {

				returnList.add(createShelfsTableBean(i));
			}

			return returnList;
		}

		private ShelfsTableBean createShelfsTableBean(final int seqNo) {

			final ShelfsTableBean tableBean = new ShelfsTableBean();

			tableBean.setShelfId(1000 + seqNo);
			tableBean.setName("タイトル" + seqNo);
			tableBean.setDeleteFlag("0");

			return tableBean;
		}
	}

	private static void assertEntity(final Shelf entity, final ShelfsTableBean tableBean) {

		assertThat(entity, is(not(nullValue())));
		assertSameInfo(entity, tableBean);
	}

	private static void assertTableBean(final ShelfsTableBean tableBean, final Shelf entity) {

		assertThat(tableBean, is(not(nullValue())));
		assertSameInfo(entity, tableBean);
	}

	private static void assertSameInfo(final Shelf entity, final ShelfsTableBean tableBean) {

		assertThat(entity.getName().getValue(), is(tableBean.getName()));
		assertThat(entity.getShelfId().getValue(), is(tableBean.getShelfId()));
	}

}
