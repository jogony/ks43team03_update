package ks43team03.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ks43team03.dto.Board;
import ks43team03.dto.BoardComment;
import ks43team03.dto.BoardCtgCd;
import ks43team03.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	
	/* 게시글 전체 목록 조회 */
	@GetMapping("/boardList")
	public String getBoardList(Model model
							,@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage) {
		System.out.println("------------------------게시글 전체 목록 조회-----------------------------");
		
		Map<String, Object> resultMap =  boardService.getBoardList(currentPage);
		
		log.info("resultMap 받기 : {}", resultMap);
		log.info("resultMap.get(\"boardList\") : {}",resultMap.get("boardList"));
		
		model.addAttribute("title", 				"게시판");
		model.addAttribute("resultMap", 			resultMap);
		model.addAttribute("currentPage", 			currentPage);
		model.addAttribute("boardList",				resultMap.get("boardList"));
		model.addAttribute("lastPage", 				resultMap.get("lastPage"));
		model.addAttribute("startPageNum", 			resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", 			resultMap.get("endPageNum"));
		
		System.out.println("------------------------게시글 전체 목록 조회 끝-----------------------------");
		
		return "board/boardList";
	}
	
	/* 게시글 코드로 상세 조회 */
	/* 게시물 답글 조회  */
	@GetMapping("/boardDetail")
	public String getBoardDetail(Model model 
								,@RequestParam(value = "boardPostCd", required = false) String boardPostCd
								,@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage) {
		System.out.println("------------------------게시글 상세조회-----------------------------");
		Board board = boardService.getBoardDetail(boardPostCd);
		
		List<BoardComment> boardCommentList = boardService.getBoardCommentList(boardPostCd);
		boardService.boardViewUpdate(boardPostCd);
		
		model.addAttribute("title", 			"게시글 상세보기");
		model.addAttribute("board", 			board);
		model.addAttribute("boardCommentList",	boardCommentList);
		
		log.info("boardPostcd : {}", boardPostCd);
		log.info("board : {}", board);
		log.info("boardCommentList : {}", boardCommentList);
		
		Map<String, Object> resultMap =  boardService.getBoardList(currentPage);
		model.addAttribute("resultMap", 			resultMap);
		model.addAttribute("currentPage", 			currentPage);
		model.addAttribute("boardList",				resultMap.get("boardList"));
		model.addAttribute("lastPage", 				resultMap.get("lastPage"));
		model.addAttribute("startPageNum", 			resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", 			resultMap.get("endPageNum"));
		
		System.out.println("------------------------게시글 상세조회 끝-----------------------------");
		return "board/boardDetail";
	}
	
	/*
	@GetMapping("/download")
	public void download(HttpServletResponse response
						,@RequestParam MultipartFile[] boardImgFile
						,TFile tFile) {
	
		// 직접 파일 정보를 변수에 저장해 놨지만, 이 부분이 db에서 읽어왔다고 가정한다.
		String fileName = tFile.getReFileName();
		String saveFileName = tFile.getOriginalFileName(); // 맥일 경우 "/tmp/connect.png" 로 수정
		int fileLength = tFile.getFileSize();
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Length", "" + fileLength);
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
	
		try(
			FileInputStream fis = new FileInputStream(saveFileName);
			OutputStream out = response.getOutputStream();
		){
			int readCount = 0;
			byte[] buffer = new byte[1024];
			while((readCount = fis.read(buffer)) != -1){
				out.write(buffer,0,readCount);
			}
		}catch(Exception ex){
		  throw new RuntimeException("file Save Error");
		}
	}
	*/
	
	/* 게시글 등록 처리 */
	@PostMapping("/addBoard")
	@ResponseBody
	public String addBoard(Board board
						  ,RedirectAttributes reAttr
					 	  ,@RequestParam MultipartFile[] boardImgFile
						  ,HttpServletRequest request) {
		System.out.println("------------------------게시글 등록 처리-----------------------------");
		
		log.info("회원이 입력한 게시글 내용 : {}", board); 
		//log.info("boardImgFile : {}", boardImgFile[0].getOriginalFilename());
		
		String serverName = request.getServerName();
		String fileRealPath = "";
		
		if("localhost".equals(serverName)) {
			// server 가 localhost 일때 접근
			fileRealPath = System.getProperty("user.dir") + "/src/main/resources/static/";
			System.out.println(System.getProperty("user.dir"));
			//fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}else {
			//배포용 주소
			fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}
		
		String boardPostCd = boardService.addBoard(board, boardImgFile, fileRealPath);
		log.info("boardPostCd : {}", boardPostCd);
		
		reAttr.addAttribute("boardPostCd", boardPostCd);
		
		System.out.println("------------------------게시글 등록 처리 끝-----------------------------");

		return "/board/boardDetail?boardPostCd="+boardPostCd;
	}
	
	/* 게시글 등록 페이지 */
	@GetMapping("/addBoard")
	public String addBoard(Board board, BoardCtgCd boardCtg, Model model) {
		System.out.println("------------------------게시글 등록 페이지-----------------------------");
		
		List<BoardCtgCd> boardCtgCdList = boardService.getBoardCtgCd(boardCtg);
		
		model.addAttribute("title", "게시글 등록");
		model.addAttribute("boardCtgCdList", boardCtgCdList);
		
		log.info("boardCtgCdList : {}", boardCtgCdList);
		System.out.println("------------------------게시글 등록 페이지 끝-----------------------------");
		
		return "board/addBoard";
	}
	
	/* 게시글수정 처리 */
	@PostMapping("/modifyBoard")
	public String modifyBoard(Board board, Model model) {
		System.out.println("------------------------게시글 수정 처리------------------------");
		
		int result = boardService.modifyBoard(board);
		
		log.info("result : {}", result);
		System.out.println("------------------------게시글 수정 처리 끝------------------------");
		
		return "redirect:/board/boardList";
	}
	
	/* 게시글수정 페이지 */
	@GetMapping("/modifyBoard")
	public String modifyBoard(Model model, BoardCtgCd boardCtg,
							@RequestParam(name = "boardPostCd", required = false) String boardPostCd) {
		System.out.println("---------------------게시글 수정 페이지-----------------------");
		
		log.info("받아온 boardPostCd : {}", boardPostCd);
		
		Board board = boardService.getBoardDetail(boardPostCd);
		
		List<BoardCtgCd> boardCtgCdList = boardService.getBoardCtgCd(boardCtg);
		
		model.addAttribute("title", "게시글 수정");
		model.addAttribute("board", board);
		model.addAttribute("boardCtgCdList", boardCtgCdList);

		System.out.println("---------------------게시글 수정 페이지 끝-----------------------");

		return "board/modifyBoard";
	}
	
	/* 게시물 답글 등록 */
	@PostMapping(value = "/addBoardComment", produces = "application/json")
	public String addBoardComment(BoardComment boardComment, Board board, RedirectAttributes attr
								,@RequestParam(name = "boardPostCd", required = false) String boardPostCd) {
		System.out.println("------------------------게시글 답글 등록-----------------------------");
		
		attr.addAttribute("boardPostCd", boardComment.getBoardPostCd());
		attr.addAttribute("boardPostContentDetail", boardComment.getBoardPostContentDetail());
		
		log.info("답글 : {}", boardComment.getBoardPostContentDetail());
		
		boardService.countComment(boardPostCd);
		boardService.addBoardComment(boardComment);
		System.out.println("------------------------게시글 답글 등록 끝-----------------------------");
		
		return "redirect:/board/boardDetail";
	}
	
	/* 게시글 답글 삭제 */
	@GetMapping("/removeComment")
	public String deleteComment(Model model, BoardComment boardComment, RedirectAttributes attr,
								@RequestParam(name = "boardPostCommentCd", required = false) String boardPostCommentCd
								,@RequestParam(name = "boardPostCd", required = false) String boardPostCd) {
		System.out.println("------------------------게시글 답글 삭제-----------------------------");
		
		log.info("삭제할 답글 코드 : {}", boardPostCommentCd);
		int result = boardService.removeComment(boardPostCommentCd);
		
		log.info("result : {}", result);
		log.info("boardPostCd : {}", boardPostCd);
		
		attr.addAttribute("boardPostCd", boardPostCd);
		
		boardService.countCommentMinus(boardPostCd);
		System.out.println("------------------------게시글 답글 삭제 끝-----------------------------");

		return "redirect:/board/boardDetail";
	}
	
	/* 게시글 삭제 */
	@GetMapping("/removeBoard")
	public String removeBoard(Board board) {
		System.out.println("------------------------게시글 삭제-----------------------------");
		String boardPostCd = board.getBoardPostCd();

		int resultBC = boardService.removeCommentByPostCd(boardPostCd);
		int resultB = boardService.removeBoard(boardPostCd);
		
		System.out.println("------------------------게시글 삭제 끝-----------------------------");

		return "redirect:/board/boardList";
	}
	
}
