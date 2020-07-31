package kr.co.bw.board.model.vo;

import java.sql.Date;

public class BoardReplyVO {
	private int boardReplyNo;
	private int boardReplyLevel;
	private String boardReplyWriter;
	private String boardReplyContent;
	private String boardReplyTitle;
	private int boardRef;
	private int boardReplyRef;
	private Date boardReplyDate;
	private String boardReplyPw;
	
	public String getReplyBr() {
		return boardReplyContent.replaceAll("\r\n", "<br>");
	}

	public BoardReplyVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BoardReplyVO(int boardReplyNo, int boardReplyLevel, String boardReplyWriter, String boardReplyContent,
			String boardReplyTitle, int boardRef, int boardReplyRef, Date boardReplyDate, String boardReplyPw) {
		super();
		this.boardReplyNo = boardReplyNo;
		this.boardReplyLevel = boardReplyLevel;
		this.boardReplyWriter = boardReplyWriter;
		this.boardReplyContent = boardReplyContent;
		this.boardReplyTitle = boardReplyTitle;
		this.boardRef = boardRef;
		this.boardReplyRef = boardReplyRef;
		this.boardReplyDate = boardReplyDate;
		this.boardReplyPw = boardReplyPw;
	}

	public int getBoardReplyNo() {
		return boardReplyNo;
	}

	public void setBoardReplyNo(int boardReplyNo) {
		this.boardReplyNo = boardReplyNo;
	}

	public int getBoardReplyLevel() {
		return boardReplyLevel;
	}

	public void setBoardReplyLevel(int boardReplyLevel) {
		this.boardReplyLevel = boardReplyLevel;
	}

	public String getBoardReplyWriter() {
		return boardReplyWriter;
	}

	public void setBoardReplyWriter(String boardReplyWriter) {
		this.boardReplyWriter = boardReplyWriter;
	}

	public String getBoardReplyContent() {
		return boardReplyContent;
	}

	public void setBoardReplyContent(String boardReplyContent) {
		this.boardReplyContent = boardReplyContent;
	}

	public String getBoardReplyTitle() {
		return boardReplyTitle;
	}

	public void setBoardReplyTitle(String boardReplyTitle) {
		this.boardReplyTitle = boardReplyTitle;
	}

	public int getBoardRef() {
		return boardRef;
	}

	public void setBoardRef(int boardRef) {
		this.boardRef = boardRef;
	}

	public int getBoardReplyRef() {
		return boardReplyRef;
	}

	public void setBoardReplyRef(int boardReplyRef) {
		this.boardReplyRef = boardReplyRef;
	}

	public Date getBoardReplyDate() {
		return boardReplyDate;
	}

	public void setBoardReplyDate(Date boardReplyDate) {
		this.boardReplyDate = boardReplyDate;
	}

	public String getBoardReplyPw() {
		return boardReplyPw;
	}

	public void setBoardReplyPw(String boardReplyPw) {
		this.boardReplyPw = boardReplyPw;
	}
	
}
