package ks43team03.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ks43team03.common.FileUtils;
import ks43team03.dto.Board;
import ks43team03.dto.BoardComment;
import ks43team03.dto.BoardCtgCd;
import ks43team03.mapper.BoardMapper;
import ks43team03.mapper.FileMapper;

@Service
@Transactional
public class BoardService {
	
	private final BoardMapper boardMapper;
	private final FileMapper fileMapper;
	
	public BoardService(BoardMapper boardMapper, FileMapper fileMapper) {
		this.boardMapper = boardMapper;
		this.fileMapper = fileMapper;
	}
	
	private static final Logger log = LoggerFactory.getLogger(BoardService.class);
	
	/* 게시글 전체 목록 조회 */
	public Map<String, Object> getBoardList(int currentPage) {
		System.out.println("------------------------게시글 전체목록 조회 서비스-----------------------------");
		
		int rowPerPage = 9;
		
		double rowCount = boardMapper.getBoardCount();
		
		int lastPage = (int) Math.ceil(rowCount / rowPerPage);
		
		int startRow = (currentPage - 1) * rowPerPage;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("startRow", startRow);
		paramMap.put("rowPerPage", rowPerPage);
		
		int startPageNum = 1;
		int endPageNum = 10;
		
		if (lastPage > 10) {
			if (currentPage >= 6) {
				startPageNum = currentPage - 4;
				endPageNum = currentPage + 5;

				if (endPageNum >= lastPage) {
					startPageNum = lastPage - 9;
					endPageNum = lastPage;
				}
			}
		} else {
			endPageNum = lastPage;
		}
		
		log.info("paramMap : {}", paramMap);
		
		List<Map<String, Object>> boardList = boardMapper.getBoardList(paramMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("lastPage", 		lastPage);
		resultMap.put("boardList", 		boardList);
		resultMap.put("startPageNum", 	startPageNum);
		resultMap.put("endPageNum", 	endPageNum);
		
		System.out.println("------------------------게시글 전체목록 조회 서비스 끝-----------------------------");
		return resultMap;
	}
	
	/* 시설 내 게시글 목록 조회 */
	public List<Board> getBoardListByFacility(String facilityCd){
		System.out.println("------------------------ 시설 내 게시글 목록 조회 서비스-----------------------------");
		List<Board> boardListByFacility = boardMapper.getBoardListByFacility(facilityCd);
		System.out.println("------------------------ 시설 내 게시글 목록 조회 서비스 끝-----------------------------");
		return boardListByFacility;
	}
	
	/* 게시글 코드로 상세 조회   */
	public Board getBoardDetail(String boardPostCd) { 
		System.out.println("------------------------게시글 상세조회 서비스-----------------------------");
		Board board = boardMapper.getBoardDetail(boardPostCd); 
		System.out.println("------------------------게시글 상세조회 서비스 끝-----------------------------");
		return board; 
	}
	
	/* 게시글 코드로 답글 조회 */
	public List<BoardComment> getBoardCommentList(String boardPostCd){
		System.out.println("------------------------게시글 답글조회 서비스-----------------------------");
		List<BoardComment> boardCommentList = boardMapper.getBoardCommentList(boardPostCd); 
		System.out.println("------------------------게시글 답글조회 서비스 끝-----------------------------");
		return boardCommentList; 
	}
	
	/* 게시글 답글수 업데이트 */
	public int countComment(String boardPostCd) {
		System.out.println("------------------------게시글 답글수 업데이트---------------------------");
		return boardMapper.commentCountUpdate(boardPostCd);
	}
	
	/* 게시글 답글수 삭제 업데이트 */
	public int countCommentMinus(String boardPostCd) {
		System.out.println("------------------------게시글 답글수 삭제 업데이트---------------------------");
		return boardMapper.commentCountMinusUpdate(boardPostCd);
	}
	
	/* 게시글 등록 */
	public String addBoard(Board board, MultipartFile[] boardImgFile, String fileRealPath) { 
		System.out.println("------------------------게시글 등록 서비스-----------------------------");
		
		// 1. 파일 업로드
		// 2. 파일 업로드 성공시 파일 DB 인서트
		// 3. 게시글 인서트
 		// 4. 결과값 리턴
		
		boolean fileCheck = true;
		
		for (MultipartFile multipartFile : boardImgFile){
			if(!multipartFile.isEmpty()) {
				fileCheck = false;
			}
		}
		
		if(!fileCheck) {
			log.info("파일 있음"+ boardImgFile);
			
			//파일 업로드 위한 객체 생성 
			FileUtils fu = new FileUtils(boardImgFile, board.getUserId(), fileRealPath);
			List<Map<String, String>> dtoFileList = fu.parseFileInfo();
			
			// t_file 테이블에 삽입
			System.out.println(dtoFileList + "BoardService/addBoard");
			fileMapper.uploadFile(dtoFileList);
			
			// 게시글 등록 - 게시글 코드 selectKey로 담아 줌
			boardMapper.addBoard(board);
			log.info("add 이후 board : {}", board);
			
			String boardPostCd = board.getBoardPostCd();
			log.info("boardPostCd : {}", boardPostCd);
			
			// 릴레이션 테이블에 삽입
			List<Map<String, String>> relationFileList = new ArrayList<>();
			for(Map<String, String> m : dtoFileList) {
				m.put("boardPostCd", boardPostCd);
				relationFileList.add(m);
			}
			
			System.out.println(relationFileList);
			fileMapper.uploadRelationFileWithBoard(relationFileList);
			
			return boardPostCd;
 		}else {
 			
 			boardMapper.addBoard(board);
			log.info("add 이후 board : {}", board);
			
			String boardPostCd = board.getBoardPostCd();
			log.info("boardPostCd : {}", boardPostCd);
			
			return boardPostCd;
 		}
	}
	
	/* 게시글 카테고리 조회 */
	public List<BoardCtgCd> getBoardCtgCd(BoardCtgCd boardCtg){
		System.out.println("------------------------게시글 카테고리 조회 서비스-----------------------------");
		List<BoardCtgCd> boardCtgCd = boardMapper.getBoardCtgCd(boardCtg);
		System.out.println("------------------------게시글 카테고리 조회 서비스 끝-----------------------------");
		return boardCtgCd;
	}
	
	/* 게시글 수정 */
	public int modifyBoard(Board board) { 
		System.out.println("---------------------게시글 수정 서비스-------------------");
		int result = boardMapper.modifyBoard(board); 
		log.info("board : {}", board);
		System.out.println("---------------------게시글 수정 서비스 끝-------------------");
		return result;
	}
	
	/* 게시글 조회수 증가 */
	public int boardViewUpdate(String boardPostCd) { 
		System.out.println("------------------------게시글 조회수 증가 서비스-----------------------------");
		return boardMapper.boardViewUpdate(boardPostCd); 
	}
	
	/* 게시글 답글등록 */
	public int addBoardComment(BoardComment boardComment) {
		System.out.println("------------------------게시글 답글 등록 서비스-----------------------------");
		int result =  boardMapper.addBoardComment(boardComment); 
		System.out.println("------------------------게시글 답글 등록 서비스 끝-----------------------------");
		return result;
	}
	
	/* 게시글 답글 삭제 */ 
	public int removeComment(String boardCommentCode) { 
		System.out.println("------------------------게시글 답글 삭제 서비스-----------------------------");
		int result = boardMapper.removeComment(boardCommentCode); 
		System.out.println("------------------------게시글 답글 삭제 서비스 끝-----------------------------");
		return result; 
	}
	
	/* 게시글 답글 전체 삭제 */
	public int removeCommentByPostCd(String boardPostCd) {
		System.out.println("------------------------게시글 답글 전체 삭제 서비스-----------------------------");
		int result = boardMapper.removeCommentByPostCd(boardPostCd);
		System.out.println("------------------------게시글 답글 전체 삭제 서비스 끝-----------------------------");
		return result;
	}
	
	/* 게시글 삭제 */
	public int removeBoard(String boardPostCd) { 
		System.out.println("------------------------게시글 삭제 서비스-----------------------------");
		int result = boardMapper.removeBoard(boardPostCd); 
		System.out.println("------------------------게시글 삭제 서비스 끝-----------------------------");
		return result; 
	}
}
