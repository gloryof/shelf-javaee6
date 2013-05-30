package jp.glory.bookshelf.web.application.top.view;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.constant.ContentName;
import jp.glory.bookshelf.web.application.common.constant.PageTitle;
import jp.glory.bookshelf.web.application.common.view.AbstractView;
import jp.glory.bookshelf.web.application.common.view.bean.ShelfInfoBean;
import jp.glory.bookshelf.web.application.common.view.converter.ShelfInfoConverter;

/**
 * TOPビューオブジェクト
 * 
 * @author Junki Yamada
 * 
 */
public class TopView extends AbstractView {

	/** 本棚リスト */
	private final List<ShelfInfoBean> shelfInfoList;

	/**
	 * コンストラクタ
	 * 
	 * @param shelfList 本棚リスト
	 */
	public TopView(final List<Shelf> shelfList) {

		this(shelfList, new ArrayList<ShelfId>());
	}

	/**
	 * コンストラクタ
	 * 
	 * @param shelfList 本棚リスト
	 * @param checkedIdList 選択済み本棚IDリスト
	 */
	public TopView(final List<Shelf> shelfList, final List<ShelfId> checkedIdList) {

		shelfInfoList = createShelfInfoList(shelfList, checkedIdList);
	}

	/**
	 * 新規作成対象の本棚IDを取得する
	 * 
	 * @return
	 */
	public ShelfId getCreatingShelfId() {

		return ShelfId.getUnclassifiedShelfsId();
	}

	/**
	 * 本棚情報リストを作成する
	 * 
	 * @param shelfList 本棚リスト
	 * @param checkedIdList 選択済み本棚IDリスト
	 * @return
	 */
	private List<ShelfInfoBean> createShelfInfoList(final List<Shelf> shelfList, final List<ShelfId> checkedIdList) {

		if (shelfList == null) {

			return new ArrayList<>();
		}

		return ShelfInfoConverter.convertToViewInfoList(shelfList, checkedIdList);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ContentName getContentName() {

		return ContentName.TOP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PageTitle getTitle() {

		return PageTitle.TOP;
	}

	/**
	 * @return shelfInfoList
	 */
	public List<ShelfInfoBean> getShelfInfoList() {
		return shelfInfoList;
	}

}
