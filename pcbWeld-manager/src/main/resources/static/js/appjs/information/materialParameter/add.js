$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {


    var conss = [];
    $(".namePrice").each(function(){
        var conn = {
            parameterName : $(this).find("input[name='parameterName']").val(),
            price : $(this).find("input[name='price']").val(),
        }
        conss.push(conn)
    })
    var allData={
        itemId : $("#itemId option:selected").val(),
        isJunior : $("#isJunior option:selected").val(),
        namePrice:conss
    }
    console.info(allData);


	$.ajax({
		cache : true,
		type : "POST",
		url : "/information/materialParameter/save",
        contentType: "application/json",
        dataType: "json",
		data : JSON.stringify(allData),// 你的formid
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
            isJunior : {
				required : true
			}
		},
		messages : {
            isJunior : {
				required : icon + "必选"
			}
		}
	})
}