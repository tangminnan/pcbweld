$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	var type = $('input[name = "type"]:checked').val();
	if(type == "1"){
		var forIds = $('input[name = "forIds"]').val();
		if(forIds.length==0 || forIds == " "){
			return alert("接收人id不能为空！！！");
		}
	} 
	
	$.ajax({
		cache : true,
		type : "POST",
		url : "/information/msg/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			},
			/*forIds : {
				required : true
			},*/
			forDetails : {
				required : true
			},
			type : {
				required : true
			},
		},
		messages : {
			name : {
				required : icon + "不能为空"
			},
			/*forIds : {
				required : icon + "不能为空"
			},*/
			forDetails : {
				required : icon + "不能为空"
			},
			type : {
				required : icon + "必选"
			},
		}
	})
}