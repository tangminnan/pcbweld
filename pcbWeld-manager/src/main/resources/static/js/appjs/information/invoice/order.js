var prefix = "/information/invoice"
$(function () {
   load();
});

function load() {
   $('#exampleTable')
      .bootstrapTable(
         {
            method: 'get', // 服务器数据的请求方式 get or post
            url: prefix + "/list", // 服务器数据的加载地址
            //	showRefresh : true,
            //	showToggle : true,
            //	showColumns : true,
            iconSize: 'outline',
            toolbar: '#exampleToolbar',
            striped: true, // 设置为true会有隔行变色效果
            dataType: "json", // 服务器返回的数据类型
            pagination: true, // 设置为true会在底部显示分页条
            // queryParamsType : "limit",
            // //设置为limit则会发送符合RESTFull格式的参数
            singleSelect: false, // 设置为true将禁止多选
            // contentType : "application/x-www-form-urlencoded",
            // //发送到服务器的数据编码类型
            pageSize: 10, // 如果设置了分页，每页数据条数
            pageNumber: 1, // 如果设置了分布，首页页码
            //search : true, // 是否显示搜索框
            showColumns: false, // 是否显示内容下拉框（选择显示的列）
            sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
            queryParams: function (params) {
               return {
                  //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                  limit: params.limit,
                  offset: params.offset

                  // name:$('#searchName').val(),
                  // username:$('#searchName').val()
               };
            },
            // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
            // queryParamsType = 'limit' ,返回参数必须包含
            // limit, offset, search, sort, order 否则, 需要包含:
            // pageSize, pageNumber, searchText, sortName,
            // sortOrder.
            // 返回false将会终止请求
            columns: [
               {
                  checkbox: true
               },
               /*						{
            field : 'id',
            title : ''
         },
            */                     {
                  field: 'orderNo',
                  align: 'center',
                  title: '订单编号'
               },
               {
                  field: 'username',
                  align: 'center',
                  title: '用户'
               },

               {
                  field: 'invoiceType',
                  align: 'center',
                  title: '发票类型',
                  formatter: function (value) {
                     if (value == 1) {
                        return "不开发票"
                     } else if (value == 2) {
                        return "电子发票"
                     } else if (value == 3) {
                        return "普通发票"
                     } else {
                        return ""
                     }
                  }
               },
               {
                  field: 'invoiceStatus',
                  align: 'center',
                  title: '发票状态',
                  formatter: function (value) {
                     if (value == 1) {
                        return "待开"
                     } else if (value == 2) {
                        return "已开"
                     } else {
                        return ""
                     }
                  }
               },
               {
                  field: 'orderStatus',
                  align: 'center',
                  title: '订单状态',
                  formatter: function (value) {
                     if (value == 1) {
                        return "已提交"
                     } else if (value == 2) {
                        return "资料待审核"
                     } else if (value == 3) {
                        return "审核未通过"
                     } else if (value == 4) {
                        return "待支付"
                     } else if (value == 5) {
                        return "物料寄送"
                     } else if (value == 6) {
                        return "物料待审核"
                     } else if (value == 7) {
                        return "物料审核失败"
                     } else if (value == 8) {
                        return "待发货"
                     } else if (value == 9) {
                        return "待收货"
                     } else if (value == 10) {
                        return "已完成"
                     } else {
                        return ""
                     }
                  }
               },

               {
                  field: 'createTime',
                  align: 'center',
                  title: '创建时间'
               },
               {
                  field: 'updateTime',
                  align: 'center',
                  title: '更新时间'
               },

               {
                  field: 'payAmount',
                  align: 'center',
                  title: '支付总金额'
               },
               {
                  field: 'buyNumber',
                  align: 'center',
                  title: '商品总数量'
               },
               {
                  field: 'address',
                  align: 'center',
                  title: '收货人地址'
               },
               {
                  field: 'consignee',
                  align: 'center',
                  title: '收货人'
               },
               {
                  field: 'mobile',
                  align: 'center',
                  title: '收货手机号'
               },
               {
                  field: 'invoiceNo',
                  align: 'center',
                  title: '公司税号'
               },
               {
                  field: 'invoiceTitle',
                  align: 'center',
                  title: '公司抬头'
               },
               {
                  title: '操作',
                  field: 'id',
                  align: 'center',
                  formatter: function (value, row, index) {
                     var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                        + row.id
                        + '\')"><i class="fa fa-remove">删除</i></a> ';

                     var f = '<a class="btn btn-success btn-sm" href="#" title="开发票"  mce_href="#" onclick="giveInvoice(\''
                        + row.id
                        + '\')"><i class="fa fa-key">开发票</i></a> ';

                     return d + f
                  }
               }],
         });
}

function reLoad() {
   $('#exampleTable').bootstrapTable('refresh');
}

//重新审核物料
function giveInvoice(id) {
   layer.confirm('确定发票已开吗？', {
      btn: ['确定', '取消']
   }, function () {
      $.ajax({
         url: "/information/order/update",
         type: "post",
         data: {
            'id': id,
            invoiceStatus: 2
         },
         success: function (r) {
            if (r.code == 0) {
               layer.msg(r.msg);
               reLoad();
            } else {
               layer.msg(r.msg);
            }
         }
      });
   })
}


function add() {
   layer.open({
      type: 2,
      title: '增加',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: prefix + '/add' // iframe的url
   });
}

function edit(id) {
   layer.open({
      type: 2,
      title: '编辑',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: prefix + '/edit/' + id // iframe的url
   });
}

function remove(id) {
   layer.confirm('确定要删除选中的记录？', {
      btn: ['确定', '取消']
   }, function () {
      $.ajax({
         url: prefix + "/remove",
         type: "post",
         data: {
            'id': id
         },
         success: function (r) {
            if (r.code == 0) {
               layer.msg(r.msg);
               reLoad();
            } else {
               layer.msg(r.msg);
            }
         }
      });
   })
}

function resetPwd(id) {
}

function batchRemove() {
   var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
   if (rows.length == 0) {
      layer.msg("请选择要删除的数据");
      return;
   }
   layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
      btn: ['确定', '取消']
      // 按钮
   }, function () {
      var ids = new Array();
      // 遍历所有选择的行数据，取每条数据对应的ID
      $.each(rows, function (i, row) {
         ids[i] = row['id'];
      });
      $.ajax({
         type: 'POST',
         data: {
            "ids": ids
         },
         url: prefix + '/batchRemove',
         success: function (r) {
            if (r.code == 0) {
               layer.msg(r.msg);
               reLoad();
            } else {
               layer.msg(r.msg);
            }
         }
      });
   }, function () {

   });
}