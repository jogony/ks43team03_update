package ks43team03.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ks43team03.dto.Board;
import ks43team03.dto.BoardComment;
import ks43team03.dto.BoardCtgCd;

@Mapper
public interface BoardMapper {

	//게시글 전체 목록 조회 
	public List<Map<String, Object>> getBoardList(Map<String, Object> paramMap);
	
	//시설 내 게시글 목록 조회
	public List<Board> getBoardListByFacility(String facilityCd);
	
	//게시글 코드로 상세 조회 
	public Board getBoardDetail(String boardPostCd);
	
	// 게시글 등록
	public int addBoard(Board board);
	
	// 게시글 카테고리 조회
	public List<BoardCtgCd> getBoardCtgCd(BoardCtgCd boardCtg);
	
	// 게시글 수정 
	public int modifyBoard(Board board);
	
	// 게시글 조회수 업데이트 
	public int boardViewUpdate(String boardPostCd);
	
	//게시글 답글수 업데이트
	public int commentCountUpdate(String boardPostCd);
	
	//게시글 답글수 삭제 업데이트
	public int commentCountMinusUpdate(String boardPostCd);
	
	// 게시글 답글 등록 
	public int addBoardComment(BoardComment boardComment);
	
	// 게시글 답글 조회 
	public List<BoardComment> getBoardCommentList(String boardPostCd);
	
	// 게시글 답글 삭제 
	public int removeComment(String boardCommentCode);
	
	// 게시글 답글 전체 삭제
	public int removeCommentByPostCd(String boardPostCd);
	
	// 게시글 삭제
	public int removeBoard(String boardPostCd);

	// 게시글 테이블 총 row(튜플) 수
	public int getBoardCount();
}
