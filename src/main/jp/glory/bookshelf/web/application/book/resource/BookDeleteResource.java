package jp.glory.bookshelf.web.application.book.resource;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import jp.glory.bookshelf.common.expection.BusinessException;
import jp.glory.bookshelf.domain.book.repository.BookRepository;
import jp.glory.bookshelf.domain.book.service.BookService;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepository;
import jp.glory.bookshelf.domain.shelf.sevice.ShelfReferenceService;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.constant.UrlParameterNameConst;
import jp.glory.bookshelf.web.application.common.response.ResponseCreator;
import jp.glory.bookshelf.web.application.common.view.converter.BookIdConverter;
import jp.glory.bookshelf.web.application.shelf.view.ShelfView;

/**
 * 本削除リソース
 * 
 * @author Junki Yamada
 * 
 */
@Stateless
@Path(ResourceUrlConst.BOOK_DELETE)
public class BookDeleteResource {

	/** 本棚リポジトリ */
	private final ShelfRepository shelfRepository;

	/** 本リポジトリ */
	private final BookRepository bookRepository;

	/**
	 * コンストラクタ
	 * 
	 * @param shelfRepository 本棚リポジトリ
	 * @param bookRepository 本リポジトリ
	 */
	@Inject
	public BookDeleteResource(final ShelfRepository shelfRepository, final BookRepository bookRepository) {

		this.shelfRepository = shelfRepository;
		this.bookRepository = bookRepository;
	}

	/**
	 * 本を削除する
	 * 
	 * @param parentShelfIdParam 親本棚ID
	 * @param bookIdListParam 削除対象本リスト
	 * @return レスポンス
	 */
	@POST
	public Response deleteBooks(@FormParam(UrlParameterNameConst.PARENT_SHELF_ID) final Long parentShelfIdParam,
			@FormParam(UrlParameterNameConst.BOOK_ID) final List<Long> bookIdListParam) {

		final BookService bookService = createBookService();

		final List<BookId> bookIdList = BookIdConverter.convertToBookList(bookIdListParam);

		try {

			bookService.deleteBooks(bookIdList);
		} catch (final BusinessException e) {

			return createErrorResponse(parentShelfIdParam, bookIdListParam, e);
		}

		return createSuccessResponse(parentShelfIdParam);
	}

	/**
	 * 削除成功時のレスポンスを作成する
	 * 
	 * @param shelfId 本棚ID
	 * @return レスポンス
	 */
	private Response createSuccessResponse(final Long shelfId) {

		final String successUrl = ResourceUrlConst.SHELF + shelfId;
		final ResponseCreator successResponse = new ResponseCreator(successUrl);

		return successResponse.getResponse();
	}

	/**
	 * エラーレスポンスを作成する
	 * 
	 * @param longShelfId 本棚ID
	 * @param bizException 業務例外
	 * @return レスポンス
	 */
	private Response createErrorResponse(final Long longShelfId, final List<Long> checkedIdList,
			final BusinessException bizException) {

		final ShelfReferenceService service = createShelfService();
		final ShelfId shelfId = new ShelfId(longShelfId);
		final List<Shelf> moveList = service.getMoveShelfLit(shelfId);
		final List<BookId> bookIdList = BookIdConverter.convertToBookList(checkedIdList);

		final ShelfView view = new ShelfView(service.findById(shelfId), moveList, bookIdList);
		view.addErrors(bizException.getErrors());
		final ResponseCreator creator = new ResponseCreator(view);

		return creator.getResponse();
	}

	/**
	 * 本サービス作成
	 * 
	 * @return 本サービス
	 */
	private BookService createBookService() {

		return new BookService(bookRepository);
	}

	/**
	 * 本棚参照サービス作成
	 * 
	 * @return 本棚参照サービス
	 */
	private ShelfReferenceService createShelfService() {

		return new ShelfReferenceService(shelfRepository);
	}
}
