package kr.co.bw.board.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.bw.board.model.dao.BoardDaoImpl;

@Service("boardService")
public class BoardServiceImpl {
	@Autowired
	@Qualifier("boardDao")
	private BoardDaoImpl dao;
	
}
