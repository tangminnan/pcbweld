package com.pcbWeld.users.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcbWeld.common.utils.PageUtils;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.users.domain.ResultAcupunctureDo;
import com.pcbWeld.users.service.ResultAcupunctureService;

@Controller
@RequestMapping("/pcbWeld/acupunctureResult")
public class AcupunctureController {

	@Autowired
	private ResultAcupunctureService rsService;

	@PostMapping("/getDetail/{id}")
	@ResponseBody
	public PageUtils getDetail(@PathVariable("id") Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", id);
		List<ResultAcupunctureDo> list = rsService.list(params);
		int total = rsService.count(params);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Integer id, Model model) {
		ResultAcupunctureDo result = rsService.get(id);
		System.out.println(result.getFirstCheckDate());
		model.addAttribute("result", result);
		return "users/acupunctureEdit";
	}

	@ResponseBody
	@PostMapping("/updateAcupuncture")
	public R update(ResultAcupunctureDo rsDo) {
		rsService.update(rsDo);
		return R.ok();
	}

	@GetMapping("/add/{id}")
	String add(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("id", id);
		return "users/acupunctureAdd";
	}

	@ResponseBody
	@PostMapping("/addAcupuncture")
	public R save(ResultAcupunctureDo rsDo) {
		if (rsService.save(rsDo) > 0) {
			return R.ok();
		}
		return R.error();
	}

}
