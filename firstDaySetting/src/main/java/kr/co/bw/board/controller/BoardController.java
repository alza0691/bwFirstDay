package kr.co.bw.board.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.bw.board.model.service.BoardServiceImpl;
import kr.co.bw.board.model.vo.BoardCommentVO;
import kr.co.bw.board.model.vo.BoardData;
import kr.co.bw.board.model.vo.BoardVO;

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
		BoardData data = service.selectBoardList(reqPage);
		request.setAttribute("list", data.getList());
		request.setAttribute("pageNavi", data.getPageNavi());
		System.out.println(data);
		return "board/boardList";
	}
	
	@RequestMapping("/contentPage.do")
	public String contentPage(Model model, int boardNo) {
		BoardVO oneContent = service.oneContent(boardNo);
		System.out.println(oneContent);
		model.addAttribute("oneContent", oneContent);
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
	
	@RequestMapping("/boardWriteFrm.do")
	public String boardWriteFrm() {
		return "board/boardWrite";
	}
	
	@RequestMapping("/boardDelete.do")
	public String boardDelete(int boardNo) {
		System.out.println("아아");
		int result = service.boardDelete(boardNo);
		if (result == 1) {
			System.out.println("삭제성공");
		} else {
			System.out.println("삭제실패");
		}
		return "redirect:/bw/board/boardList.do";
	}
	
	@RequestMapping("/boardUpdate.do")
	public String boardUpdate(BoardVO boardVo) {
		int result = service.boardUpdate(boardVo);
		if (result == 1) {
			System.out.println("수정성공");
		} else {
			System.out.println("수정실패");
		}
		return "redirect:/bw/board/boardList.do";
	}
	
	@RequestMapping(value="boardUpdateFrm.do")
	public String boardUpdateFrm(Model model, int boardNo) {
		BoardVO boardVo = service.boardUpdateFrm(boardNo);
		model.addAttribute("boardVo", boardVo);
		return "board/boardUpdate";
	}

	

	
}
