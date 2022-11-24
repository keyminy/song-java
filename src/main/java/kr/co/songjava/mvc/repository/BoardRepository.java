package kr.co.songjava.mvc.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.songjava.mvc.domain.Board;

@Repository
public interface BoardRepository {

	List<Board> getList();
	
	Board get(int boardSeq);//단건 조회
	
	void save(Board board);
	
	void update(Board board);
	
	void delete(int boardSeq);
}
