package com.yaoyao.spring.boot.file.upload.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.yaoyao.spring.boot.file.upload.service.FileUploadService;

@Controller
@RequestMapping("/file/upload")
public class FileUploadController {

	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping("")
	public String index(HttpServletRequest request, String source) {
		request.setAttribute("ctx", request.getContextPath());
		String viewName = "";
		if ("import".equals(source)) {
			viewName = "file/upload/import_excel_upload";
		} else {
			viewName = "file/upload/common_upload";
		}
		return viewName;
	}

	/**
	 * 保存公共公共文件
	 * 
	 * @param uploadFiles
	 * @param contextPath
	 * @return
	 */
	@RequestMapping("/savePersistentFiles")
	@ResponseBody
	public JSONArray saveCommonFiles(@RequestParam("file") List<MultipartFile> uploadFiles,
			@RequestParam(value = "contextPath", defaultValue = "") String contextPath) {
		JSONArray fileInfos = new JSONArray();
		// 保存公共文件
		fileInfos = fileUploadService.saveCommonFiles(uploadFiles, contextPath);

		return fileInfos;
	}

	/**
	 * 保存导入Excel文件
	 * 
	 * @param uploadFiles
	 * @param contextPath
	 * @return
	 */
	@RequestMapping("/saveImportExcelFiles")
	@ResponseBody
	public JSONArray saveImportExcelFiles(@RequestParam("file") List<MultipartFile> uploadFiles,
			@RequestParam(value = "contextPath", defaultValue = "") String contextPath) {
		JSONArray fileInfos = new JSONArray();
		// 保存导入Excel文件
		fileInfos = fileUploadService.saveImportExcelFiles(uploadFiles, contextPath);

		return fileInfos;
	}

	/**
	 * 查询导入Excel文件
	 * 
	 * @return
	 */
	@RequestMapping("/getImportExcelFiles")
	@ResponseBody
	public List<Map<String, Object>> getImportExcelFiles() {
		return fileUploadService.getImportExcelFiles();
	}
}
