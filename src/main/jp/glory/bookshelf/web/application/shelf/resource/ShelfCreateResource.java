package jp.glory.bookshelf.web.application.shelf.resource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import jp.glory.bookshelf.common.expection.BusinessException;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepository;
import jp.glory.bookshelf.domain.shelf.sevice.ShelfUpdateService;
import jp.glory.bookshelf.domain.shelf.value.Name;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.constant.UrlParameterNameConst;
import jp.glory.bookshelf.web.application.common.response.ResponseCreator;
import jp.glory.bookshelf.web.application.shelf.view.ShelfEditView;

/**
 * 本棚編集リソース
 * 
 * @author Junki Yamada
 * 
 */
@Path(ResourceUrlConst.SHELF_CREATE)
@Stateless
public class ShelfCreateResource {

	/** 本棚リポジトリ */
	private final ShelfRepository repository;

	/**
	 * コンストラクタ
	 * 
	 * @param repository 本棚リポジトリ
	 */
	@Inject
	public ShelfCreateResource(final ShelfRepository repository) {

		this.repository = repository;
	}

	/**
	 * 初期表示
	 * 
	 * @return レスポンス
	 */
	@GET
	public Response initView() {

		final ShelfEditView view = new ShelfEditView(new Shelf(), false);
		final ResponseCreator creator = new ResponseCreator(view);

		return creator.getResponse();
	}

	/**
	 * 登録処理
	 * 
	 * @param name 名前
	 * @return レスポンス
	 */
	@POST
	public Response create(@FormParam(UrlParameterNameConst.SHELF_NAME) final String name) {

		final Shelf shelf = new Shelf();
		shelf.setName(new Name(name));

		final ShelfUpdateService service = createService();
		try {

			final ShelfId shelfId = service.createNewSelf(shelf);

			final String url = ResourceUrlConst.SHELF + shelfId.getValue();
			final ResponseCreator nextViewResponse = new ResponseCreator(url);

			return nextViewResponse.getResponse();
		} catch (final BusinessException e) {

			final ShelfEditView view = new ShelfEditView(shelf, false);
			view.addErrors(e.getErrors());

			final ResponseCreator errorResponse = new ResponseCreator(view);
			return errorResponse.getResponse();
		}

	}

	/**
	 * サービスオブジェクトを作成する
	 * 
	 * @return サービス
	 */
	private ShelfUpdateService createService() {

		return new ShelfUpdateService(repository);
	}
}
