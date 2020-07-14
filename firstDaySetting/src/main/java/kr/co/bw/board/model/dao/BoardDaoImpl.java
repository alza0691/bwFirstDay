package kr.co.bw.board.model.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.bw.board.model.vo.BoardVO;



@Repository("boardDao")
public class BoardDaoImpl {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public BoardVO oneContent(int boardNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.oneContent", boardNo);
	}

	public int boardDelete(int boardNo) {
		// TODO Auto-generated method stub
		return sqlSession.delete("board.boardDelete",boardNo);
	}

	public int boardWrite(BoardVO boardVo) {
		// TODO Auto-generated method stub
		return sqlSession.insert("board.boardWrite",boardVo);
	}

	public int boardUpdate(BoardVO boardVo) {
		// TODO Auto-generated method stub
		System.out.println(boardVo);
		return sqlSession.update("board.boardUpdate", boardVo);
	}

	public BoardVO boardUpdateFrm(int boardNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.boardUpdateFrm",boardNo);
	}

	public int selectMapperInfoCount() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.selectMapperInfoCount");
	}

	public List<BoardVO> selectMapperInfo(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.selectBoardList", map);
	}
	
}
