package kr.co.bw.board.model.vo;

public class BoardVO {

	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriter;
	private String boardDate;
	private String boardPw;
	private String type;
	private String keyword;
	private int rnum;
	
	public BoardVO(int boardNo, String boardTitle, String boardContent, String boardWriter, String boardDate,
			String boardPw, String type, String keyword, int rnum) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
		this.boardDate = boardDate;
		this.boardPw = boardPw;
		this.type = type;
		this.keyword = keyword;
		this.rnum = rnum;
	}


	public String getBoardTitle2() {
		return boardTitle.replaceAll("<","&lt;").replaceAll(">","&gt;");
	}
	public String getBoardContent2() {
		return boardContent.replaceAll("<","&lt;").replaceAll(">","&gt;");
	}
	public String getBoardWriter2() {
		return boardWriter.replaceAll("<","&lt;").replaceAll(">","&gt;");
	}
	
	public BoardVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getRnum() {
		return rnum;
	}


	public void setRnum(int rnum) {
		this.rnum = rnum;
	}


	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardWriter() {
		return boardWriter;
	}
	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}
	public String getBoardDate() {
		return boardDate;
	}
	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}
	public String getBoardPw() {
		return boardPw;
	}
	public void setBoardPw(String boardPw) {
		this.boardPw = boardPw;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
