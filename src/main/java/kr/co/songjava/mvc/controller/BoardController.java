package kr.co.songjava.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.songjava.configuration.exception.BaseException;
import kr.co.songjava.configuration.http.BaseResponse;
import kr.co.songjava.configuration.http.BaseResponseCode;
import kr.co.songjava.mvc.domain.Board;
import kr.co.songjava.mvc.service.BoardService;

@RestController
@RequestMapping("/board")
@Api(tags="게시판 API")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping
	@ApiOperation(value="목록 조회",notes = "게시물 목록 정보를 조회할 수 있습니다.")
	public BaseResponse<List<Board>> getList(){
		return new BaseResponse<>(boardService.getList());
	};
	@GetMapping("/{boardSeq}")
	@ApiOperation(value="상세 조회",notes = "게시물 번호에 해당하는 상세 정보를 조회할 수 있습니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "boardSeq",value="게시물 번호",example="1")
	})
	public BaseResponse<Board> get(@PathVariable int boardSeq) {
		Board board = boardService.get(boardSeq);
		//null처리
		if(board == null) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL,new String[] {"게시물"});
		}
		return new BaseResponse<>(boardService.get(boardSeq));
	};//단건 조회
	
	/**
	 * 등록/수정 처리
	 * @param parameter
	 */
	@PutMapping("/save")
	@ApiOperation(value="등록 / 수정 처리",notes = "신규 게시물 저장 및 기존 게시물 업데이트가 가능합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "boardSeq",value="게시물 번호",example="1"),
		@ApiImplicitParam(name = "title",value="제목",example="spring"),
		@ApiImplicitParam(name = "contents",value="내용",example="spring 강좌"),
	})
	public BaseResponse<Integer> save(Board parameter) {
		//null처리 하기
		//제목 필수 체크
		if(StringUtils.isEmpty(parameter.getTitle())) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED,new String[] {"title" , "제목"});
		}
		//내용 필수 체크
		if(StringUtils.isEmpty(parameter.getContents())) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED,new String[] {"contents" , "내용"});
		}
		
		boardService.save(parameter);
		return new BaseResponse<>(parameter.getBoardSeq());
	};

	
	/**
	 * 삭제 처리
	 * @param boardSeq
	 */
	@DeleteMapping("/delete/{boardSeq}")
	@ApiOperation(value="삭제 처리",notes = "게시물 번호에 해당하는 정보 삭제")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "boardSeq",value="게시물 번호",example="1"),
	})
	public BaseResponse<Boolean> delete(@PathVariable int boardSeq) {
		Board board = boardService.get(boardSeq);
		if(board == null) {
			return new BaseResponse<Boolean>(false);
		}
		boardService.delete(boardSeq);
		return new BaseResponse<Boolean>(true);
	};
	
}
