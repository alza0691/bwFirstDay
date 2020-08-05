package kr.co.bw.board.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.co.bw.board.model.dao.BoardDaoImpl;
import kr.co.bw.board.model.vo.BoardCommentVO;
import kr.co.bw.board.model.vo.BoardData;
import kr.co.bw.board.model.vo.BoardReplyVO;
import kr.co.bw.board.model.vo.BoardVO;
import kr.co.bw.board.model.vo.BoardViewData;

@Service("boardService")
public class BoardServiceImpl {
	@Autowired
	@Qualifier("boardDao")
	private BoardDaoImpl dao;

	public BoardData selectBoardList(int reqPage, String type, String keyword) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", type);
		map.put("keyword", keyword);
		
		//한 페이지당 게시물
		int numPerPage = 5;
		//총 게시글 수
		int totalCount = dao.selectMapperInfoCount(map);
		System.out.println("토탈카운트 = " + totalCount);
		//총 페이지 수
		int totalPage;
		//총 페이지 수 연산 (추가적인 페이지를 가지고 올것인가 아닌가를 확인)
		if (totalCount % numPerPage == 0) {				//총 페이지 수와 한페이당 게시물의 나머지가 0이면 추가적인 페이지를 가지고 올 필요가 없으므로
			totalPage = totalCount / numPerPage;		//(총 페이지 수 = 총 게시글 / 한페이지당 게시물)
		} else {										//나누어 지면 총 페이지에서 넘어가지 않고 딱 떨어짐
			totalPage = totalCount / numPerPage + 1;	//그렇지 않은 것은 한 페이지가 더 필요함
		}	
		
		//조회해 올 게시물 (보여지는 페이지) 시작번호와 끝번호 연산
		int start = (reqPage - 1) * numPerPage + 1;		//((요청페이지 -1) * 한페이지당 게시글)을 하여 전페이지의 마지막 게시글을 가지고 온 후 +1을 하여 요청페이지의 첫글번호를 가져옴 //요청페이지가 어디든 요청페이지의 첫글을 찾을 수 있음
		int end = reqPage * numPerPage;					//(요청페이지 * 한페이지당 게시글)을 하여 요청페이지의 마지막 번호를 가져옴
		
		//시작번호와 끝번호를 map에 저장
		map.put("start", String.valueOf(start));
		map.put("end", String.valueOf(end));
		
		//해당 페이지의 게시물들 조회
		List<BoardVO> list = dao.selectMapperInfo(map);
				
		System.out.println("totalPage = " + totalPage);
		System.out.println("start = " + start);
		System.out.println("end = " + end);
		
		
		//페이지 네비게이션 작성 시작 (주소값 처리)
		StringBuffer pageNavi = new StringBuffer();
		
		//페이지 네비게이션 길이
		int pageNaviSize = 5;
		int pageNo = ((reqPage -1) / pageNaviSize) * pageNaviSize + 1; 						//(보여지는 네비묶음1~5, 6~10) 해당 게시물의 페이지 네비게이션 첫번째 수를 조회 ((해당페이지 -1) / 페이지네비사이즈) * 페이지네비사이즈 +1
																							//이전버튼과 다음버튼 현재페이지 처리를 위한 것 (-1을 안할 시 마지막 번호쪽클릭시 다음으로 이동함)
		
		pageNavi.append("<a");																//<a>이전</a>을 위한 append(계속해서 유지하기 위해서)
		if(pageNo != 1) {																	//페이지 네비게이션의 첫번째 수가 1이 아니면
			pageNavi.append(" href='/bw/board/boardList.do?reqPage="+(pageNo-1));			//이전으로 이동할 수 있게끔 하고(-1을 안할 경우 이전이 넘어가지 않음(페이지네비의 첫페이지로 지정이 되서 그럼))
			if(type!=null) {																//검색 값이 있으면
				pageNavi.append("&type=" + type + "&keyword=" + keyword);					//검색 조건과 키워드를 검색
			}																				//없으면 통과
			pageNavi.append("'");
		}
		pageNavi.append(">이전</a>");
		
		for (int i = 0; i < pageNaviSize; i++) {											//페이지 네비게이션 사이즈만큼 출력
			if (pageNo == reqPage) {														//해당 게시물의 위치일 때  
				pageNavi.append("<a>" + pageNo + "</a>");									//넘어갈 수 없이 빈값으로 처리(주소값 없이처리)
			} else {
				pageNavi.append("<a href='/bw/board/boardList.do?reqPage=" + pageNo);		//게시물 페이지가 작거나 클 때 넘어갈 수 있도록 주소값 추가하여 처리
				if(type!=null) {															//검색 값이 있으면
					pageNavi.append("&type=" + type + "&keyword=" + keyword);				//검색 조건과 키워드를 검색
				}																			//없으면 통과
				pageNavi.append("'>" + pageNo + "</a>");
			}	
			pageNo++;
			if (pageNo > totalPage) {														//만약 해당게시물의 페이지네비게이션의 수가 총 페이지수보다 높을 시에는 통과
				break;																		//break 안 할 시 추가적인 빈 네비가 생성(딱 맞춰서 끊어야 되기 때문에)
			}
		}
		pageNo--;																			//break하기 전에 +1이 되기때문에 -1을 해줌
		
		pageNavi.append("<a");
		if (pageNo < totalPage) {															//해당게시물의 페이지가 총 페이지수보다 작을 때 
			pageNavi.append(" href='/bw/board/boardList.do?reqPage=" + (pageNo+1));			//이동할 수 있도록 처리 (+1을 안해주면 다음으로 넘어가지 않음(페이지네비의 마지막페이지로 지정되서 그럼))
			if (type != null) {																//검색 조건이 있을 시
				pageNavi.append("&type=" + type + "&keyword=" + keyword);					//검색 조건을 넣음
			}																				//없으면 통과
			pageNavi.append("'");
		}
		pageNavi.append(">다음</a>");	
		
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
	
	public int boardCommentInsert(BoardCommentVO comment) {
		// TODO Auto-generated method stub
		return dao.boardCommentInsert(comment);
	}

	public BoardViewData boardCommentList(int boardNo) {
		BoardVO selectBoard = dao.selectOneBoard(boardNo);
		List<BoardCommentVO> selectCommentList = dao.selectCommentList(boardNo);		
		BoardViewData data = new BoardViewData();
		data.setB(selectBoard);
		data.setCommentList(selectCommentList);
		
		return data;
	}

	public int replyInsert(BoardVO boardVo) {
		// TODO Auto-generated method stub
		return dao.replyInsert(boardVo);
	}

	public BoardCommentVO boardOneComment(int boardNo) {
		// TODO Auto-generated method stub
		return dao.boardOneComment(boardNo);
	}

	public BoardCommentVO commentPwCheck(BoardCommentVO bcv) {
		// TODO Auto-generated method stub
		return dao.commentPwCheck(bcv);
	}

	public int deleteComment(BoardCommentVO bcv) {
		// TODO Auto-generated method stub
		return dao.deleteComment(bcv);
	}

	public int modifyComment(BoardCommentVO bcv) {
		// TODO Auto-generated method stub
		return dao.modifyComment(bcv);
	}

	public BoardVO replyInfo(int boardNo) {
		// TODO Auto-generated method stub
		return dao.selectReply(boardNo);
	}
	
}
