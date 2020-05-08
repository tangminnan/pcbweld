var prefix = "/information/order"
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
                  offset: params.offset,
                  orderStatus: $('#orderStatus').val(),
                  orderNo: $('#orderNo').val()

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
                  field: 'payType',
                  title: '支付方式',
                  align: 'center',
                  formatter: function (value) {
                     if (value == 0) {
                        return "积分兑换"
                     } else if (value == 1) {
                        return "在线支付"
                     } else {
                        return ""
                     }
                  }
               },
               {
                  field: 'shipmentTime',
                  align: 'center',
                  title: '配送时间',
                  formatter: function (value) {
                     if (value == 1) {
                        return "不限送货时间"
                     } else if (value == 2) {
                        return "工作日送货"
                     } else if (value == 3) {
                        return "双休日、假日送货"
                     } else {
                        return ""
                     }
                  }
               },
               {
                  field: 'shipmentType',
                  align: 'center',
                  title: '配送方式',
                  formatter: function (value) {
                     if (value == 0) {
                        return "免运费"
                     } else if (value == 1) {
                        return "运费"
                     } else {
                        return ""
                     }
                  }
               },
               {
                  field: 'shipmentAmount',
                  align: 'center',
                  title: '快递费'
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
                  field: 'postid',
                  align: 'center',
                  title: '运单号'
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
                  field: 'orderAmount',
                  align: 'center',
                  title: '订单金额'
               },
               {
                  field: 'orderScore',
                  align: 'center',
                  title: '订单积分'
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
               }, {
                  field: 'unDataResult',
                  align: 'center',
                  title: '资料审核<br>失败原因'
               },
               {
                  field: 'files',
                  align: 'center',
                  title: '资料附件'
               }, {
                  field: 'unMaterialResult',
                  align: 'center',
                  title: '物料审核<br>失败原因'
               },
               {
                  title: '操作',
                  field: 'id',
                  align: 'center',
                  formatter: function (value, row, index) {
                     var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                        + row.id
                        + '\')"><i class="fa fa-remove">删除</i></a> ';

                     var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
                        + row.id
                        + '\')"><i class="fa fa-key"></i></a> ';
                     var checkData = '<a class="btn btn-primary btn-sm" href="#" title="审核资料"  mce_href="#" onclick="checkData(\''
                        + row.id
                        + '\')"><i class="fa fa-edit">审核</i></a> ';
                     var recheckData = '<a class="btn btn-primary btn-sm" href="#" title="重新审核资料"  mce_href="#" onclick="checkData(\''
                        + row.id
                        + '\')"><i class="fa fa-edit">重新审核</i></a> ';
                     var checkMaterial = '<a class="btn btn-primary btn-sm" href="#" title="审核物料"  mce_href="#" onclick="checkData(\''
                        + row.id
                        + '\')"><i class="fa fa-edit">去审核</i></a> ';
                     var recheckMaterial = '<a class="btn btn-primary btn-sm" href="#" title="审核物料"  mce_href="#" onclick="recheckMaterial(\''
                        + row.id
                        + '\')"><i class="fa fa-edit">重新审核</i></a> ';
                     var remindPay = '<a class="btn btn-primary btn-sm" href="#" title="提醒支付"  mce_href="#" onclick="remindPay(\''
                        + row.userId
                        + '\')"><i class="fa fa-edit">提醒支付</i></a> ';
                     var checkLogistics = '<a class="btn btn-primary btn-sm" href="#" title="查看物流"  mce_href="#" onclick="checkWuliu(\''
                        + row.postid
                        + '\')"><i class="fa fa-edit">查看物流</i></a> ';
                     var affirmShouhuo = '<a class="btn btn-primary btn-sm" href="#" title="确认收货"  mce_href="#" onclick="affirmShouhuo(\''
                        + row.id
                        + '\')"><i class="fa fa-edit">确认收货</i></a> ';
                     var deliver = '<a class="btn btn-primary btn-sm" href="#" title="去发货"  mce_href="#" onclick="deliver(\''
                        + row.id
                        + '\')"><i class="fa fa-edit">去发货</i></a> ';
                     var achieve = '<a class="btn btn-primary btn-sm" href="#" title="已完成"  mce_href="#" onclick="achieve(\''
                        + row.id
                        + '\')"><i class="fa fa-edit">已完成</i></a> ';

                     //审核状态显示审核按钮
                     if (row.orderStatus == 2) {
                        return d /*+ checkData*/;
                     }//审核未过状态显示重新审核
                     else if (row.orderStatus == 3) {
                        return d /*+ recheckData*/;
                     }//待支付 显示
                     else if (row.orderStatus == 4) {
                        return d + remindPay;
                     }//物料寄送 显示
                     else if (row.orderStatus == 5) {
                        return d + checkLogistics + affirmShouhuo;
                     }//物料待审核 显示 去审核
                     else if (row.orderStatus == 6) {
                        return d /*+ checkMaterial*/;
                        //物料审核失败 显示 重新审核
                     } else if (row.orderStatus == 7) {
                        return d + recheckMaterial;
                     }
                     //待发货 显示 去发货
                     else if (row.orderStatus == 8) {
                        return d + deliver;
                     }
                     //待收货 显示 已完成
                     else if (row.orderStatus == 9) {
                        return d + achieve;
                     }
                     //已完成 显示 删除
                     else if (row.orderStatus == 10) {
                        return d;
                     }
                     else {
                        return d;
                     }
                  }
               }],
            onLoadSuccess: function () {
               //如果状态是资料待审核状态
               if ($('#orderStatus').val() == 2) {

                  $('#exampleTable').bootstrapTable('hideColumn', 'payType');//隐藏支付方式
                  $('#exampleTable').bootstrapTable('hideColumn', 'shipmentTime');//隐藏配送时间
                  $('#exampleTable').bootstrapTable('hideColumn', 'shipmentType');//配送类型
                  $('#exampleTable').bootstrapTable('hideColumn', 'shipmentAmount');//快递费
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceType');//发票类型
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceStatus');//发票状态
                  $('#exampleTable').bootstrapTable('hideColumn', 'postid');//运单号
                  $('#exampleTable').bootstrapTable('hideColumn', 'orderAmount');//订单金额
                  $('#exampleTable').bootstrapTable('hideColumn', 'orderScore');//订单积分
                  $('#exampleTable').bootstrapTable('hideColumn', 'payAmount');//支付金额
                  $('#exampleTable').bootstrapTable('hideColumn', 'buyNumber');//购买数量
                  $('#exampleTable').bootstrapTable('hideColumn', 'updateTime');//更新时间
                  $('#exampleTable').bootstrapTable('hideColumn', 'address');//收货地址
                  $('#exampleTable').bootstrapTable('hideColumn', 'consignee');//收货人
                  $('#exampleTable').bootstrapTable('hideColumn', 'mobile');//收货人手机
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceNo');//公司税号
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceTitle');//公司抬头

                  $('#exampleTable').bootstrapTable('hideColumn', 'unDataResult');//资料审核未通过原因
                  $('#exampleTable').bootstrapTable('hideColumn', 'unMaterialResult');//物流审核未通过原因
               }
               //审核未过
               else if ($('#orderStatus').val() == 3) {

                  $('#exampleTable').bootstrapTable('hideColumn', 'payType');//隐藏支付方式
                  $('#exampleTable').bootstrapTable('hideColumn', 'shipmentTime');//隐藏配送时间
                  $('#exampleTable').bootstrapTable('hideColumn', 'shipmentType');//配送类型
                  $('#exampleTable').bootstrapTable('hideColumn', 'shipmentAmount');//快递费
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceType');//发票类型
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceStatus');//发票状态
                  $('#exampleTable').bootstrapTable('hideColumn', 'postid');//运单号
                  $('#exampleTable').bootstrapTable('hideColumn', 'orderAmount');//订单金额
                  $('#exampleTable').bootstrapTable('hideColumn', 'orderScore');//订单积分
                  $('#exampleTable').bootstrapTable('hideColumn', 'payAmount');//支付金额
                  $('#exampleTable').bootstrapTable('hideColumn', 'buyNumber');//购买数量
                  $('#exampleTable').bootstrapTable('hideColumn', 'updateTime');//更新时间
                  $('#exampleTable').bootstrapTable('hideColumn', 'address');//收货地址
                  $('#exampleTable').bootstrapTable('hideColumn', 'consignee');//收货人
                  $('#exampleTable').bootstrapTable('hideColumn', 'mobile');//收货人手机
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceNo');//公司税号
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceTitle');//公司抬头

                  $('#exampleTable').bootstrapTable('hideColumn', 'files');//资料附件
                  $('#exampleTable').bootstrapTable('hideColumn', 'unMaterialResult');//物流审核未通过原因

               }//待支付
               else if ($('#orderStatus').val() == 4) {

                  $('#exampleTable').bootstrapTable('hideColumn', 'postid');//运单号
                  $('#exampleTable').bootstrapTable('hideColumn', 'orderScore');//订单积分
                  $('#exampleTable').bootstrapTable('hideColumn', 'updateTime');//更新时间

                  $('#exampleTable').bootstrapTable('hideColumn', 'unDataResult');//资料审核未通过原因
                  $('#exampleTable').bootstrapTable('hideColumn', 'files');//资料附件
                  $('#exampleTable').bootstrapTable('hideColumn', 'unMaterialResult');//物流审核未通过原因

               }//物料寄送
               else if ($('#orderStatus').val() == 5) {

                  $('#exampleTable').bootstrapTable('hideColumn', 'shipmentType');//配送类型
                  $('#exampleTable').bootstrapTable('hideColumn', 'shipmentAmount');//快递费
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceType');//发票类型
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceStatus');//发票状态
                  $('#exampleTable').bootstrapTable('hideColumn', 'orderScore');//订单积分
                  $('#exampleTable').bootstrapTable('hideColumn', 'payAmount');//支付金额
                  $('#exampleTable').bootstrapTable('hideColumn', 'buyNumber');//购买数量
                  $('#exampleTable').bootstrapTable('hideColumn', 'updateTime');//更新时间
                  $('#exampleTable').bootstrapTable('hideColumn', 'address');//收货地址
                  $('#exampleTable').bootstrapTable('hideColumn', 'consignee');//收货人
                  $('#exampleTable').bootstrapTable('hideColumn', 'mobile');//收货人手机
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceNo');//公司税号
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceTitle');//公司抬头

                  $('#exampleTable').bootstrapTable('hideColumn', 'unDataResult');//资料审核未通过原因
                  $('#exampleTable').bootstrapTable('hideColumn', 'files');//资料附件
                  $('#exampleTable').bootstrapTable('hideColumn', 'unMaterialResult');//物流审核未通过原因

               }//物料待审
               else if ($('#orderStatus').val() == 6) {

                  $('#exampleTable').bootstrapTable('hideColumn', 'shipmentAmount');//快递费
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceType');//发票类型
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceStatus');//发票状态
                  $('#exampleTable').bootstrapTable('hideColumn', 'postid');//运单号
                  $('#exampleTable').bootstrapTable('hideColumn', 'orderScore');//订单积分
                  $('#exampleTable').bootstrapTable('hideColumn', 'buyNumber');//购买数量
                  $('#exampleTable').bootstrapTable('hideColumn', 'updateTime');//更新时间
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceNo');//公司税号
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceTitle');//公司抬头

                  $('#exampleTable').bootstrapTable('hideColumn', 'unDataResult');//资料审核未通过原因
                  $('#exampleTable').bootstrapTable('hideColumn', 'files');//资料附件
                  $('#exampleTable').bootstrapTable('hideColumn', 'unMaterialResult');//物流审核未通过原因

               }//物料未过
               else if ($('#orderStatus').val() == 7) {

                  $('#exampleTable').bootstrapTable('hideColumn', 'payType');//隐藏支付方式
                  $('#exampleTable').bootstrapTable('hideColumn', 'shipmentTime');//隐藏配送时间
                  $('#exampleTable').bootstrapTable('hideColumn', 'shipmentType');//配送类型
                  $('#exampleTable').bootstrapTable('hideColumn', 'shipmentAmount');//快递费
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceType');//发票类型
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceStatus');//发票状态
                  $('#exampleTable').bootstrapTable('hideColumn', 'postid');//运单号
                  $('#exampleTable').bootstrapTable('hideColumn', 'orderAmount');//订单金额
                  $('#exampleTable').bootstrapTable('hideColumn', 'orderScore');//订单积分
                  $('#exampleTable').bootstrapTable('hideColumn', 'payAmount');//支付金额
                  $('#exampleTable').bootstrapTable('hideColumn', 'buyNumber');//购买数量
                  $('#exampleTable').bootstrapTable('hideColumn', 'updateTime');//更新时间
                  $('#exampleTable').bootstrapTable('hideColumn', 'address');//收货地址
                  $('#exampleTable').bootstrapTable('hideColumn', 'consignee');//收货人
                  $('#exampleTable').bootstrapTable('hideColumn', 'mobile');//收货人手机
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceNo');//公司税号
                  $('#exampleTable').bootstrapTable('hideColumn', 'invoiceTitle');//公司抬头

                  $('#exampleTable').bootstrapTable('hideColumn', 'unDataResult');//资料审核未通过原因
                  $('#exampleTable').bootstrapTable('hideColumn', 'files');//资料附件

               }//待发货
               else if ($('#orderStatus').val() == 8) {

                  $('#exampleTable').bootstrapTable('hideColumn', 'postid');//运单号
                  $('#exampleTable').bootstrapTable('hideColumn', 'updateTime');//更新时间

                  $('#exampleTable').bootstrapTable('hideColumn', 'unDataResult');//资料审核未通过原因
                  $('#exampleTable').bootstrapTable('hideColumn', 'files');//资料附件
                  $('#exampleTable').bootstrapTable('hideColumn', 'unMaterialResult');//物流审核未通过原因

               }//待收货
               else if ($('#orderStatus').val() == 9) {


                  $('#exampleTable').bootstrapTable('hideColumn', 'unDataResult');//资料审核未通过原因
                  $('#exampleTable').bootstrapTable('hideColumn', 'files');//资料附件
                  $('#exampleTable').bootstrapTable('hideColumn', 'unMaterialResult');//物流审核未通过原因

               }//已完成
               else if ($('#orderStatus').val() == 10) {

                  $('#exampleTable').bootstrapTable('hideColumn', 'unDataResult');//资料审核未通过原因
                  $('#exampleTable').bootstrapTable('hideColumn', 'files');//资料附件
                  $('#exampleTable').bootstrapTable('hideColumn', 'unMaterialResult');//物流审核未通过原因

               }
            }
         });
}

