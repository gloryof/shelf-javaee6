package jp.glory.bookshelf.web.application.top.resource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import jp.glory.bookshelf.domain.shelf.repository.ShelfRepository;
import jp.glory.bookshelf.domain.shelf.sevice.ShelfReferenceService;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.response.ResponseCreator;
import jp.glory.bookshelf.web.application.top.view.TopView;

/**
 * トップ画面表示リソース
 * 
 * @author Junki Yamada
 * 
 */
@Path(ResourceUrlConst.ROOT)
@Stateless
public class TopViewResource {

	/** 本棚リポジトリ */
	private final ShelfRepository repository;

	/**
	 * コンストラクタ
	 * 
	 * @param repository 本棚リポジトリ
	 */
	@Inject
	public TopViewResource(final ShelfRepository repository) {

		this.repository = repository;
	}

	/**
	 * 本棚リスト表示
	 * 
	 * @return レスポンス
	 */
	@GET
	public Response viewShelfList() {

		final ShelfReferenceService service = new ShelfReferenceService(repository);

		final TopView view = new TopView(service.findAll());

		final ResponseCreator creator = new ResponseCreator(view);

		return creator.getResponse();
	}
}
