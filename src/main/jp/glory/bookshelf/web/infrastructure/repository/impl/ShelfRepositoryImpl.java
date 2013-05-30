package jp.glory.bookshelf.web.infrastructure.repository.impl;

import java.util.List;

import javax.inject.Inject;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepository;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.infrastructure.repository.annotation.WebRepository;
import jp.glory.bookshelf.web.infrastructure.repository.bean.BooksTableBean;
import jp.glory.bookshelf.web.infrastructure.repository.bean.ShelfsTableBean;
import jp.glory.bookshelf.web.infrastructure.repository.converter.BookConverter;
import jp.glory.bookshelf.web.infrastructure.repository.converter.ShelfConverter;
import jp.glory.bookshelf.web.infrastructure.repository.dao.BooksTableDao;
import jp.glory.bookshelf.web.infrastructure.repository.dao.ShelfBooksTableDao;
import jp.glory.bookshelf.web.infrastructure.repository.dao.ShelfsTableDao;

/**
 * 本棚リポジトリ
 * 
 * @author Junki Yamada
 * 
 */
@WebRepository
public class ShelfRepositoryImpl implements ShelfRepository {

	/** 本DAO */
	private final BooksTableDao bookDao;

	/** 本棚DAO */
	private final ShelfsTableDao shelfDao;

	/** 本棚-本DAO */
	private final ShelfBooksTableDao shelfBooksDao;

	/**
	 * コンストラクタ
	 * 
	 * @param bookDao 本DAO
	 * @param shelfDao 本棚DAO
	 * @param shelfBooksDao 本棚-本DAO
	 * @param
	 */
	@Inject
	public ShelfRepositoryImpl(final BooksTableDao bookDao, final ShelfsTableDao shelfDao,
			final ShelfBooksTableDao shelfBooksDao) {

		this.bookDao = bookDao;
		this.shelfDao = shelfDao;
		this.shelfBooksDao = shelfBooksDao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(final ShelfId newId, final Shelf shelf) {

		final ShelfsTableBean tableBean = ShelfConverter.convertToTableBean(shelf);
		tableBean.setShelfId(newId.getValue());

		shelfDao.insert(tableBean);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShelfId createNewNumbering() {

		return new ShelfId(shelfDao.createNewNumbering());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(final Shelf shelf) {

		shelfDao.update(ShelfConverter.convertToTableBean(shelf));
	}

	@Override
	public void updateBooks(final Shelf shelf) {

		final long shelfId = shelf.getShelfId().getValue();

		removeAllBooks(shelf.getShelfId());

		for (final Book book : shelf.getBookList()) {

			shelfBooksDao.insert(shelfId, book.getBookId().getValue());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final ShelfId shelfId) {

		shelfDao.delete(shelfId.getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Shelf findById(final ShelfId id) {

		final ShelfsTableBean tableBookBean = shelfDao.findById(id.getValue());
		final Shelf shelf = ShelfConverter.convertToEntity(tableBookBean);

		final List<BooksTableBean> bookTableList = bookDao.findListByShlefId(id.getValue());
		final List<Book> bookList = BookConverter.convertToEntityList(bookTableList);
		for (final Book book : bookList) {

			shelf.add(book);
		}

		return shelf;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Shelf> findAll() {

		final List<ShelfsTableBean> tableBeanList = shelfDao.findAll();

		return ShelfConverter.convertToEntityList(tableBeanList);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShelfId findIdByBookId(final BookId bookId) {

		final long shelfId = shelfBooksDao.findShelfIdByBookId(bookId.getValue());

		return new ShelfId(shelfId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addBook(final ShelfId shelfId, final BookId bookId) {

		shelfBooksDao.insert(shelfId.getValue(), bookId.getValue());
	}

	@Override
	public void removeAllBooks(final ShelfId shelfId) {

		shelfBooksDao.deleteBooksByShelfId(shelfId.getValue());
	}
}
