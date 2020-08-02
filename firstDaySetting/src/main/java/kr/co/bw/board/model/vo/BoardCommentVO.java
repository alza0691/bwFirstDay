package kr.co.bw.board.model.vo;

import java.sql.Date;

public class BoardCommentVO {
	private int boardCommentNo;
	private int boardCommentLevel;
	private String boardCommentWriter;
	private String boardCommentContent;
	private int boardRef;
	private int boardCommentRef;
	private Date boardCommentDate;
	private String boardCommentPw;
	
	public String getCommentBr() {
		return boardCommentContent.replaceAll("\r\n", "<br>");
	}

	public BoardCommentVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BoardCommentVO(int boardCommentNo, int boardCommentLevel, String boardCommentWriter,
			String boardCommentContent, int boardRef, int boardCommentRef, Date boardCommentDate,
			String boardCommentPw) {
		super();
		this.boardCommentNo = boardCommentNo;
		this.boardCommentLevel = boardCommentLevel;
		this.boardCommentWriter = boardCommentWriter;
		this.boardCommentContent = boardCommentContent;
		this.boardRef = boardRef;
		this.boardCommentRef = boardCommentRef;
		this.boardCommentDate = boardCommentDate;
		this.boardCommentPw = boardCommentPw;
	}

	public int getBoardCommentNo() {
		return boardCommentNo;
	}

	public void setBoardCommentNo(int boardCommentNo) {
		this.boardCommentNo = boardCommentNo;
	}

	public int getBoardCommentLevel() {
		return boardCommentLevel;
	}

	public void setBoardCommentLevel(int boardCommentLevel) {
		this.boardCommentLevel = boardCommentLevel;
	}

	public String getBoardCommentWriter() {
		return boardCommentWriter;
	}

	public void setBoardCommentWriter(String boardCommentWriter) {
		this.boardCommentWriter = boardCommentWriter;
	}

	public String getBoardCommentContent() {
		return boardCommentContent;
	}

	public void setBoardCommentContent(String boardCommentContent) {
		this.boardCommentContent = boardCommentContent;
	}

	public int getBoardRef() {
		return boardRef;
	}

	public void setBoardRef(int boardRef) {
		this.boardRef = boardRef;
	}

	public int getBoardCommentRef() {
		return boardCommentRef;
	}

	public void setBoardCommentRef(int boardCommentRef) {
		this.boardCommentRef = boardCommentRef;
	}

	public Date getBoardCommentDate() {
		return boardCommentDate;
	}

	public void setBoardCommentDate(Date boardCommentDate) {
		this.boardCommentDate = boardCommentDate;
	}

	public String getBoardCommentPw() {
		return boardCommentPw;
	}

	public void setBoardCommentPw(String boardCommentPw) {
		this.boardCommentPw = boardCommentPw;
	}
	
		
}
