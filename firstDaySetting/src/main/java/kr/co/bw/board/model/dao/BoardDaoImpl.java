package kr.co.bw.board.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.bw.board.model.vo.BoardCommentVO;
import kr.co.bw.board.model.vo.BoardVO;



@Repository("boardDao")
public class BoardDaoImpl {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public BoardVO oneContent(int boardNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.oneContent", boardNo);
	}

	public int boardDelete(BoardVO boardVo) {
		// TODO Auto-generated method stub
		return sqlSession.delete("board.boardDelete",boardVo);
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

	public int selectMapperInfoCount(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.selectMapperInfoCount", map);
	}

	public List<BoardVO> selectMapperInfo(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.selectBoardList", map);
	}

	public BoardVO pwCheck(BoardVO boardVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.pwCheck",boardVo);
	}
	
	public int boardCommentInsert(BoardCommentVO comment) {
		// TODO Auto-generated method stub
		return sqlSession.insert("board.boardCommentInsert",comment);
	}

	public BoardVO selectOneBoard(int boardNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.oneContent", boardNo);
	}

	public List<BoardCommentVO> selectCommentList(int boardNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.selectCommentList", boardNo);
	}

	public int replyInsert(BoardVO boardVo) {
		// TODO Auto-generated method stub
		return sqlSession.insert("board.replyInsert",boardVo);
	}

	public BoardCommentVO boardOneComment(int boardNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.boardOneComment", boardNo);
	}

	public BoardCommentVO commentPwCheck(BoardCommentVO bcv) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.commentPwCheck", bcv);
	}

	public int deleteComment(BoardCommentVO bcv) {
		// TODO Auto-generated method stub
		return sqlSession.delete("board.deleteComment", bcv);
	}

	public int modifyComment(BoardCommentVO bcv) {
		// TODO Auto-generated method stub
		return sqlSession.update("board.modifyComment", bcv);
	}

	public BoardVO selectReply(int boardNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.selectReply", boardNo);
	}

	public int deleteFile(int boardNo) {
		// TODO Auto-generated method stub
		return sqlSession.update("board.deleteFile", boardNo);
	}

	public int insertExcel(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return sqlSession.insert("board.insertExcel", paramMap);
	}

	public List<BoardVO> checkRefBeing(BoardVO boardVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.checkRefBeing", boardVo);
	}

	public int boardDeleteUpdate(BoardVO boardVo) {
		// TODO Auto-generated method stub
		return sqlSession.update("board.boardDeleteUpdate", boardVo);
	}

	public List<BoardVO> excelDownload(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board.excelDownload", map);
	}
	
}
