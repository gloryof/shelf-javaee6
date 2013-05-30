package jp.glory.bookshelf.web.application.shelf.view;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.constant.ContentName;
import jp.glory.bookshelf.web.application.common.constant.PageTitle;
import jp.glory.bookshelf.web.application.common.view.AbstractView;
import jp.glory.bookshelf.web.application.common.view.bean.BookInfoBean;
import jp.glory.bookshelf.web.application.common.view.bean.ShelfInfoBean;
import jp.glory.bookshelf.web.application.common.view.converter.BookInfoConverter;
import jp.glory.bookshelf.web.application.common.view.converter.ShelfInfoConverter;

/**
 * 本棚View
 * 
 * @author Junki Yamada
 * 
 */
public class ShelfView extends AbstractView {

	/** 本棚情報 */
	private final ShelfInfoBean shelf;

	/** 本リスト */
	private final List<BookInfoBean> bookList;

	/** 移動先リスト */
	private final List<ShelfInfoBean> moveShelfList;

	/**
	 * コンストラクタ
	 * 
	 * @param shelf 本棚
	 * @param moveShelfList 移動先リスト
	 */
	public ShelfView(final Shelf shelf, final List<Shelf> moveShelfList) {

		this(shelf, moveShelfList, new ArrayList<BookId>());
	}

	/**
	 * コンストラクタ
	 * 
	 * @param shelf 本棚
	 * @param moveShelfList 移動先リスト
	 * @param checkedBookIdList 選択済み本棚リスト
	 */
	public ShelfView(final Shelf shelf, final List<Shelf> moveShelfList, final List<BookId> checkedBookIdList) {

		this.shelf = createShelfInfo(shelf);
		this.bookList = createBookInfoList(shelf, checkedBookIdList);
		this.moveShelfList = createMoveShelfList(moveShelfList);
	}

	/**
	 * 本棚情報Beanを作成する
	 * 
	 * @param shelf 本棚
	 * @return 本棚情報Bean
	 */
	private ShelfInfoBean createShelfInfo(final Shelf shelf) {

		if (shelf == null) {

			return new ShelfInfoBean();
		}

		return ShelfInfoConverter.convertToViewInfo(shelf, false);
	}

	/**
	 * 本情報Beanリストを作成する
	 * 
	 * @param shelf 本棚
	 * @param checkedBookIdList 選択済み本棚IDリスト
	 * @return 本情報Beanリスト
	 */
	private List<BookInfoBean> createBookInfoList(final Shelf shelf, final List<BookId> checkedBookIdList) {

		if (shelf == null) {

			return new ArrayList<>();
		}

		return BookInfoConverter.convertToBookInfoList(shelf.getBookList(), checkedBookIdList);
	}

	/**
	 * 移動対象本棚情報Beanリストを作成する
	 * 
	 * @param moveShelfList 移動対象本棚リスト
	 * @param checkedBookIdList 選択済み本棚IDリスト
	 * @return 移動対象本棚情報Beanリスト
	 */
	private List<ShelfInfoBean> createMoveShelfList(final List<Shelf> moveShelfList) {

		if (moveShelfList == null) {

			return new ArrayList<>();
		}

		return ShelfInfoConverter.convertToViewInfoList(moveShelfList, new ArrayList<ShelfId>());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PageTitle getTitle() {

		return PageTitle.SHELF_VIEW;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ContentName getContentName() {

		return ContentName.SHELF_VIEW;
	}

	/**
	 * @return shelf
	 */
	public ShelfInfoBean getShelf() {
		return shelf;
	}

	/**
	 * @return bookList
	 */
	public List<BookInfoBean> getBookList() {
		return bookList;
	}

	/**
	 * @return moveShelfList
	 */
	public List<ShelfInfoBean> getMoveShelfList() {
		return moveShelfList;
	}

}
