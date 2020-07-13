package kr.co.bw.board.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository("trainerDao")
public class BoardDaoImpl {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
}
