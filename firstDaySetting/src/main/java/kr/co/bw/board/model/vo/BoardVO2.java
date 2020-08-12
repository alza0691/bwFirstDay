package kr.co.bw.board.model.vo;

import org.springframework.web.multipart.MultipartFile;

public class BoardVO2 {

	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriter;
	private String boardDate;
	private String boardPw;
	private String type;
	private String keyword;
	private int commentCount;
	private int boardLevel;
	private int boardRef;
	private String filepath;
	private String filename;
	private String showFilename;

	
	public BoardVO2(int boardNo, String boardTitle, String boardContent, String boardWriter, String boardDate,
			String boardPw, String type, String keyword, int commentCount, int boardLevel, int boardRef,
			String filepath, String filename, MultipartFile mp) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
		this.boardDate = boardDate;
		this.boardPw = boardPw;
		this.type = type;
		this.keyword = keyword;
		this.commentCount = commentCount;
		this.boardLevel = boardLevel;
		this.boardRef = boardRef;
		this.filepath = filepath;
		this.filename = filename;
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
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getBoardLevel() {
		return boardLevel;
	}
	public void setBoardLevel(int boardLevel) {
		this.boardLevel = boardLevel;
	}
	public int getBoardRef() {
		return boardRef;
	}
	public void setBoardRef(int boardRef) {
		this.boardRef = boardRef;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String string) {
		this.filename = string;
	}
	public String getShowFilename() {
		return showFilename;
	}
	public void setShowFilename(String showFilename) {
		this.showFilename = showFilename;
	}
	public BoardVO2(int boardNo, String boardTitle, String boardContent, String boardWriter, String boardDate,
			String boardPw, String type, String keyword, int commentCount, int boardLevel, int boardRef,
			String filepath, String filename, String showFilename) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
		this.boardDate = boardDate;
		this.boardPw = boardPw;
		this.type = type;
		this.keyword = keyword;
		this.commentCount = commentCount;
		this.boardLevel = boardLevel;
		this.boardRef = boardRef;
		this.filepath = filepath;
		this.filename = filename;
		this.showFilename = showFilename;
	}
	public BoardVO2() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getBoardTitle2() {
		return boardTitle.replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&nbsp;", "&amp;nbsp;");
	}
	public String getBoardContent2() {
		return boardContent.replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&nbsp;", "&amp;nbsp;");
	}
	public String getBoardWriter2() {
		return boardWriter.replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&nbsp;", "&amp;nbsp;");
	}
	
}
