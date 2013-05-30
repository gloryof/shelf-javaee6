package jp.glory.bookshelf.web.application.shelf.resource;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import jp.glory.bookshelf.common.expection.BusinessException;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepository;
import jp.glory.bookshelf.domain.shelf.sevice.ShelfReferenceService;
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
@Path(ResourceUrlConst.SHELF_EDIT + "{" + UrlParameterNameConst.SHELF_ID + "}")
@Stateless
public class ShelfEditResource {

	/** 本棚リポジトリ */
	private final ShelfRepository repository;

	/**
	 * コンストラクタ
	 * 
	 * @param repository 本棚リポジトリ
	 */
	@Inject
	public ShelfEditResource(final ShelfRepository repository) {

		this.repository = repository;
	}

	/**
	 * 初期表示
	 * 
	 * @param shelfIdParam 本棚ID
	 * @return レスポンス
	 */
	@GET
	public Response initView(@PathParam(UrlParameterNameConst.SHELF_ID) final long shelfIdParam) {

		final ShelfReferenceService service = createReferenceService();
		final ShelfId shelfId = new ShelfId(shelfIdParam);

		final ShelfEditView view = new ShelfEditView(service.findById(shelfId), true);
		final ResponseCreator creator = new ResponseCreator(view);

		return creator.getResponse();
	}

	/**
	 * 更新処理
	 * 
	 * @param pathParamShelfId 本棚ID（パスパラメータ）
	 * @param formParamShelfId 本棚ID（フォームパラメータ）
	 * @param name 名前
	 * @return レスポンス
	 */
	@POST
	public Response update(@PathParam(UrlParameterNameConst.SHELF_ID) final long pathParamShelfId,
			@FormParam(UrlParameterNameConst.SHELF_ID) final long formParamShelfId,
			@FormParam(UrlParameterNameConst.SHELF_NAME) final String name) {

		if (pathParamShelfId != formParamShelfId) {

			final ResponseCreator badRequestResponse = new ResponseCreator(Response.Status.BAD_REQUEST);
			return badRequestResponse.getResponse();
		}

		final ShelfUpdateService service = createUpdateService();

		final Shelf shelf = new Shelf(new ShelfId(pathParamShelfId), new ArrayList<Book>());
		shelf.setName(new Name(name));

		try {
			service.updateShelfName(shelf);
		} catch (final BusinessException e) {

			final ShelfEditView view = new ShelfEditView(shelf, true);
			view.addErrors(e.getErrors());

			final ResponseCreator errorResponse = new ResponseCreator(view);
			return errorResponse.getResponse();
		}

		final String url = ResourceUrlConst.SHELF + pathParamShelfId;
		final ResponseCreator successResponse = new ResponseCreator(url);

		return successResponse.getResponse();
	}

	/**
	 * 参照サービスオブジェクトを作成する
	 * 
	 * @return 参照サービス
	 */
	private ShelfReferenceService createReferenceService() {

		return new ShelfReferenceService(repository);
	}

	/**
	 * 更新サービスオブジェクトを作成する
	 * 
	 * @return 更新サービス
	 */
	private ShelfUpdateService createUpdateService() {

		return new ShelfUpdateService(repository);
	}
}
