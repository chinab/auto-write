package com.autowrite.common.framework.bean;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartRequest;

import com.autowrite.common.framework.dao.AddressDao;
import com.autowrite.common.framework.entity.AddressEntity;
import com.autowrite.common.framework.entity.AddressListEntity;

@Component
public class AddressService extends CommonService{

	@Autowired 
    AdminService adminService;
	
	@Autowired
	AddressDao addressDao;

	public AddressService() {
	}

	
	/**
	 * 개인별 사이트 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public AddressListEntity listPrivateAddress(Map param) throws Exception {
		setCondition(param);
		
		AddressListEntity listEntity = new AddressListEntity();
		
		try {
			Long pageNum = (Long) param.get("PAGE_NUM");
			Long pageSize = (Long) param.get("PAGE_SIZE");
			listEntity.setPageNum(pageNum);
			listEntity.setPageSize(pageSize);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		Long boardCount = addressDao.countListPrivateAddress(param);
		listEntity.setTotalListCount(boardCount);
		
		List<AddressEntity> addressList = addressDao.listPrivateAddress(param);
		
		listEntity.setAddressList(addressList);
		
		return listEntity;
	}


	public AddressEntity addressView(Map param) {
		AddressEntity boardEntity = addressDao.addressView(param);
		
		return boardEntity;
	}


	public AddressListEntity writePrivateAddress(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		addressDao.writePrivateAddress(param);
		
		return listPrivateAddress(param);
	}
	
	
	
	public AddressListEntity modifyAddress(HttpServletRequest req, Map param) throws Exception {
		setCondition(param);
		
		addressDao.modifyPrivateAddress(param);
		
		return listPrivateAddress(param);
	}


	public void deleteAddress(HttpServletRequest req, Map param) {
		setCondition(param);
		
		addressDao.deletePrivateAddress(param);
	}
	
	private void fileUpload(HttpServletRequest req, Map param) throws IOException {
		MultipartRequest multipartRequest = (MultipartRequest) req;
		
		Iterator files = multipartRequest.getFileNames();
		
		if ( files.hasNext() ){
			// office dev
//			String baseDir = "D:\\eGovFrameDev-2.0.1-FullVer\\workspace\\autowrite\\WebContent\\upload";
			
			// home dev
//			String baseDir = "C:\\dev\\junoWork\\autowrite\\WebContent\\upload";
			
			// server dev
//			String baseDir = "D:\\apache-tomcat-6.0.36\\webapps\\autowrite\\upload";
//			
//			String baseDir = (String) servletContext.getAttribute("UPLOAD_BASE_DIR");
//			if ( baseDir == null ){
//				baseDir = PropertyUtil.getProperty("UPLOAD_BASE_DIR");
//				servletContext.setAttribute("UPLOAD_BASE_DIR", baseDir);
//			}
//			
//			String webDir = req.getContextPath() + File.separator + "upload";
//			String uploadDir = FileUtil.buildDatePathDir(baseDir);
//			
//			long seqId = 0;
//			
//			if (param.get("SEQ_ID") instanceof Long){
//				seqId = (Long) param.get("SEQ_ID") ;
//			} else if  (param.get("SEQ_ID") instanceof String) {
//				seqId = new Long(param.get("SEQ_ID").toString());
//			} else {
//				seqId = (Long) param.get("SEQ_ID") ;
//			}
//			
//			while ( files.hasNext() ) {
//				String attachmentName = files.next().toString();
//				
//				MultipartFile multipartFile = multipartRequest.getFile(attachmentName);
//				
//				String originalFileName = multipartFile.getOriginalFilename();
//				
//				if ( originalFileName.length() > 0 ) {
//					
//					String targetFileName = "";
//					targetFileName = System.currentTimeMillis()+"";
//					File target = new File(uploadDir, targetFileName);
//					
//					String targetPath = uploadDir + File.separator + targetFileName;
//				    String webPath = webDir + uploadDir.substring(baseDir.length(), uploadDir.length()) + File.separator + targetFileName;
//				    String thumbnailPath = uploadDir + File.separator + "thumb_" + targetFileName + ".jpg";
//				    String thumbnailWebPath = webDir + uploadDir.substring(baseDir.length(), uploadDir.length()) + File.separator + "thumb_" + targetFileName + ".jpg";
//				    
//				    multipartFile.transferTo(target);
//				    
//				    Thumbnailer Thumbnailer = new Thumbnailer();
//					Thumbnailer.createImageThumbnail(targetPath, thumbnailPath, 100, 100);
//					
//				   
//					param.put("TABLE_SEQ_ID", seqId);
//					param.put("P_FILE_NAME", originalFileName);
//					param.put("L_FILE_NAME", webPath);
//					param.put("THUMBNAIL_FILE_NAME", thumbnailWebPath);
//					param.put("FILE_EXT", "");
//					param.put("FILE_SIZE", multipartFile.getSize());
//					param.put("DEL_YN", "N");
//					
//					boardDao.writeAttachmentFile(param);
//				}
//			}
		}
	}
	
	public List<AddressEntity> extractExcelContents(Map param, File excelFile) throws Exception {
		
		List<AddressEntity> adressList = null;
				
		if (excelFile != null) {

			Workbook workbook = null;
			
			String vCell = "";
			String stnditmftx = "";
			
			if (excelFile.exists()) {

				int row = 1;
				int i = 0;
				StringBuilder msg = new StringBuilder();
				
				try {
					workbook = Workbook.getWorkbook(excelFile);
					
					if (workbook.getNumberOfSheets() > 0) {
						
						Sheet[] mySheets = workbook.getSheets();
						
						if (mySheets.length > 0) {
							adressList = new ArrayList();
							
							Sheet sheet1 = mySheets[0];
							
							for (i = 1; i < sheet1.getRows(); i++) { // 행
								AddressEntity addressEntity = new AddressEntity();
								adressList.add(addressEntity);
								
								addressEntity.setWriter_ip((String) param.get("WRITER_IP"));
								addressEntity.setWriter_seq_id((String) param.get("WRITER_SEQ_ID"));
								
								for (int columnNo = 0; columnNo < sheet1.getColumns(); columnNo++) { // 열

									Cell iCell = sheet1.getCell(columnNo, i);
									vCell = iCell.getContents();
									
//									System.out.println(vCell);
									
									switch (columnNo) {
									
										case 0://	이름	name
											addressEntity.setName(vCell);
											break;
										
										case 1://	휴대폰번호	cell_phone
											addressEntity.setCell_phone(vCell);
											break;
										
										case 2://	회사번호	company_phone
											addressEntity.setCompany_phone(vCell);
											break;
										
										case 3://	집번호	home_phone
											addressEntity.setHome_phone(vCell);
											break;
										
										case 4://	FAX	fax
											addressEntity.setFax(vCell);
											break;
										
										case 5://	내선번호	extention_number
											addressEntity.setExtention_number(vCell);
											break;
										
										case 6://	기타번호	etc_number
											addressEntity.setEtc_number(vCell);
											break;
										
										case 7://	이메일	email
											addressEntity.setEmail(vCell);
											break;
										
										case 8://	생일(양력/음력)	birth_day
											addressEntity.setBirth_day(vCell);
											break;
										
										case 9://	결혼(양력/음력)	marry_day
											addressEntity.setMarry_day(vCell);
											break;
										
										case 10://	기념일(양력/음력)	anniversary
											addressEntity.setAnniversary(vCell);
											break;
										
										case 11://	기타기념일(양력/음력)	etc_anniversary
											addressEntity.setEtc_anniversary(vCell);
											break;
										
										case 12://	개인홈피_블로그	homepage
											addressEntity.setHomepage(vCell);
											break;
										
										case 13://	회사_학교명	company_name
											addressEntity.setCompany_name(vCell);
											break;
										
										case 14://	부서_학과	company_division
											addressEntity.setCompany_division(vCell);
											break;
										
										case 15://	직급_학번	company_class
											addressEntity.setCompany_class(vCell);
											break;
										
										case 16://	집우편번호	home_zipcode
											addressEntity.setHome_zipcode(vCell);
											break;
										
										case 17://	집주소	home_address
											addressEntity.setHome_address(vCell);
											break;
										
										case 18://	회사우편번호	company_zipcode
											addressEntity.setCompany_zipcode(vCell);
											break;
										
										case 19://	회사주소	company_address
											addressEntity.setCompany_address(vCell);
											break;
										
										case 20://	기타우편번호	etc_zipcode
											addressEntity.setEtc_zipcode(vCell);
											break;
										
										case 21://	기타주소	etc_address
											addressEntity.setEtc_address(vCell);
											break;
										
										case 22://	기타_메모	memo
											addressEntity.setMemo(vCell);
											break;
											
										default:
											break;

									}// switch
								} // for 행
							}// for 열
						}// mysheet
					}// workbook
					
				} catch (Exception ex) {
					throw new Exception(i + "번째 행에 입력오류가 있습니다. 확인바랍니다.");
				} finally {
					if (workbook != null)
						try {
							workbook.close();
						} catch (Exception e) {
						}
					if (excelFile.exists())
						excelFile.delete();
				}
			}
		}
		
		return adressList;
	}


	/**
	 * adressList의 내용을 가지고 주소록을 업로드한다.
	 * @param req
	 * @param adressList
	 */
	public void writeExcelAddressList(List<AddressEntity> adressList) {
		try {
			addressDao.writeExcelAddressList(adressList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * 엑셀 업로드 내역을 기록한다.
	 * @param req
	 * @param adressList
	 */
	public void writeExcelAddressMaster(HttpServletRequest req, List<AddressEntity> adressList) {
		// TODO Auto-generated method stub
		
	}
}
