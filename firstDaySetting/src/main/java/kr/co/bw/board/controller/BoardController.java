package kr.co.bw.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping("/boardWrite.do")
	public String boardWrite(BoardVO boardVo) {
		System.out.println(boardVo);
		int result = service.boardWirte(boardVo);
		if (result == 1) {
			System.out.println("글쓰기 성공");
		} else {
			System.out.println("글쓰기 실패");
		}
		return "redirect:/bw/board/boardList.do";
	}
	
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
		System.out.println(comment.getBoardCommentPw());
		System.out.println(comment.getBoardCommentContent());
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
		model.addAttribute("boardNo", boardNo);
		return "board/replyWrite";
	}
	
	@RequestMapping("/replyWrite.do")
	public String replyWrite(BoardReplyVO replyVo) {
		System.out.println(replyVo.getBoardReplyWriter());
		int result = service.replyInsert(replyVo);
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
		int pwCheck = service.commentPwCheck(bcv);
		if(pwCheck == 1) {
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
//	public String replyWrite(BoardReplyVO replyVo) {
////		int boardReplyRef = replyVo.getBoardReplyRef() != 0 ? replyVo.getBoardReplyRef() : null;
////		replyVo.setBoardReplyRef(boardReplyRef);
}
