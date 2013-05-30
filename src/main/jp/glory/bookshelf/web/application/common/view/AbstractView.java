package jp.glory.bookshelf.web.application.common.view;

import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.web.application.common.constant.ContentName;
import jp.glory.bookshelf.web.application.common.constant.PageTitle;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;

import com.sun.jersey.api.view.Viewable;

/**
 * Viewベースクラス
 * 
 * @author Junki Yamada
 * 
 */
public abstract class AbstractView {

	/** エラーリスト */
	private final ValidateErrors errors = new ValidateErrors();

	/**
	 * テンプレートパスを取得する
	 * 
	 * @return テンプレート名
	 */
	public final String getTemplatePath() {

		return "/template/baseLayout.vm";
	}

	/**
	 * エラーエリアのパスを取得する
	 * 
	 * @return エラーエリアのパス
	 */
	public final String getErrorAreaPath() {

		return "/common/errorMessageArea.vm";
	}

	/**
	 * コンテンツパスを取得する
	 * 
	 * @return コンテンツ名
	 */
	public final String getContentPath() {

		return "/content/" + getContentName().getValue();
	}

	/**
	 * コンテキストルートを取得する
	 * 
	 * @return コンテキストルート
	 */
	public final String getContextRoot() {

		return ResourceUrlConst.CONTEXT_ROOT;
	}

	/**
	 * Viewableオブジェクトを作成する
	 * 
	 * @return Viewableオブジェクト
	 */
	public final Viewable createViewable() {

		Viewable view = new Viewable(getTemplatePath(), this);
		return view;
	}

	/**
	 * エラーを追加数
	 * 
	 * @param aditinalErrors エラーリスト
	 */
	public void addErrors(final ValidateErrors aditinalErrors) {
		errors.addAll(aditinalErrors);
	}

	/**
	 * @return errors
	 */
	public ValidateErrors getErrors() {
		return errors;
	}

	/**
	 * タイトルを取得する
	 * 
	 * @return タイトル
	 */
	public abstract PageTitle getTitle();

	/**
	 * コンテンツ名を取得する
	 * 
	 * @return コンテンツ名
	 */
	protected abstract ContentName getContentName();
}
