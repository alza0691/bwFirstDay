package kr.co.bw.board.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import kr.co.bw.board.model.service.BoardServiceImpl;
import kr.co.bw.board.model.vo.BoardCommentVO;
import kr.co.bw.board.model.vo.BoardData;
import kr.co.bw.board.model.vo.BoardVO;
import kr.co.bw.board.model.vo.BoardViewData;

@Controller
@RequestMapping("/bw/board")
public class BoardController {
	@Autowired
	@Qualifier("boardService")
	private BoardServiceImpl service;

	@RequestMapping("/boardList.do")
	public String boardList(HttpServletRequest request) {
		int reqPage = 0;
		try {
			reqPage = Integer.parseInt(request.getParameter("reqPage"));
		} catch (Exception e) {
			reqPage = 1;
		}
		
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		System.out.println(type);
		System.out.println(keyword);		
		
		BoardData data = service.selectBoardList(reqPage, type, keyword);
		
		System.out.println(data.getList());
		
		
		request.setAttribute("list", data.getList());
		request.setAttribute("pageNavi", data.getPageNavi());
		request.setAttribute("reqPage", reqPage);
		request.setAttribute("type", type);
		request.setAttribute("keyword", keyword);
		request.setAttribute("totalPage", data.getTotalPage());
		request.setAttribute("totalCount", data.getTotalCount());
		request.setAttribute("numPerPage", data.getNumPerPage());

		return "board/boardList";
	}	
	
	@RequestMapping("/contentPage.do")
	public String contentPage(Model model, int boardNo, BoardData boardData) {
		BoardViewData data = service.boardCommentList(boardNo);
		BoardData paging = new BoardData();
		paging.setReqPage(boardData.getReqPage());
		paging.setKeyword(boardData.getKeyword());
		paging.setType(boardData.getType());
		model.addAttribute("paging", paging);
		model.addAttribute("b", data.getB());
		model.addAttribute("commentList", data.getCommentList());
		
		return "board/contentPage";
	}
	
	 @RequestMapping(value = "/download.do")
		public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			String filename = request.getParameter("filename");
			
			String filepath = UPLOAD_PATH;
			
			File file = new File(filepath + "\\" + filename);
			
			//1) 경로설정
//			String root = request.getSession().getServletContext().getRealPath("/");
//			String saveDirectory = root + "upload/";
			//파일이랑 서블릿 연결
			FileInputStream fis = new FileInputStream(file);
			//속도를 위한 보조 스트림 생성
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			//파일을 내보내기 위한 스트림 생성
			ServletOutputStream sos = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(sos);
			
			String resFilname = "";
			
			//브라우저가 IE인지 확인
			boolean bool = request.getHeader("user-agent").indexOf("MSIE") != -1 || request.getHeader("user-agent").indexOf("Trident") != -1;
			System.out.println("IE여부 : " + bool);
			
			String str = filename.substring(filename.indexOf('_')+1);
			
			if (bool) {//IE인 경우
				resFilname = URLEncoder.encode(str, "UTF-8");
				resFilname = resFilname.replace("\\\\", "%20");
			} else {//나머지 브라우저인 경우
				resFilname = new String(str.getBytes("UTF-8"), "ISO-8859-1");
			}
			
			System.out.println("resFilename : " + resFilname);
			
			//파일 다운로드를 위한 HTTP Header 설정
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=" +resFilname);
			
			int read = -1;
			while((read = bis.read()) != -1) {
				bos.write(read);
			}
			
