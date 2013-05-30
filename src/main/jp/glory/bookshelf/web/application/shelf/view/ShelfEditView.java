package jp.glory.bookshelf.web.application.shelf.view;

import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.web.application.common.constant.ContentName;
import jp.glory.bookshelf.web.application.common.constant.PageTitle;
import jp.glory.bookshelf.web.application.common.view.AbstractView;
import jp.glory.bookshelf.web.application.common.view.bean.ShelfInfoBean;
import jp.glory.bookshelf.web.application.common.view.converter.ShelfInfoConverter;

/**
 * 本棚編集View
 * 
 * @author Junki Yamada
 * 
 */
public class ShelfEditView extends AbstractView {

	/** 本棚 */
	private final ShelfInfoBean shelf;

	/** 更新モード */
	private final boolean updateMode;

	/**
	 * コンストラクタ
	 * 
	 * @param shelf 本棚
	 * @param updateMode 更新モード
	 */
	public ShelfEditView(final Shelf shelf, final boolean updateMode) {

		this.shelf = createShelfInfo(shelf);
		this.updateMode = updateMode;
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

	@Override
	public PageTitle getTitle() {

		return PageTitle.SHELF_EDIT_VIEW;
	}

	@Override
	protected ContentName getContentName() {

		return ContentName.SHELF_EDIT_VIEW;
	}

	/**
	 * @return shelf
	 */
	public ShelfInfoBean getShelf() {
		return shelf;
	}

	/**
	 * @return updateMode
	 */
	public boolean isUpdateMode() {
		return updateMode;
	}

}
