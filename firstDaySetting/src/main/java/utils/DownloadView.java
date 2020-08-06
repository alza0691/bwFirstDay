package utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		File file = (File)model.get("downloadFile");
        if(file != null) {
            String fileName = null;
            String userAgent = request.getHeader("User-Agent");
            
            if(userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1){
                fileName = URLEncoder.encode(file.getName(), "utf-8").replaceAll("\\+", "%20");;
            }else if(userAgent.indexOf("Chrome") > -1) {
            	StringBuffer sb = new StringBuffer();
            	for(int i=0; i<file.getName().length(); i++) {
            		char c = file.getName().charAt(i);
            		if(c > '~') {
            			sb.append(URLEncoder.encode(""+c, "UTF-8"));
            		}else {
            			sb.append(c);
            		}
            	}
            	fileName = sb.toString();
            }else {
            	fileName = new String(file.getName().getBytes("utf-8"));
            }
            response.setContentType(getContentType());
            response.setContentLength((int)file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");
            
            OutputStream out = response.getOutputStream();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                FileCopyUtils.copy(fis, out);
            } catch(Exception e){
                e.printStackTrace();
            }finally{
                if(fis != null){
                    try{
                        fis.close();
                    }catch(Exception e){
                    	e.printStackTrace();
                    }
                }
                
                if(out != null) {
                	out.flush();
                }
            }
            
        }
	}
}