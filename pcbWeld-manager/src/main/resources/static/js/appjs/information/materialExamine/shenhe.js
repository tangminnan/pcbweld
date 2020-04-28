$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	var examineFlag = $('#examineFlag option:selected').val();
	if(examineFlag == "2"){
		$("#examineIdea").val("审核通过");
	}
	if(examineFlag == "3"){
		var examineIdea = $("#examineIdea").val();
		if(examineIdea.length==0||examineIdea==" "){
			return "审核备注/意见不能为空！！！"
		}
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : "/information/materialExamine/update",
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
			examineFlag : {
				required : true
			}
		},
		messages : {
			examineFlag : {
				required : icon + "必选"
			}
		}
	})
}