$().ready(function () {
   validateRule();
});

$.validator.setDefaults({
   submitHandler: function () {
      save();
   }
});

function save() {
   $.ajax({
      cache: true,
      type: "POST",
      url: "/information/materialItem/save",
      data: $('#signupForm').serialize(),// 你的formid
      async: false,
      error: function (request) {
         parent.layer.alert("Connection error");
      },
      success: function (data) {
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
      rules: {
         type: {
            required: true
         },
         displayForm: {
            required: true
         },
         price: {
            required: true
         },
         countWay: {
            required: true
         },
      },
      messages: {
         type: {
            required: icon + "类目名称不能为空"
         },
         displayForm: {
            required: icon + "显示样式不能为空"
         },
         price: {
            required: icon + "价格不能为空"
         },
         countWay: {
            required: icon + "计算方式不能为空"
         }

      }
   })
}