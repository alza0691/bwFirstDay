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
	private int commentCount;
	private int boardLevel;
	private int boardRef;
	private String filepath;
	private String filename;
	private String filename1;
	public BoardVO(int boardNo, String boardTitle, String boardContent, String boardWriter, String boardDate,
			String boardPw, String type, String keyword, int commentCount, int boardLevel, int boardRef,
			String filepath, String filename, String filename1, String filename2, String filename3,
			String showFilename1, String showFilename2, String showFilename3) {
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
		this.filename1 = filename1;
		this.filename2 = filename2;
		this.filename3 = filename3;
		this.showFilename1 = showFilename1;
		this.showFilename2 = showFilename2;
		this.showFilename3 = showFilename3;
	}
	private String filename2;
	private String filename3;
	private String showFilename1;
	private String showFilename2;
	private String showFilename3;


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
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilename2() {
		return filename2;
	}
	public void setFilename2(String filename2) {
		this.filename2 = filename2;
	}
	public String getFilename3() {
		return filename3;
	}
	public void setFilename3(String filename3) {
		this.filename3 = filename3;
	}
	public String getShowFilename1() {
		return showFilename1;
	}
	public void setShowFilename1(String showFilename1) {
		this.showFilename1 = showFilename1;
	}
	public String getShowFilename2() {
		return showFilename2;
	}
	public void setShowFilename2(String showFilename2) {
		this.showFilename2 = showFilename2;
	}
	public String getShowFilename3() {
		return showFilename3;
	}
	public void setShowFilename3(String showFilename3) {
		this.showFilename3 = showFilename3;
	}
	public String getFilename1() {
		return filename1;
	}
	public void setFilename1(String filename1) {
		this.filename1 = filename1;
	}
	public BoardVO() {
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
