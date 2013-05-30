package jp.glory.bookshelf.web.infrastructure.repository.converter;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.book.value.Price;
import jp.glory.bookshelf.domain.book.value.Title;
import jp.glory.bookshelf.web.infrastructure.repository.bean.BooksTableBean;

/**
 * 本エンティティと本テーブルBeanの変換を行う。
 * 
 * @author Junki Yamada
 * 
 */
public final class BookConverter {

	/**
	 * コンストラクタ
	 */
	private BookConverter() {

	}

	/**
	 * エンティティに変換する
	 * 
	 * @param テーブルBean
	 * @return エンティティ
	 */
	public static Book convertToEntity(final BooksTableBean tableBean) {

		if (tableBean == null) {

			return null;
		}

		final Book entity = new Book(new BookId(tableBean.getBookId()));

		entity.setTitle(new Title(tableBean.getTitle()));
		entity.setIsbnCode(new IsbnCode(tableBean.getIsbnCode()));
		entity.setPrice(new Price(tableBean.getPrice()));

		return entity;
	}

	/**
	 * テーブルBeanに変換する
	 * 
	 * @param entity エンティティ
	 * @return テーブルBean
	 */
	public static BooksTableBean convertToTableBean(final Book entity) {

		if (entity == null) {

			return null;
		}

		final BooksTableBean tableBean = new BooksTableBean();

		tableBean.setBookId(entity.getBookId().getValue());
		tableBean.setTitle(entity.getTitle().getValue());
		tableBean.setIsbnCode(entity.getIsbnCode().getOnlyCodes());
		tableBean.setPrice(entity.getPrice().getValue());

		return tableBean;
	}

	/**
	 * テーブルBeanのリストをエンティティのリストに変換する
	 * 
	 * @param bookList 本テーブルリスト
	 * @return 本エンティティリスト
	 */
	public static List<Book> convertToEntityList(final List<BooksTableBean> bookList) {

		if (bookList == null) {

			return null;
		}

		final List<Book> returnList = new ArrayList<>();

		for (final BooksTableBean tableBean : bookList) {

			returnList.add(convertToEntity(tableBean));
		}

		return returnList;
	}

	/**
	 * エンティティのリストをテーブルBeanのリストに変換する
	 * 
	 * @param entityList 本エンティティリスト
	 * @return 本テーブルリスト
	 */
	public static List<BooksTableBean> convertToTableBeanList(final List<Book> entityList) {

		if (entityList == null) {

			return null;
		}

		final List<BooksTableBean> returnList = new ArrayList<>();

		for (final Book entity : entityList) {

			returnList.add(convertToTableBean(entity));
		}

		return returnList;
	}
}
