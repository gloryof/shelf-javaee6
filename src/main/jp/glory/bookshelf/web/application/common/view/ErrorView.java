package jp.glory.bookshelf.web.application.common.view;

import jp.glory.bookshelf.web.application.common.constant.ContentName;
import jp.glory.bookshelf.web.application.common.constant.PageTitle;

/**
 * エラーView
 * 
 * @author Junki Yamada
 * 
 */
public class ErrorView extends AbstractView {

	/** メッセージ */
	private final String message;

	/**
	 * コンストラクタ
	 * 
	 * @param message メッセージ
	 */
	public ErrorView(final String message) {

		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PageTitle getTitle() {

		return PageTitle.ERROR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ContentName getContentName() {

		return ContentName.ERROR;
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

}
