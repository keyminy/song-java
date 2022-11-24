package kr.co.songjava.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.songjava.mvc.domain.Board;
import kr.co.songjava.mvc.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	public List<Board> getList(){
		return boardRepository.getList();
	};
	
	public Board get(int boardSeq) {
		return boardRepository.get(boardSeq);
	};//단건 조회
	
	public void save(Board parameter) {
		//조회하여 리턴된 정보
		Board board = boardRepository.get(parameter.getBoardSeq());
		if(board == null) {
			boardRepository.save(parameter);			
		}else {
			boardRepository.update(parameter);
		}
	};
	
	public void update(Board board) {
		boardRepository.update(board);
	};
	
	public void delete(int boardSeq) {
		boardRepository.delete(boardSeq);
	};
}