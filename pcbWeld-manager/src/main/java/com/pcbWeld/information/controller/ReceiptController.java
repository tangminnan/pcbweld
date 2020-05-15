package com.pcbWeld.information.controller;

import java.util.List;
import java.util.Map;

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

import com.pcbWeld.information.domain.ReceiptDO;
import com.pcbWeld.information.service.ReceiptService;
import com.pcbWeld.common.utils.PageUtils;
import com.pcbWeld.common.utils.Query;
import com.pcbWeld.common.utils.R;

/**
 * 发票表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-13 10:02:23
 */
 
@Controller
@RequestMapping("/information/receipt")
public class ReceiptController {
	@Autowired
	private ReceiptService receiptService;
	
	@GetMapping()
	@RequiresPermissions("information:receipt:receipt")
	String Receipt(){
	    return "information/receipt/receipt";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:receipt:receipt")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ReceiptDO> receiptList = receiptService.list(query);
		int total = receiptService.count(query);
		PageUtils pageUtils = new PageUtils(receiptList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:receipt:add")
	String add(){
	    return "information/receipt/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:receipt:edit")
	String edit(@PathVariable("id") Long id,Model model){
		ReceiptDO receipt = receiptService.get(id);
		model.addAttribute("receipt", receipt);
	    return "information/receipt/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:receipt:add")
	public R save( ReceiptDO receipt){
		if(receiptService.save(receipt)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( ReceiptDO receipt){
		receiptService.update(receipt);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( Long id){
		ReceiptDO receiptDO = new ReceiptDO();
		receiptDO.setId(id);
		receiptDO.setDeleteFlag(1);
		if(receiptService.update(receiptDO)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:receipt:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		receiptService.batchRemove(ids);
		return R.ok();
	}

	/**
	 *修改开发票状态
	 */
	@PostMapping("/editReceipt")
	@ResponseBody
	public R editReceipt(long id){
		ReceiptDO receiptDO = new ReceiptDO();
		receiptDO.setId(id);
		receiptDO.setReceiptStatus(1);//已开发票
		if(receiptService.update(receiptDO) >0)
			return R.ok();
		return R.error();
	}
	
}
