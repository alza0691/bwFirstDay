package kr.co.bw.board.model.vo;

import java.util.List;

import lombok.Data;

@Data
public class BoardData {
	private List<BoardVO> list;
	private String pageNavi;
	private int totalPage;
	private int totalCount;
	private int numPerPage;
}
