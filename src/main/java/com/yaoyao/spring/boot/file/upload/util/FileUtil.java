package com.yaoyao.spring.boot.file.upload.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class FileUtil {

	/**
	 * 存储文件
	 * 
	 * @param uploadFiles
	 * 上传文件
	 * @param contextPath
	 * 文件资源上下文路径
	 * @param savePathPrefixName
	 * 文件保存路径前缀名
	 * @param networkPathPrefixName
	 * 文件网络路径前缀名
	 * @return
	 */
	public JSONArray storageFiles(List<MultipartFile> uploadFiles, String contextPath, String savePathPrefixName, String networkPathPrefixName) {
		if (uploadFiles == null) {
			return null;
		}
		Configuration configuration = null;
		try {
			configuration = new PropertiesConfiguration("myconfig.properties");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		// 文件信息数组
		JSONArray fileInfos = new JSONArray();
		// 获取文件保存路径前缀
		String fileSavePathPrefix = configuration.getString(savePathPrefixName);
		// 获取文件网络路径前缀
		String fileNetworkPathPrefix = configuration.getString(networkPathPrefixName);
		for (int i = 0, len = uploadFiles.size(); i < len; i++) {
			JSONObject fileInfo = new JSONObject();
			// 获取上传文件
			MultipartFile uploadFile = uploadFiles.get(i);
			// 生成文件目录
			String datePath = "/" + DateUtil.getDate("yyyy/MM/dd");
			String fileName = "/" + MyUtil.getUUID() + "_" + uploadFile.getOriginalFilename();
			String fileSavePath = fileSavePathPrefix + datePath + fileName;
			fileSavePath = fileSavePath.replace("/", File.separator);
			File file = new File(fileSavePath);
			// 创建文件目录
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			// 保存文件
			try {
				uploadFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 生成文件信息
			fileInfo.put("fileName", uploadFile.getOriginalFilename());
			fileInfo.put("fileType", getFileContentType(file));
			fileInfo.put("fileRealPath", fileSavePath);
			fileInfo.put("filePath", (contextPath + fileNetworkPathPrefix + datePath + fileName).replace("\\", "/"));
			// 添加文件信息
			fileInfos.add(fileInfo);
		}

		return fileInfos;
	}

	/**
	 * 获取文件类型
	 * 
	 * @param file
	 * @return
	 */
	public String getFileContentType(File file) {
		try {
			return Files.probeContentType(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
