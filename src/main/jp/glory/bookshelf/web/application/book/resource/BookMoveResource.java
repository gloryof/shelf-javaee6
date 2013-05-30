package jp.glory.bookshelf.web.application.book.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import jp.glory.bookshelf.common.expection.BusinessException;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepository;
import jp.glory.bookshelf.domain.shelf.sevice.ShelfReferenceService;
import jp.glory.bookshelf.domain.shelf.sevice.ShelfUpdateService;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.constant.UrlParameterNameConst;
import jp.glory.bookshelf.web.application.common.response.ResponseCreator;
import jp.glory.bookshelf.web.application.shelf.view.ShelfView;

/**
 * 本移動リソース
 * 
 * @author Junki Yamada
 * 
 */
@Stateless
@Path(ResourceUrlConst.BOOK_MOVE)
public class BookMoveResource {

	/** 本棚リポジトリ */
	private final ShelfRepository repository;

	/**
	 * コンストラクタ
	 * 
	 * @param repository 本棚リポジトリ
	 */
	@Inject
	public BookMoveResource(final ShelfRepository repository) {

		this.repository = repository;
	}

	/**
	 * 本を移動する
	 * 
	 * @param parentShelIdParam 親本棚ID
	 * @param toShelfIdParam 移動先本棚ID
	 * @param bookIdListParam 移動本IDリスト
	 * @return レスポンス
	 */
	@POST
	public Response move(@FormParam(UrlParameterNameConst.PARENT_SHELF_ID) final long parentShelIdParam,
			@FormParam(UrlParameterNameConst.TO_SHELF_ID) final long toShelfIdParam,
			@FormParam(UrlParameterNameConst.BOOK_ID) final List<Long> bookIdListParam) {

		final ShelfId fromShelfId = new ShelfId(parentShelIdParam);
		final ShelfId toShelfId = new ShelfId(toShelfIdParam);
		final List<BookId> bookIdList = new ArrayList<>();

		for (final long bookId : bookIdListParam) {

			bookIdList.add(new BookId(bookId));
		}

		final ShelfUpdateService service = createShelfUpdateService();

		try {
			service.move(fromShelfId, toShelfId, bookIdList);

			final String nextUrl = ResourceUrlConst.SHELF + parentShelIdParam;
			final ResponseCreator nextUrlResponse = new ResponseCreator(nextUrl);

			return nextUrlResponse.getResponse();
		} catch (final BusinessException e) {

			return createErrorResponse(fromShelfId, e);
		}
	}

	/**
	 * エラーレスポンスを作成する
	 * 
	 * @param shelfId 本棚ID
	 * @param bizExc 業務例外
	 * @return レスポンス
	 */
	private Response createErrorResponse(final ShelfId shelfId, final BusinessException bizExc) {

		final ShelfReferenceService service = createShelfReferenceService();
		final Shelf shelf = service.findById(shelfId);
		final List<Shelf> moveList = service.getMoveShelfLit(shelfId);

		final ShelfView shelfView = new ShelfView(shelf, moveList);
		shelfView.addErrors(bizExc.getErrors());

		final ResponseCreator errorResponse = new ResponseCreator(shelfView);

		return errorResponse.getResponse();
	}

	/**
	 * 本棚更新サービス作成
	 * 
	 * @return 本棚更新サービス
	 */
	private ShelfUpdateService createShelfUpdateService() {

		return new ShelfUpdateService(repository);
	}

	/**
	 * 本棚参照サービス作成
	 * 
	 * @return 本棚参照サービス
	 */
	private ShelfReferenceService createShelfReferenceService() {

		return new ShelfReferenceService(repository);
	}
}