			bos.close();
			bis.close();
	 }
	

	
	@ResponseBody
	@RequestMapping(value="/boardWrite.do" ,method = RequestMethod.POST, consumes= {"multipart/form-data"})
	public String boardWrite(@RequestParam(value="files") MultipartFile[] uploadfile, BoardVO boardVo) {
		if(uploadfile.length != 0) {
		boardVo.setFilename(saveFile(uploadfile));	
		}
	    if(boardVo.getFilename()==null) {
	    	boardVo.setFilepath(null);
	    } else {
	    	boardVo.setFilepath(UPLOAD_PATH);
	    }
		int result = service.boardWirte(boardVo);
		if (result == 1) {
			System.out.println("글쓰기 성공");
			return "1";
		} else {
			System.out.println("글쓰기 실패");
			return "0";
		}
	}
	
	private String saveFile(@RequestParam("files") MultipartFile[] file){
		String multifileName = "";
		if(file[0].getSize() != 0) {
			for(int i = 0; i < file.length; i++) {
			    UUID uuid = UUID.randomUUID();
			    String saveName = uuid + "_" + file[i].getOriginalFilename();
			    File saveFile = new File(UPLOAD_PATH,saveName);
			    try {
					file[i].transferTo(saveFile);
					System.out.println("파일 업로드");
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    multifileName +=  saveName + "*";
			}
			return multifileName;
		} else {
			return null;
		}
	}
	
	public org.slf4j.Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String UPLOAD_PATH = "C:\\Users\\lance";
	//단독파일 올리기
//	private String saveFile(MultipartFile file){
//	    // 파일 이름 변경
//		if(file.getOriginalFilename() != "") {
//	    UUID uuid = UUID.randomUUID();
//	    String saveName = uuid + "_" + file.getOriginalFilename();
//
//	    logger.info("saveName: {}",saveName);
//
//	    // 저장할 File 객체를 생성(껍데기 파일)ㄴ
//	    File saveFile = new File(UPLOAD_PATH,saveName); // 저장할 폴더 이름, 저장할 파일 이름
//
//	    try {
//	        file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	        return null;
//	    }
//
//	    return saveName;
//		} else {
//			return null;
//		}
//	}
	
	@ResponseBody
	@RequestMapping(value = "/boardUpdate.do", method = RequestMethod.POST, consumes= {"multipart/form-data"})
	public String boardUpdate(BoardVO boardVo, @RequestParam("files") MultipartFile[] uploadfile, BoardData boardData){
		String multifileName = "";
		if(boardVo.getFilename1() != null) {
			multifileName +=  boardVo.getFilename1() + "*";
		}
		if(boardVo.getFilename2() != null) {
			multifileName +=  boardVo.getFilename2() + "*";
		}
		if(boardVo.getFilename3() != null) {
			multifileName +=  boardVo.getFilename3() + "*";
		}
		
		if(uploadfile.length != 0) {
	    multifileName += saveFile(uploadfile);
		}
		System.out.println(multifileName);
		
		boardVo.setFilename(multifileName);
		
	    if(boardVo.getFilename()==null) {
	    	boardVo.setFilepath(null);
	    } else {
	    	boardVo.setFilepath(UPLOAD_PATH);
	    	String name = boardVo.getFilename();
	    	String[] arr = name.split("\\*");
	    	try {
	    		boardVo.setFilename1(arr[0]);
	    		boardVo.setFilename2(arr[1]);
	    		boardVo.setFilename3(arr[2]);
				} catch(IndexOutOfBoundsException e) {
					System.out.println(e);
				}
				try {
					boardVo.setShowFilename1(arr[0].substring(arr[0].indexOf('_')+1));
					boardVo.setShowFilename2(arr[1].substring(arr[1].indexOf('_')+1));
					boardVo.setShowFilename3(arr[2].substring(arr[2].indexOf('_')+1));
				} catch(IndexOutOfBoundsException e){
					System.out.println(e);
				}
	    }
		int result = service.boardUpdate(boardVo);
		if (result == 1) {
			System.out.println("수정성공");
		return "1";
		} else {
			System.out.println("수정실패");
		return "0";
		}
	}
	
	@RequestMapping("/boardWriteFrm.do")
	public String boardWriteFrm() {
		return "board/boardWrite";
	}
	
	@ResponseBody
	@RequestMapping(value="/boardDelete.do", produces = "text/html; charset=utf-8")
	public String boardDelete(BoardVO boardVo) {
		int result = service.boardDelete(boardVo);
		if (result == 1) {
			System.out.println("삭제성공");
			return "1";
		} else {
			System.out.println("삭제실패");
			return "0";
		}
	}
	
	@RequestMapping(value="boardUpdateFrm.do")
	public String boardUpdateFrm(Model model, int boardNo, BoardData data) {
		BoardVO boardVo = service.boardUpdateFrm(boardNo);
		if(boardVo.getFilename() != null) {
		String filename = boardVo.getFilename();
		String[] arr = filename.split("\\*");
		try {
		boardVo.setFilename1(arr[0]);
		boardVo.setFilename2(arr[1]); 
		boardVo.setFilename3(arr[2]); 
		} catch(IndexOutOfBoundsException e) {
			System.out.println(e);
		}
		try {
			boardVo.setShowFilename1(arr[0].substring(arr[0].indexOf('_')+1));
			boardVo.setShowFilename2(arr[1].substring(arr[1].indexOf('_')+1));
			boardVo.setShowFilename3(arr[2].substring(arr[2].indexOf('_')+1));
		} catch(IndexOutOfBoundsException e) {
			System.out.println(e);
		}
		}
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("data", data);
		return "board/boardUpdate";
	}

	@ResponseBody
	@RequestMapping(value="pwCheck.do", produces = "text/html; charset=utf-8")
	public String pwCheck(BoardVO boardVo) {
		System.out.println(boardVo.getBoardNo() + boardVo.getBoardPw());
		BoardVO result = service.pwCheck(boardVo);
		if (result != null) {
			System.out.println("비밀번호가 확인 되었습니다.");
			return "1";
		} else{
			System.out.println("비밀번호가 틀렸습니다.");
			return "0";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="boardCommentInsert.do", produces = "text/html; charset=utf-8")
	public String boardCommentInsert(BoardCommentVO comment) {
		int result = service.boardCommentInsert(comment);
		if (result == 1) {
			System.out.println("코멘트 입력 완료");
			return "1";
		} else{
			System.out.println("코멘트 입력 실패");
			return "0";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="selectOneComment.do", produces = "application/json; charset=utf-8")
	public BoardCommentVO selectOneComment(int boardNo) {
		BoardCommentVO boardComment = service.boardOneComment(boardNo);
		return boardComment;
	}
	
	@ResponseBody
	@RequestMapping(value="boardCommentList.do", produces = "application/json; charset=utf-8")
	public String boardCommentList(HttpServletRequest request, int boardNo) {
		BoardViewData data = service.boardCommentList(boardNo);
		request.setAttribute("b", data.getB());
		request.setAttribute("commentList", data.getCommentList());
		return new Gson().toJson(data.getCommentList());
	}
	
	@RequestMapping("/replyWriteFrm.do")
	public String replyWriteFrm(Model model, int boardNo, BoardData data) {
		model.addAttribute("data", data);
		model.addAttribute("boardNo", boardNo);
		return "board/replyWrite";
	}
	
	@RequestMapping(value="/replyWrite.do")
	public String replyWrite(BoardVO boardVo, BoardData boardData, HttpServletRequest request) {
		int result = service.replyInsert(boardVo);
		if (result == 1) {
			System.out.println("글쓰기 성공");
			
		} else {
			System.out.println("글쓰기 실패");
		}
		BoardData data = service.selectBoardList(boardData.getReqPage(), boardData.getType(), boardData.getKeyword());
		//return "redirect:/bw/board/boardList.do?reqPage"+ reqPage + "type=" + ${type} + "&keyword=" + ${keyword};
		request.setAttribute("list", data.getList());
		request.setAttribute("pageNavi", data.getPageNavi());
		request.setAttribute("reqPage", boardData.getReqPage());
		request.setAttribute("type", boardData.getType());
		request.setAttribute("keyword", boardData.getKeyword());
		request.setAttribute("totalPage", data.getTotalPage());
		request.setAttribute("totalCount", data.getTotalCount());
		request.setAttribute("numPerPage", data.getNumPerPage());
		return "redirect:/bw/board/boardList.do?reqPage=" + boardData.getReqPage() + "&type=" + boardData.getType() + "&keyword=" + boardData.getKeyword();
	}
	
	@ResponseBody
	@RequestMapping("/commentPwCheck.do")
	public String commentPwCheck(BoardCommentVO  bcv) {
		System.out.println(bcv.getBoardCommentNo() + "," + bcv.getBoardCommentPw());
		BoardCommentVO result = service.commentPwCheck(bcv);
		System.out.println(result);
		if(result != null) {
			return "1";
		} else {
			return "0";
		}
	}
	
	@ResponseBody
	@RequestMapping("/deleteComment.do")
	public String deleteComment(BoardCommentVO bcv) {
		int result = service.deleteComment(bcv);
		if(result == 1) {
			return "1";
		} else {
			return "0";
		}
	}
	
	@ResponseBody
	@RequestMapping("/modifyComment.do")
	public String modifyComment(BoardCommentVO bcv) {
		int result = service.modifyComment(bcv);
		if(result == 1) {
			return "1";
		} else {
			return "0";
		}
	}
	
	@ResponseBody
	@RequestMapping("/deleteFile.do")
	public String deleteFile(int boardNo) {
		int result = service.deleteFile(boardNo);
		if(result == 1) {
			return "1";
		} else {
			return "0";
		}
	}
	
	@RequestMapping("/excelUpFrm.do")
	public String excelUpFrm() {
		return "board/excelUpload";
	}
	
	@RequestMapping(value="/excelDownload.do", method = RequestMethod.POST)
	public String excelDownload(Locale locale, Model model, HttpServletRequest request) {
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", type);
		map.put("keyword", keyword);
		List<BoardVO> list = service.excelDownload(map);
		
		Calendar now = new GregorianCalendar();
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		int second = now.get(Calendar.SECOND);
		int MILLISECOND = now.get(Calendar.MILLISECOND);
		int year = now.get(Calendar.YEAR);
		StringBuffer str = new StringBuffer();
		str.append( year ).append( month ).append( day ).append( hour ).append( minute ).append( second ).append( MILLISECOND );
		
		model.addAttribute("filename", str);
		model.addAttribute("list", list);
		return "board/exportToExcel";
	}
	
//	@ResponseBody
//	@RequestMapping(value="/excelUp.do", method = RequestMethod.POST)
//	public Map<String, Object> excelUp(MultipartFile testFile, MultipartHttpServletRequest request) {
//		MultipartFile excelFile = request.getFile("excelFile");
//		if(excelFile == null || excelFile.isEmpty()) {
//			throw new RuntimeException("엑셀파일을 선택해 주세요");
//		}
//		File destFile = new File("C:\\Users\\lance\\Desktop\\" + excelFile.getOriginalFilename());
//		
//		try {
//			excelFile.transferTo(destFile);
//		} catch(Exception e) {
//			throw new RuntimeException(e.getMessage(),e);
//		}
//		Map<String, Object> result = service.ExcelUpload(destFile);
//		destFile.delete();
//		
//		return result;
//	}

}

















