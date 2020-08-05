package kr.co.bw.board.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.google.gson.Gson;

import kr.co.bw.board.model.service.BoardServiceImpl;
import kr.co.bw.board.model.vo.BoardCommentVO;
import kr.co.bw.board.model.vo.BoardData;
import kr.co.bw.board.model.vo.BoardReplyVO;
import kr.co.bw.board.model.vo.BoardVO;
import kr.co.bw.board.model.vo.BoardViewData;

@Controller
@RequestMapping("/bw/board")
public class BoardController {
	@Autowired
	@Qualifier("boardService")
	private BoardServiceImpl service;

	@RequestMapping("/boardList.do")
	public String boardList(Model model, HttpServletRequest request) {
		int reqPage = 0;
		try {
			reqPage = Integer.parseInt(request.getParameter("reqPage"));
		} catch (Exception e) {
			reqPage = 1;
		}
		
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		System.out.println(type);
		System.out.println(keyword);		
		
		BoardData data = service.selectBoardList(reqPage, type, keyword);
		
		System.out.println(data.getList());
		
		
		request.setAttribute("list", data.getList());
		request.setAttribute("pageNavi", data.getPageNavi());
		request.setAttribute("reqPage", reqPage);
		request.setAttribute("type", type);
		request.setAttribute("keyword", keyword);
		request.setAttribute("totalPage", data.getTotalPage());
		request.setAttribute("totalCount", data.getTotalCount());
		request.setAttribute("numPerPage", data.getNumPerPage());

		return "board/boardList";
	}	
	
	@RequestMapping("/contentPage.do")
	public String contentPage(Model model, int boardNo) {
		BoardViewData data = service.boardCommentList(boardNo);
		model.addAttribute("b", data.getB());
		model.addAttribute("commentList", data.getCommentList());
		
		return "board/contentPage";
	}
	
	@RequestMapping(value="/boardWrite.do" ,method = RequestMethod.POST)
	public String boardWrite(BoardVO boardVo, MultipartFile uploadfile) {
		logger.info("upload() POST 호출");
	    logger.info("파일 이름: {}", uploadfile.getOriginalFilename());
	    logger.info("파일 크기: {}", uploadfile.getSize());
	    boardVo.setFilename(saveFile(uploadfile));
	    boardVo.setFilepath(UPLOAD_PATH);
		int result = service.boardWirte(boardVo);
		if (result == 1) {
			System.out.println("글쓰기 성공");
		} else {
			System.out.println("글쓰기 실패");
		}
		return "redirect:/bw/board/boardList.do";
	}
	
	public org.slf4j.Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	private static final String UPLOAD_PATH = "C:\\Users\\lance";
	
	private String saveFile(MultipartFile file){
	    // 파일 이름 변경
	    UUID uuid = UUID.randomUUID();
	    String saveName = uuid + "_" + file.getOriginalFilename();

	    logger.info("saveName: {}",saveName);

	    // 저장할 File 객체를 생성(껍데기 파일)ㄴ
	    File saveFile = new File(UPLOAD_PATH,saveName); // 저장할 폴더 이름, 저장할 파일 이름

	    try {
	        file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }

	    return saveName;
	} // end saveFile(
	
	@RequestMapping(value = "/boardUpdate.do")
	public String boardUpdate(BoardVO boardVo){
		System.out.println("보드업데이트 탐");
		System.out.println(boardVo.getBoardNo());
		int result = service.boardUpdate(boardVo);
		if (result == 1) {
			System.out.println("수정성공");
		return "redirect:/bw/board/contentPage.do?boardNo="+boardVo.getBoardNo();
		} else {
			System.out.println("수정실패");
		return "redirect:/bw/board/boardUpdateFrm.do?boardNo="+boardVo.getBoardNo();
		}
	}
	
	@RequestMapping("/boardWriteFrm.do")
	public String boardWriteFrm() {
		return "board/boardWrite";
	}
	
