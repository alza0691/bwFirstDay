package kr.co.bw.board.model.vo;

import lombok.Data;

@Data
public class BoardVO {

	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriter;
	private String boardDate;
}
