package com.yaoyao.spring.boot.file.upload.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.yaoyao.spring.boot.file.upload.util.FileUtil;

@Controller
@RequestMapping("/file/upload")
public class FileUploadController {

	@Autowired
	private FileUtil fileUtil;

	@RequestMapping("")
	public String index(HttpServletRequest request) {

		request.setAttribute("ctx", request.getContextPath());
		return "file/upload/pl_upload";
	}

	@RequestMapping("/savePersistentFiles")
	@ResponseBody
	public JSONArray savePersistentFiles(HttpServletRequest request, @RequestParam("file") List<MultipartFile> uploadFiles) {
		JSONArray fileInfos = new JSONArray();

		fileInfos = fileUtil.storageFiles(uploadFiles, request.getContextPath());

		return fileInfos;
	}
}
