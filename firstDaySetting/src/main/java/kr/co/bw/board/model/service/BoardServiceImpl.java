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

	public BoardData selectBoardList(int reqPage, String type, String keyword) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", type);
		map.put("keyword", keyword);
		
		//한 페이지당 식사 기록 수
		int numPerPage = 5;
		//총 게시글 수
		int totalCount = dao.selectMapperInfoCount(map);
		//총 페이지 수
		System.out.println(totalCount);
		int totalPage;
		if (totalCount % numPerPage == 0) {
			totalPage = totalCount / numPerPage;
		} else {
			totalPage = totalCount / numPerPage + 1;
		}	
		
		int start = (reqPage - 1) * numPerPage + 1;
		int end = reqPage * numPerPage;
		map.put("start", String.valueOf(start));
		map.put("end", String.valueOf(end));
		List<BoardVO> list = dao.selectMapperInfo(map);
		System.out.println("totalPage = " + totalPage);
		System.out.println("start = " + start);
		System.out.println("end = "+end);
		System.out.println(list);
		
		StringBuffer pageNavi = new StringBuffer();
		int pageNaviSize = numPerPage;
		int pageNo = ((reqPage ) / pageNaviSize) * pageNaviSize+1;
		
		int pageLeft = pageNo-1;
		int pageRight = pageNo+5;
		
		if(reqPage != 0) {
			pageNavi.append("<a");
			if(reqPage > numPerPage) {
				pageNavi.append(" href='/bw/board/boardList.do?reqPage="+pageLeft+"'");
			}
			pageNavi.append(">이전</a>");
		}
		
		if(pageNo != 1) {
			pageNavi.append("<a href='/bw/board/boardList.do?reqPage="+(pageNo -1));
			if(type!=null) {
				pageNavi.append("&type=" + type + "&keyword=" + keyword);
			}
			pageNavi.append("'></a>");
		}
		
		for (int i = 0; i < pageNaviSize; i++) {
					
			if (pageNo == reqPage) {
				pageNavi.append("<a>" + pageNo + "</a>");
			} else {
				pageNavi.append("<a href='/bw/board/boardList.do?reqPage=" + pageNo);
				if(type!=null) {
					pageNavi.append("&type=" + type + "&keyword=" + keyword);
				}
				pageNavi.append("'>" + pageNo + "</a>");
			}
				
			pageNo++;
			
			if (pageNo > totalPage) {
				break;
			}
		}
		
		if (pageNo <= totalPage) {
			pageNavi.append("<a href='/bw/board/boardList.do?reqPage=" + pageNo);
			if (type != null) {
				pageNavi.append("&type=" + type + "&keyword=" + keyword);
			}
			pageNavi.append("'></a>");
		}
		
		if(reqPage != 0) {
		pageNavi.append("<a");
			if(pageNo <= totalPage) {
				pageNavi.append(" href='/bw/board/boardList.do?reqPage="+pageRight+"'");
			}
			pageNavi.append(">다음</a>");
		}
		
		BoardData data = new BoardData();
		data.setList(list);
		data.setPageNavi(pageNavi.toString());
		data.setTotalPage(totalPage);
		data.setTotalCount(totalCount);
		data.setNumPerPage(numPerPage);

		return data;	
	}

	public BoardVO oneContent(int boardNo) {
		// TODO Auto-generated method stub
		return dao.oneContent(boardNo);
	}

	public int boardDelete(BoardVO boardVo) {
		// TODO Auto-generated method stub
		return dao.boardDelete(boardVo);
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

	public BoardVO pwCheck(BoardVO boardVo) {
		// TODO Auto-generated method stub
		return dao.pwCheck(boardVo);
	}
	
}
