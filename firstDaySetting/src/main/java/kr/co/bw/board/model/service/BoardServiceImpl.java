package kr.co.bw.board.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.bw.board.model.dao.BoardDaoImpl;
import kr.co.bw.board.model.vo.BoardData;
import kr.co.bw.board.model.vo.BoardVO;

@Service("boardService")
public class BoardServiceImpl {
	@Autowired
	@Qualifier("boardDao")
	private BoardDaoImpl dao;

	public BoardData selectBoardList(int reqPage) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		//한 페이지당 식사 기록 수
		int numPerPage = 10;
				
		//총 식사 기룩
		int totalCount = dao.selectMapperInfoCount();
		//총 페이지 수
		int totalPage;
		if (totalCount % numPerPage == 0) {
			totalPage = totalCount / numPerPage;
		} else {
			totalPage = totalCount / numPerPage + 1;
		}
				
		int start = (reqPage - 1) * numPerPage + 1;
		int end = reqPage * numPerPage;
		map.put("start", start);
		map.put("end", end);
		List<BoardVO> list = dao.selectMapperInfo(map);
		System.out.println(list);
		
		StringBuffer pageNavi = new StringBuffer();
		int pageNaviSize = 10;
		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;
		
		if (pageNo != 1) {
			pageNavi.append("<li><a href='/bw/board/boardList.do?&reqPage=" + (pageNo - 1) + "'><</a></li>");
		}
		
		for (int i = 0; i < pageNaviSize; i++) {
					
			if (pageNo == reqPage) {
				pageNavi.append("<li>" + pageNo + "</li>");
			} else {
				pageNavi.append("<li><a href='/bw/board/boardList.do?&reqPage=" + pageNo + "'>" + pageNo + "</a></li>");
			}
				
			pageNo++;
			
			if (pageNo > totalPage) {
				break;
			}
		}
		
		if (pageNo <= totalPage) {
			pageNavi.append("<li><a href='/bw/board/boardList.do?&reqPage=" + pageNo + "'>></a></li>");
		}
		
		BoardData data = new BoardData();
		data.setList(list);
		data.setPageNavi(pageNavi.toString());

		return data;	
	}

	public BoardVO oneContent(int boardNo) {
		// TODO Auto-generated method stub
		return dao.oneContent(boardNo);
	}

	public int boardDelete(int boardNo) {
		// TODO Auto-generated method stub
		return dao.boardDelete(boardNo);
	}

	public int boardWirte(BoardVO boardVo) {
		// TODO Auto-generated method stub
		return dao.boardWrite(boardVo);
	}

	public int boardUpdate(BoardVO boardVo) {
		// TODO Auto-generated method stub
		return dao.boardUpdate(boardVo);
	}
	
	public BoardVO boardUpdateFrm(int boardNo) {
		// TODO Auto-generated method stub
		return dao.boardUpdateFrm(boardNo);
	}


	
}
