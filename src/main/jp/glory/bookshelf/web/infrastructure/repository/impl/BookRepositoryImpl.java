package jp.glory.bookshelf.web.infrastructure.repository.impl;

import java.util.List;

import javax.inject.Inject;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.repository.BookRepository;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.infrastructure.repository.annotation.WebRepository;
import jp.glory.bookshelf.web.infrastructure.repository.bean.BooksTableBean;
import jp.glory.bookshelf.web.infrastructure.repository.converter.BookConverter;
import jp.glory.bookshelf.web.infrastructure.repository.dao.BooksTableDao;
import jp.glory.bookshelf.web.infrastructure.repository.dao.ShelfBooksTableDao;

/**
 * 本リポジトリ
 * 
 * @author Junki Yamada
 * 
 */
@WebRepository
public class BookRepositoryImpl implements BookRepository {

	/** 本テーブルDAO */
	private final BooksTableDao bookDao;

	/** 本棚-本テーブルDAO */
	private final ShelfBooksTableDao shelfBooksDao;

	/**
	 * コンストラクタ
	 * 
	 * @param dao 本テーブルDAO
	 * @param shelfBooksDao
	 */
	@Inject
	public BookRepositoryImpl(final BooksTableDao dao, final ShelfBooksTableDao shelfBooksDao) {

		this.bookDao = dao;
		this.shelfBooksDao = shelfBooksDao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(final BookId bookId, final Book book) {

		final BooksTableBean tableBean = BookConverter.convertToTableBean(book);
		tableBean.setBookId(bookId.getValue());

		bookDao.insert(tableBean);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(final Book book) {

		bookDao.update(BookConverter.convertToTableBean(book));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final BookId bookId) {

		shelfBooksDao.deleteBook(bookId.getValue());
		bookDao.delete(bookId.getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Book findById(final BookId bookId) {

		final BooksTableBean result = bookDao.findById(bookId.getValue());

		final Book book = BookConverter.convertToEntity(result);
		book.setParentShelfId(getParentShelfId(bookId.getValue()));

		return book;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Book> findListByShelfId(final ShelfId shelfId) {

		final List<BooksTableBean> bookList = bookDao.findListByShlefId(shelfId.getValue());

		return convertEntityList(bookList);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Book> findListByIsbnCode(final IsbnCode isbnCode) {

		final List<BooksTableBean> bookList = bookDao.findListByIsbnCode(isbnCode.getOnlyCodes());

		return convertEntityList(bookList);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BookId createNewNumbering() {

		return new BookId(bookDao.createNewNumbering());
	}

	/**
	 * 親本棚IDを取得する
	 * 
	 * @param bookId 本ID
	 * @return 親本棚ID
	 */
	private ShelfId getParentShelfId(final long bookId) {

		final long parentShelfId = shelfBooksDao.findShelfIdByBookId(bookId);

		return new ShelfId(parentShelfId);
	}

	/**
	 * エンティティのリストに変換する
	 * 
	 * @param bookTableList 本テーブルリスト
	 * @return エンティティのリスト
	 */
	private List<Book> convertEntityList(final List<BooksTableBean> bookTableList) {

		final List<Book> bookList = BookConverter.convertToEntityList(bookTableList);
		for (final Book entity : bookList) {

			entity.setParentShelfId(getParentShelfId(entity.getBookId().getValue()));
		}

		return bookList;
	}
}
