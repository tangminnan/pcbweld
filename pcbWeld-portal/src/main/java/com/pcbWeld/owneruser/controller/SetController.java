package com.pcbWeld.owneruser.controller;

import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.owneruser.domain.SetDO;
import com.pcbWeld.owneruser.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-03-18 14:11:10
 */
 
@RestController
@RequestMapping("/pcbWeld/set")
public class SetController {
	@Autowired
	private SetService setService;
	
	
	/**
	 *获取设置
	 */
	@GetMapping("/getSet")
	public Map<String,Object> getUserSet(Integer flag){
		Map<String,Object> map = new HashMap<String,Object>();
		SetDO setDO=new SetDO();
		if(flag==1){//家长模式
			setDO = setService.getHouseHolderSet(ShiroUtils.getUser().getId());
			map.put("code", 0);
			map.put("msg", "成功");
			map.put("data", setDO);
		}
		else if(flag==2){//专业模式
			setDO = setService.getProfessionSet(ShiroUtils.getUser().getId());
			map.put("code", 0);
			map.put("msg", "成功");
			map.put("data", setDO);
		}
		return map;
	}
	
	/**
	 * 保存设置
	 */
	@PostMapping("/addSet")
	public Map<String,Object> addsetUser(SetDO setDO){
		setDO.setUserid(ShiroUtils.getUser().getId());
		SetDO so = setService.getHouseHolderSet(ShiroUtils.getUser().getId());
		int i=0;
		if(so==null) {
			i = setService.addsetUser(setDO);
		}else {
			i = setService.updatesetUser(setDO);
		}
		setService.updateFlag(setDO);
		Map<String,Object> map = new HashMap<String,Object>();
		if(i>0) {
			map.put("code", 0);
			map.put("msg", "保存成功");
			map.put("data", setDO);
		}else {
			map.put("code", -1);
			map.put("msg", "保存失败");
			map.put("data", null);
		}
		return map;
	}
	
	/**
	 * 获取模式
	 */
	
	@GetMapping("/getFlag")
	public Map<String,Object> getFlag(){
		SetDO setDO = setService.getFlag(ShiroUtils.getUser().getId());
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("code", 0);
		map.put("msg", "成功");
		map.put("data", setDO);
		return map;
	}
}