function reLoad() {
   $('#exampleTable').bootstrapTable('refresh');
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

//填写运单号
function deliver(id) {
   layer.open({
      type: 2,
      title: '填写运单号',
      maxmin: true,
      shadeClose: false, // 点击遮罩关闭层
      area: ['800px', '520px'],
      content: prefix + '/writeNum/' + id // iframe的url
   })
   ;
}

function achieve(id) {
   layer.confirm('确定完成吗？', {
      btn: ['确定', '取消']
   }, function () {
      $.ajax({
         url: "/information/order/update",
         type: "post",
         data: {
            'id': id,
            orderStatus: 10
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

//重新审核物料
function recheckMaterial(id) {
   layer.confirm('确定重新审核物料吗？', {
      btn: ['确定', '取消']
   }, function () {
      $.ajax({
         url: "/information/order/update",
         type: "post",
         data: {
            'id': id,
            orderStatus: 6
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

//确认收到物料
function affirmShouhuo(id) {
   layer.confirm('确定收到物料了吗？', {
      btn: ['确定', '取消']
   }, function () {
      $.ajax({
         url: "/information/order/update",
         type: "post",
         data: {
            'id': id,
            orderStatus: 6
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

//提醒用户支付
function remindPay(userId) {
   layer.confirm('确定提醒用户支付吗？', {
      btn: ['确定', '取消']
   }, function () {
      $.ajax({
         url: "/information/msg/save",
         type: "post",
         data: {
            'forIds': userId,
            'name': '通知',
            'type': 0,
            'forDetails': '您有一笔订单尚未支付，请尽快支付哦'
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

//查看物料物流
function checkWuliu(postid) {
   if (postid != null && postid != "") {
      $.ajax({
         type: 'get',
         url: 'http://www.kuaidi100.com/autonumber/auto',
         dataType: "jsonp",
         data: {
            num: postid,
            key: 'rMUPmWRC4136'
         }, success: function (r) {
            var comCode = r[0].comCode;

            layer.open({
               type: 2,
               title: '物流详情',
               maxmin: true,
               shadeClose: false, // 点击遮罩关闭层
               area: ['800px', '520px'],
               content: "/information/order/getWuliu?num=" + postid + "&comCode=" + comCode // iframe的url
            });
         }
      })
   } else {
      layer.msg("运单号为空");
   }


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