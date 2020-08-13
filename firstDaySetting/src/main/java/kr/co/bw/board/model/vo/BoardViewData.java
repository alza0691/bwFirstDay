package kr.co.bw.board.model.vo;

import java.util.List;

import lombok.Data;

@Data
public class BoardViewData {
	private BoardVO b;
	private List<BoardCommentVO> commentList;
	private BoardReplyVO reply;
}
