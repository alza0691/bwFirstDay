package kr.co.bw.board.model.vo;

public class BoardVO {

	private int memberNo;
	private String trainerIntro;
	private int catFirst;
	private int catSecond;
	private int catThird;
	private String memberName;
	private String catFirstName;
	private String catSecondName;
	private String catThirdName;
	private String memberProfile;
	
	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getTrainerIntro() {
		return trainerIntro;
	}
	
	public String getTrainerIntro2() {
		return trainerIntro.replaceAll("\r\n", "<br>");
	}

	public void setTrainerIntro(String trainerIntro) {
		this.trainerIntro = trainerIntro;
	}

	public int getCatFirst() {
		return catFirst;
	}

	public void setCatFirst(int catFirst) {
		this.catFirst = catFirst;
	}

	public int getCatSecond() {
		return catSecond;
	}

	public void setCatSecond(int catSecond) {
		this.catSecond = catSecond;
	}

	public int getCatThird() {
		return catThird;
	}

	public void setCatThird(int catThird) {
		this.catThird = catThird;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCatFirstName() {
		return catFirstName;
	}

	public void setCatFirstName(String catFirstName) {
		this.catFirstName = catFirstName;
	}

	public String getCatSecondName() {
		return catSecondName;
	}

	public void setCatSecondName(String catSecondName) {
		this.catSecondName = catSecondName;
	}

	public String getCatThirdName() {
		return catThirdName;
	}

	public void setCatThirdName(String catThirdName) {
		this.catThirdName = catThirdName;
	}

	public String getMemberProfile() {
		return memberProfile;
	}

	public void setMemberProfile(String memberProfile) {
		this.memberProfile = memberProfile;
	}

	public BoardVO(int memberNo, String trainerIntro, int catFirst, int catSecond, int catThird, String memberName,
			String catFirstName, String catSecondName, String catThirdName, String memberProfile) {
		super();
		this.memberNo = memberNo;
		this.trainerIntro = trainerIntro;
		this.catFirst = catFirst;
		this.catSecond = catSecond;
		this.catThird = catThird;
		this.memberName = memberName;
		this.catFirstName = catFirstName;
		this.catSecondName = catSecondName;
		this.catThirdName = catThirdName;
		this.memberProfile = memberProfile;
	}

	public BoardVO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
