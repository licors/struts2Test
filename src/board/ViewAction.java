package board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URLEncoder;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.opensymphony.xwork2.ActionSupport;

public class ViewAction extends ActionSupport{
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	private BoardVO paramClass = new BoardVO();
	private BoardVO resultClass = new BoardVO();
	
	private int currentPage;
	private int no;
	private String password; 
	private String fileUploadPath="C:\\java\\upload\\";
	
	private InputStream inputStream;
	private String contentDisposition;
	private long contentLength;
	
	public ViewAction() throws IOException {
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	
	public String execute() throws Exception {
		paramClass.setNo(getNo());
		sqlMapper.update("updateReadHit",paramClass);
		
		resultClass = (BoardVO) sqlMapper.queryForObject("selectOne",getNo());
		return SUCCESS;
	}

	public String download() throws Exception {
		resultClass = (BoardVO) sqlMapper.queryForObject("selectOne",getNo());
		File fileInfo = new File(fileUploadPath + resultClass.getFile_savname());
		
		setContentLength(fileInfo.length());
		setContentDisposition("attachment;filename="+URLEncoder.encode(resultClass.getFile_orgname(), "UTF-8"));
		setInputStream(new FileInputStream(fileUploadPath + resultClass.getFile_savname()));
		
		return SUCCESS;
	}
	
	public String checkForm() throws Exception {
		return SUCCESS;
	}
	
	public String checkAction() throws Exception {
		paramClass.setNo(getNo());
		paramClass.setPassword(getPassword());
		resultClass = (BoardVO) sqlMapper.queryForObject("selectPassword",paramClass);
		
		if(resultClass == null) {
			return ERROR;
		}
		return SUCCESS;
	}
	
	public BoardVO getParamClass() {
		return paramClass;
	}

	public BoardVO getResultClass() {
		return resultClass;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getNo() {
		return no;
	}

	public String getPassword() {
		return password;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setParamClass(BoardVO paramClass) {
		this.paramClass = paramClass;
	}

	public void setResultClass(BoardVO resultClass) {
		this.resultClass = resultClass;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
	
	
}
