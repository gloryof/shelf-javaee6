package jp.glory.bookshelf.web.application.shelf.resource;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepository;
import jp.glory.bookshelf.domain.shelf.sevice.ShelfReferenceService;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.constant.UrlParameterNameConst;
import jp.glory.bookshelf.web.application.common.response.ResponseCreator;
import jp.glory.bookshelf.web.application.shelf.view.ShelfView;

/**
 * 本棚表示リソース
 * 
 * @author Junki Yamada
 * 
 */
@Path(ResourceUrlConst.SHELF + "{" + UrlParameterNameConst.SHELF_ID + "}")
@Stateless
public class ShelfViewResource {

	/** 本棚リポジトリ */
	private final ShelfRepository repository;

	/**
	 * コンストラクタ
	 * 
	 * @param repository 本棚リポジトリ
	 */
	@Inject
	public ShelfViewResource(final ShelfRepository repository) {

		this.repository = repository;
	}

	/**
	 * 初期表示
	 * 
	 * @return レスポンス
	 */
	@GET
	public Response initView(@PathParam(UrlParameterNameConst.SHELF_ID) final long shelfIdParam) {

		final ShelfReferenceService service = new ShelfReferenceService(repository);
		final ShelfId shelfId = new ShelfId(shelfIdParam);
		final List<Shelf> moveList = service.getMoveShelfLit(shelfId);

		final ShelfView view = new ShelfView(service.findById(shelfId), moveList);
		final ResponseCreator creator = new ResponseCreator(view);

		return creator.getResponse();
	}

}