	@ResponseBody
	@RequestMapping(value="/boardDelete.do", produces = "text/html; charset=utf-8")
	public String boardDelete(BoardVO boardVo) {
		int result = service.boardDelete(boardVo);
		if (result == 1) {
			System.out.println("삭제성공");
			return "1";
		} else {
			System.out.println("삭제실패");
			return "0";
		}
	}
	
	@RequestMapping(value="boardUpdateFrm.do")
	public String boardUpdateFrm(Model model, int boardNo) {
		BoardVO boardVo = service.boardUpdateFrm(boardNo);
		model.addAttribute("boardVo", boardVo);
		return "board/boardUpdate";
	}

	@ResponseBody
	@RequestMapping(value="pwCheck.do", produces = "text/html; charset=utf-8")
	public String pwCheck(BoardVO boardVo) {
		System.out.println(boardVo.getBoardNo() + boardVo.getBoardPw());
		BoardVO result = service.pwCheck(boardVo);
		if (result != null) {
			System.out.println("비밀번호가 확인 되었습니다.");
			return "1";
		} else{
			System.out.println("비밀번호가 틀렸습니다.");
			return "0";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="boardCommentInsert.do", produces = "text/html; charset=utf-8")
	public String boardCommentInsert(BoardCommentVO comment) {
		int result = service.boardCommentInsert(comment);
		if (result == 1) {
			System.out.println("코멘트 입력 완료");
			return "1";
		} else{
			System.out.println("코멘트 입력 실패");
			return "0";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="selectOneComment.do", produces = "application/json; charset=utf-8")
	public BoardCommentVO selectOneComment(int boardNo) {
		BoardCommentVO boardComment = service.boardOneComment(boardNo);
		return boardComment;
	}
	
	@ResponseBody
	@RequestMapping(value="boardCommentList.do", produces = "application/json; charset=utf-8")
	public String boardCommentList(HttpServletRequest request, int boardNo) {
		BoardViewData data = service.boardCommentList(boardNo);
		request.setAttribute("b", data.getB());
		request.setAttribute("commentList", data.getCommentList());
		return new Gson().toJson(data.getCommentList());
	}
	
	@RequestMapping("/replyWriteFrm.do")
	public String replyWriteFrm(Model model, int boardNo) {
		BoardVO reply = service.replyInfo(boardNo);
		model.addAttribute("reply", reply);
		model.addAttribute("boardNo", boardNo);
		return "board/replyWrite";
	}
	
	@RequestMapping(value="/replyWrite.do")
	public String replyWrite(BoardVO boardVo) {
		System.out.println(boardVo);
		int result = service.replyInsert(boardVo);
		if (result == 1) {
			System.out.println("글쓰기 성공");
		} else {
			System.out.println("글쓰기 실패");
		}
		return "redirect:/bw/board/boardList.do";
	}
	
	@ResponseBody
	@RequestMapping("/commentPwCheck.do")
	public String commentPwCheck(BoardCommentVO  bcv) {
		System.out.println(bcv.getBoardCommentNo() + "," + bcv.getBoardCommentPw());
		BoardCommentVO result = service.commentPwCheck(bcv);
		System.out.println(result);
		if(result != null) {
			return "1";
		} else {
			return "0";
		}
	}
	
	@ResponseBody
	@RequestMapping("/deleteComment.do")
	public String deleteComment(BoardCommentVO bcv) {
		int result = service.deleteComment(bcv);
		if(result == 1) {
			return "1";
		} else {
			return "0";
		}
	}
	
	@ResponseBody
	@RequestMapping("/modifyComment.do")
	public String modifyComment(BoardCommentVO bcv) {
		int result = service.modifyComment(bcv);
		if(result == 1) {
			return "1";
		} else {
			return "0";
		}
	}
	
//	public String replyWrite(BoardReplyVO replyVo) {
//		int boardReplyRef = replyVo.getBoardReplyRef() != 0 ? replyVo.getBoardReplyRef() : null;
//		replyVo.setBoardReplyRef(boardReplyRef);
//	}
}
