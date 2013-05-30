package jp.glory.bookshelf.web.application.shelf.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import jp.glory.bookshelf.common.expection.BusinessException;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepository;
import jp.glory.bookshelf.domain.shelf.sevice.ShelfReferenceService;
import jp.glory.bookshelf.domain.shelf.sevice.ShelfUpdateService;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.response.ResponseCreator;
import jp.glory.bookshelf.web.application.common.view.converter.ShelfIdConverter;
import jp.glory.bookshelf.web.application.top.view.TopView;

/**
 * 本棚削除リソース
 * 
 * @author Junki Yamada
 * 
 */
@Path(ResourceUrlConst.SHELF_DELETE)
@Stateless
public class ShelfDeleteResource {

	/** 本棚リポジトリ */
	private final ShelfRepository repository;

	/**
	 * コンストラクタ
	 * 
	 * @param service 本棚更新サービス
	 */
	@Inject
	public ShelfDeleteResource(final ShelfRepository repository) {

		this.repository = repository;
	}

	/**
	 * 本棚を削除する
	 * 
	 * @param deleteIdList 削除本棚IDリスト
	 * @return レスポンス
	 */
	@POST
	public Response deleteShelfs(@FormParam("deleteShelfId") final List<Long> deleteIdList) {

		try {

			final List<ShelfId> shelfIdList = convertShelfIdList(deleteIdList);
			createUpdateService().delete(shelfIdList);

			return createSuccessResponse();
		} catch (BusinessException e) {

			return createErrorResponse(e, deleteIdList);
		}
	}

	/**
	 * 正常時レスポンスを作成する
	 * 
	 * @return レスポンス
	 */
	private Response createSuccessResponse() {

		final ResponseCreator complateResponse = new ResponseCreator(ResourceUrlConst.ROOT);

		return complateResponse.getResponse();
	}

	/**
	 * エラー時レスポンスを作成する
	 * 
	 * @param bizException 業務例外
	 * @param deleteIdList 本棚IDリスト
	 * @return レスポンス
	 */
	private Response createErrorResponse(final BusinessException bizException, final List<Long> deleteIdList) {

		final List<Shelf> shelfList = createReferenceService().findAll();
		final List<ShelfId> shelfIdList = ShelfIdConverter.convertToShelfIdList(deleteIdList);
		final TopView view = new TopView(shelfList, shelfIdList);

		view.addErrors(bizException.getErrors());

		final ResponseCreator response = new ResponseCreator(view);

		return response.getResponse();
	}

	/**
	 * 本棚IDリストに変換する
	 * 
	 * @param deleteIdList 本棚ID数値リスト
	 * @return 本棚IDリスト
	 */
	private List<ShelfId> convertShelfIdList(final List<Long> deleteIdList) {

		final List<ShelfId> shelfIdList = new ArrayList<>();
		for (final Long deleteId : deleteIdList) {

			shelfIdList.add(new ShelfId(deleteId));
		}

		return shelfIdList;
	}

	/**
	 * 本棚更新サービスを作成する
	 * 
	 * @return 本棚更新サービス
	 */
	private ShelfUpdateService createUpdateService() {

		return new ShelfUpdateService(repository);
	}

	/**
	 * 本棚参照サービスを作成する
	 * 
	 * @return 本棚参照サービス
	 */
	private ShelfReferenceService createReferenceService() {

		return new ShelfReferenceService(repository);
	}
}
