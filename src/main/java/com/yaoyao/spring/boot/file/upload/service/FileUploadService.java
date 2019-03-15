package com.yaoyao.spring.boot.file.upload.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yaoyao.spring.boot.file.upload.util.FileUtil;
import com.yaoyao.spring.boot.file.upload.util.MyUtil;

@Service
public class FileUploadService {

	@Autowired
	private FileUtil fileUtil;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 保存公共文件
	 * 
	 * @param uploadFiles
	 * 上传文件
	 * @param contextPath
	 * 文件资源上下文路径
	 */
	public JSONArray saveCommonFiles(List<MultipartFile> uploadFiles, String contextPath) {
		String savePathPrefixName = "common.file.save.path.prefix";
		String networkPathPrefixName = "common.file.network.path.prefix";
		JSONArray fileInfos = new JSONArray();
		// 存储文件
		fileInfos = fileUtil.storageFiles(uploadFiles, contextPath, savePathPrefixName, networkPathPrefixName);
		// 去除文件真实路径信息
		if (fileInfos != null && !fileInfos.isEmpty()) {
			for (int i = 0, len = fileInfos.size(); i < len; i++) {
				JSONObject fileInfo = fileInfos.getJSONObject(i);
				fileInfo.remove("fileRealPath");
			}
		}
		return fileInfos;
	}

	/**
	 * 保存导入Excel文件
	 * 
	 * @param uploadFiles
	 * 上传文件
	 * @param contextPath
	 * 文件资源上下文路径
	 * @return
	 */
	@Transactional
	public JSONArray saveImportExcelFiles(List<MultipartFile> uploadFiles, String contextPath) {
		String savePathPrefixName = "import.excel.file.save.path.prefix";
		String networkPathPrefixName = "import.excel.file.network.path.prefix";
		JSONArray fileInfos = new JSONArray();
		// 存储文件
		fileInfos = fileUtil.storageFiles(uploadFiles, contextPath, savePathPrefixName, networkPathPrefixName);
		if (fileInfos != null && !fileInfos.isEmpty()) {
			List<Object> paras = new ArrayList<>();
			String sql = "insert into t_import_excel_file (id, filename, filetype, filerealpath, filepath, createtime) values (?, ?, ?, ?, ?, ?)";
			for (int i = 0, len = fileInfos.size(); i < len; i++) {
				JSONObject fileInfo = fileInfos.getJSONObject(i);
				paras.clear();
				paras.add(MyUtil.getUUID());
				paras.add(fileInfo.getString("fileName"));
				paras.add(fileInfo.getString("fileType"));
				paras.add(fileInfo.getString("fileRealPath"));
				paras.add(fileInfo.getString("filePath"));
				paras.add(new Date());
				jdbcTemplate.update(sql, paras.toArray());
			}
		}
		return fileInfos;
	}

	/**
	 * 查询导入Excel文件
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getImportExcelFiles() {
		String sql = "select * from t_import_excel_file";
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
		if (results != null && !results.isEmpty()) {
			for (int i = 0, len = results.size(); i < len; i++) {
				System.out.println(JSONObject.toJSON(results.get(i)));
			}
		}
		return results;
	}
}
