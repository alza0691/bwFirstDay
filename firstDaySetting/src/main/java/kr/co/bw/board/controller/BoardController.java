package kr.co.bw.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import kr.co.bw.board.model.service.BoardServiceImpl;


@Controller
@SessionAttributes("id")
@RequestMapping("/bw/board")
public class BoardController {
	@Autowired
	@Qualifier("boardService")
	private BoardServiceImpl service;
	
}
