package com.pcbWeld.information.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcbWeld.information.domain.MaterialExamineDO;
import com.pcbWeld.information.service.MaterialExamineService;
import com.pcbWeld.common.utils.PageUtils;
import com.pcbWeld.common.utils.Query;
import com.pcbWeld.common.utils.R;

/**
 * 资料审核
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-28 11:28:36
 */
 
@Controller
@RequestMapping("/information/materialExamine")
public class MaterialExamineController {
	@Autowired
	private MaterialExamineService materialExamineService;
	
	@GetMapping()
	@RequiresPermissions("information:materialExamine:materialExamine")
	String MaterialExamine(){
	    return "information/materialExamine/materialExamine";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:materialExamine:materialExamine")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<MaterialExamineDO> materialExamineList = materialExamineService.list(query);
		int total = materialExamineService.count(query);
		PageUtils pageUtils = new PageUtils(materialExamineList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:materialExamine:add")
	String add(){
	    return "information/materialExamine/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:materialExamine:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		MaterialExamineDO materialExamine = materialExamineService.get(id);
		model.addAttribute("materialExamine", materialExamine);
	    return "information/materialExamine/edit";
	}
	
	@GetMapping("/xiangqing/{id}")
	@RequiresPermissions("information:materialExamine:edit")
	String xiangqing(@PathVariable("id") Integer id,Model model){
		MaterialExamineDO materialExamine = materialExamineService.get(id);
		model.addAttribute("materialExamine", materialExamine);
	    return "information/materialExamine/xiangqing";
	}
	
	@GetMapping("/shenhe/{id}")
	@RequiresPermissions("information:materialExamine:edit")
	String shenhe(@PathVariable("id") Integer id,Model model){
		MaterialExamineDO materialExamine = materialExamineService.get(id);
		model.addAttribute("materialExamine", materialExamine);
	    return "information/materialExamine/shenhe";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:materialExamine:add")
	public R save( MaterialExamineDO materialExamine){
		if(materialExamineService.save(materialExamine)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:materialExamine:edit")
	public R update( MaterialExamineDO materialExamine){
		materialExamineService.update(materialExamine);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:materialExamine:remove")
	public R remove( Integer id){
		if(materialExamineService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:materialExamine:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		materialExamineService.batchRemove(ids);
		return R.ok();
	}
	
	
	
	@GetMapping("/fujiandown")
	void fujiandown(Integer id,String host,HttpServletRequest request,HttpServletResponse response){
		MaterialExamineDO materialExamineDO = materialExamineService.get(id);
		String files = materialExamineDO.getFiles();
		File file ;
		if (files != null) {
			String Url = host+files;
			file = new File(Url);
			try{
				if (file.exists()) {
					String filename = file.getName();
					response.reset();
					response.setHeader("Content-Disposition", "attachment; filename=" +  URLEncoder.encode(filename, "UTF-8"));
					response.addHeader("Content-Length", "" + file.length());
					response.setContentType("application/octet-stream; charset=UTF-8");
	
					FileInputStream fis = new FileInputStream(file);
			        ByteArrayOutputStream bos = new ByteArrayOutputStream();
			        byte[] b = new byte[1024];
			        int n;
			        while ((n = fis.read(b)) != -1){
			             bos.write(b, 0, n);
			        }
			        fis.close();
			        bos.close();
			            
			        IOUtils.write(bos.toByteArray(), response.getOutputStream());
				}
		     }catch (FileNotFoundException e){
		        e.printStackTrace();
		     }catch (IOException e){
		        e.printStackTrace();
		     }
		}
	}
	
	
	
	
	
}
