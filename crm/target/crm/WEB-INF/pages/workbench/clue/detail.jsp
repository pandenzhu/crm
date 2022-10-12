<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">

	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

	<script type="text/javascript">

		//默认情况下取消和保存按钮是隐藏的
		var cancelAndSaveBtnDefault = true;

		$(function(){
			$("#clueRemark").focus(function(){
				if(cancelAndSaveBtnDefault){
					//设置remarkDiv的高度为130px
					$("#remarkDiv").css("height","130px");
					//显示
					$("#cancelAndSaveBtn").show("2000");
					cancelAndSaveBtnDefault = false;
				}
			});

			$("#cancelBtn").click(function(){
				//显示
				$("#cancelAndSaveBtn").hide();
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","90px");
				cancelAndSaveBtnDefault = true;
			});

			/*$(".remarkDiv").mouseover(function(){
                $(this).children("div").children("div").show();
            });

            $(".remarkDiv").mouseout(function(){
                $(this).children("div").children("div").hide();
            });

            $(".myHref").mouseover(function(){
                $(this).children("span").css("color","red");
            });

            $(".myHref").mouseout(function(){
                $(this).children("span").css("color","#E6E6E6");
            });*/



			//给"关联市场活动"按钮添加单击事件
			$("#bundActivityBtn").click(function () {
				//清空搜索框
				$("#searchActivityName").val("");
				//清空搜索列表
				$("#tBody").html("");
				//显示线索关联市场活动的模态窗口
				$("#bundModal").modal("show");
			});

			$("#remarkDivList").on("mouseover",".remarkDiv",function () {
				$(this).children("div").children("div").show();
			});

			$("#remarkDivList").on("mouseout",".remarkDiv",function () {
				$(this).children("div").children("div").hide();
			});

			$("#remarkDivList").on("mouseover",".myHref",function () {
				$(this).children("span").css("color","red");
			});

			$("#remarkDivList").on("mouseout",".myHref",function () {
				$(this).children("span").css("color","#E6E6E6");
			});
			//加载线索备注列表
			showClueRemarkList();

			//保存线索备注
			$("#saveCreateClueRemarkBtn").click(function () {

				//获取备注内容和线索标识
				var noteContent = $("#clueRemark").val().trim();

				//判断
				if ("" == clueRemark) {
					alert("备注内容不能为空");
					return;
				}

				//获取线索标识
				var clueId = $("#clueId").val();

				//发送保存ajax请求
				$.ajax({
					url:"workbench/clue/saveCreateClueRemark.do",
					type:"post",
					data:{
						clueId:clueId,
						noteContent:noteContent
					},
					success:function (data) {
						if (data.code == 1) {
							showClueRemarkList();
						} else {
							alert("保存线索备注失败，请重试...");
						}

					}
				});


			});

			//给更新按钮添加单击事件
			$("#saveEditClueRemarkBtn").click(function () {
				//获取线索备注id
				var id = $("#remarkId").val();
				//获取线索备注内容
				var noteContent = $("#edit-noteContent").val().trim();

				//判断备注内容是否为空
				if ("" == noteContent) {
					alert("备注内容不能为空");
					return;
				}

				//发送更新请求
				$.ajax({
					url:"workbench/clue/saveEditClueRemark.do",
					type:"post",
					data:{
						id:id,
						noteContent:noteContent
					},
					success:function (data) {
						if (data.code == 1) {
							//关闭模态窗口
							$("#editRemarkModal").modal("hide");
							showClueRemarkList();
						} else {
							alert(data.message);
						}
					}
				});

			});

			//加载线索已关联的市场活动列表数据
			queryBindClueActivityList();



			//给“关联市场活动”按钮添加单击事件
			$("#bindActivityBtn").click(function () {
				//打开关联市场活动的模态窗口
				$("#bindModal").modal("show");

				//加载线索未关联的市场活动列表数据
				queryUnBindClueActivityRelationList();
			});

			//给搜索市场活动名称输入框添加keyup事件
			$("#searchActivityName").keyup(function () {

				//获取到搜索的内容
				var activityName = $("#searchActivityName").val();

				queryUnBindClueActivityRelationList(activityName);
			});

			//给关联按钮添加单击事件
			$("#saveBindActivityBtn").click(function () {
				//获取选中的对象
				var checkeds = $("#tBody input[type='checkbox']:checked");

				var clueId = $("#clueId").val();

				//判断选中的数量
				if (checkeds.size() == 0) {
					alert("请选中要关联的市场活动");
					return;
				}
				var ids = "";
				//循环遍历获取选中数据的市场活动标识
				$.each(checkeds,function (index,obj) {
					ids+="id="+$(obj).val()+"&";	//id=xx&id=xx
				});

				// alert(ids);
				// alert(ids.substring(0,ids.length-1));
				//获取到要关联的市场活动id
				ids+="clueId="+clueId;
				// alert(ids);

				//发送ajax请求，请求将所有的市场活动添加关联关系
				$.ajax({
					url:"workbench/clue/saveBindClueActivity.do",
					type:"get",
					data:ids,
					success:function (data) {
						if (data.code == 1) {
							alert("您成功关联了"+data.data+"个市场活动");
							//关闭模态窗口
							$("#bindModal").modal("hide");
							//刷新数据
							queryBindClueActivityList();
						} else {
							alert(data.message);
						}

					}
				});
			});

			//给转换按钮添加单击事件
			$("#convertBtn1").click(function () {
				//跳转到线索转换的页面
				window.location.href = "workbench/clue/clueConvert.do?clueId=${clue.id}";
			});

		});

		function deleteClueRemark(id) {

			if (confirm("您确定要删除吗？")) {
				$.ajax({
					url:"workbench/clue/deleteClueRemarkById.do",
					type:"post",
					data:{
						id:id
					},
					success:function (data) {
						if (data.code == 1) {
							showClueRemarkList();
						} else {
							alert(data.message);
						}

					}
				});
			}

		}

		//编辑备注内容
		function editClueRemark(id,noteContent) {

			//打开编辑的模态窗口
			$("#editRemarkModal").modal("show");

			//给备注标签添加内容
			$("#edit-noteContent").val(noteContent);

			$("#remarkId").val(id);
		}

		//加载线索备注列表
		function showClueRemarkList() {
			//获取线索标识 李四先生-动力节点
			var clueId = $("#clueId").val();
			var str = "${clue.fullname}${clue.appellation}-${clue.company}";
			<%--var appellation = "${clue.appellation}";--%>
			<%--var company = "${clue.company}";--%>



			//发送ajax请求
			$.ajax({
				url:"workbench/clue/queryClueRemarkListByClueId.do",
				type:"get",
				data:{
					clueId:clueId
				},
				success:function (data) {

					var htmlStr = "";

					$.each(data,function (index,obj) {
						htmlStr+="<div class=\"remarkDiv\" style=\"height: 60px;\">";
						htmlStr+="<img title=\""+obj.createBy+"\" src=\"image/user-thumbnail.png\" style=\"width: 30px; height:30px;\">";
						htmlStr+="<div style=\"position: relative; top: -40px; left: 40px;\" >";
						htmlStr+="<h5>"+obj.noteContent+"</h5>";
						htmlStr+="<font color=\"gray\">线索</font> <font color=\"gray\">-</font> <b>"+str+"</b> <small style=\"color: gray;\"> "+obj.createTime+" 由"+obj.createBy+"</small>";
						htmlStr+="<div style=\"position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;\">";
						htmlStr+="<a class=\"myHref\" onclick=\"editClueRemark('"+obj.id+"','"+obj.noteContent+"')\" href=\"javascript:void(0);\"><span class=\"glyphicon glyphicon-edit\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>";
						htmlStr+="&nbsp;&nbsp;&nbsp;&nbsp;";
						htmlStr+="<a class=\"myHref\" onclick=\"deleteClueRemark('"+obj.id+"')\" href=\"javascript:void(0);\"><span class=\"glyphicon glyphicon-remove\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>";
						htmlStr+="</div>";
						htmlStr+="</div>";
						htmlStr+="</div>";
					});

					// $("#showClueRemark").html(htmlStr);
					$("#showClueRemark").html(htmlStr);
				}
			});

		}

		//加载线索已关联的市场活动列表
		function queryBindClueActivityList() {
			//获取线索id
			var clueId = $("#clueId").val();

			//发送ajax请求
			$.ajax({
				url:"workbench/clue/queryBindClueActivityListByClueId.do",
				type:"get",
				data:{
					clueId:clueId
				},
				success:function (data) {
					var htmlStr = "";

					$.each(data,function (index,obj) {
						htmlStr+="<tr>";
						htmlStr+="<td>"+obj.name+"</td>";
						htmlStr+="<td>"+obj.startDate+"</td>";
						htmlStr+="<td>"+obj.endDate+"</td>";
						htmlStr+="<td>"+obj.owner+"</td>";
						htmlStr+="<td><a href=\"javascript:void(0);\" onclick=\"unBindClueActivityRelation('"+clueId+"','"+obj.id+"')\"  style=\"text-decoration: none;\"><span class=\"glyphicon glyphicon-remove\"></span>解除关联</a></td>";
						htmlStr+="</tr>";
					});

					$("#relationTBody").html(htmlStr);

				}
			});
		}

		//解除线索与市场活动的关系
		function unBindClueActivityRelation(clueId,activityId) {

			if (confirm("您真的要解除与当前市场活动的关系吗？")) {
				$.ajax({
					url:"workbench/clue/unBindClueActivityRelation.do",
					type:"post",
					data:{
						clueId:clueId,
						activityId:activityId
					},
					success:function (data) {
						if (data.code == 1) {
							queryBindClueActivityList();
						} else {
							alert(data.message);
						}
					}
				});
			}

		}

		//查询与当前线索未关联的市场活动列表
		function queryUnBindClueActivityRelationList(activityName) {

			//获取线索标识
			var clueId = $("#clueId").val();

			//查询的市场活动名称
			// var activityName = $("#searchActivityName").val().trim();

			//发送ajax请求，获取未关联市场活动列表
			$.ajax({
				url:"workbench/clue/queryUnBindClueActivityRelationList.do",
				type:"get",
				data:{
					clueId:clueId,
					activityName:activityName
				},
				success:function (data) {
					var htmlStr = "";

					$.each(data,function (index,obj) {
						htmlStr+="<tr>";
						htmlStr+="<td><input type=\"checkbox\" value=\""+obj.id+"\"/></td>";
						htmlStr+="<td>"+obj.name+"</td>";
						htmlStr+="<td>"+obj.startDate+"</td>";
						htmlStr+="<td>"+obj.endDate+"</td>";
						htmlStr+="<td>"+obj.owner+"</td>";
						htmlStr+="</tr>";
					});

					$("#tBody").html(htmlStr);
				}
			});

		}

	</script>

</head>
<body>

<!-- 修改市场活动备注的模态窗口 -->
<div class="modal fade" id="editRemarkModal" role="dialog">
	<%-- 备注的id --%>
	<input type="hidden" id="remarkId">
	<div class="modal-dialog" role="document" style="width: 40%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">修改备注</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form">
					<input type="hidden" id="edit-id">
					<div class="form-group">
						<label for="edit-noteContent" class="col-sm-2 control-label">内容</label>
						<div class="col-sm-10" style="width: 81%;">
							<textarea class="form-control" rows="3" id="edit-noteContent"></textarea>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="saveEditClueRemarkBtn">更新</button>
			</div>
		</div>
	</div>
</div>

<!-- 关联市场活动的模态窗口 -->
<div class="modal fade" id="bindModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 80%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">关联市场活动</h4>
			</div>
			<div class="modal-body">
				<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
					<form class="form-inline" role="form">
						<div class="form-group has-feedback">
							<input id="searchActivityName" type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
							<span class="glyphicon glyphicon-search form-control-feedback"></span>
						</div>
					</form>
				</div>
				<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
					<thead>
					<tr style="color: #B3B3B3;">
						<td><input type="checkbox"/></td>
						<td>名称</td>
						<td>开始日期</td>
						<td>结束日期</td>
						<td>所有者</td>
						<td></td>
					</tr>
					</thead>
					<tbody id="tBody">

					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button id="saveBindActivityBtn" type="button" class="btn btn-primary">关联</button>
			</div>
		</div>
	</div>
</div>


<!-- 返回按钮 -->
<div style="position: relative; top: 35px; left: 10px;">
	<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
</div>

<!-- 大标题 -->
<div style="position: relative; left: 40px; top: -30px;">
	<div class="page-header">
		<h3>${clue.fullname}${clue.appellation} <small>${clue.company}</small></h3>
	</div>
	<div style="position: relative; height: 50px; width: 500px;  top: -72px; left: 700px;">
		<button id="convertBtn1" type="button" class="btn btn-default"><span class="glyphicon glyphicon-retweet"></span> 转换</button>

	</div>
</div>

<br/>
<br/>
<br/>

<!-- 详细信息 -->
<div style="position: relative; top: -70px;">
	<input type="hidden" id="clueId" value="${clue.id}"/>
	<div style="position: relative; left: 40px; height: 30px;">
		<div style="width: 300px; color: gray;">名称</div>
		<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.fullname}${clue.appellation}</b></div>
		<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">所有者</div>
		<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.owner}</b></div>
		<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
		<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
	</div>
	<div style="position: relative; left: 40px; height: 30px; top: 10px;">
		<div style="width: 300px; color: gray;">公司</div>
		<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.company}</b></div>
		<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">职位</div>
		<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.job}</b></div>
		<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
		<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
	</div>
	<div style="position: relative; left: 40px; height: 30px; top: 20px;">
		<div style="width: 300px; color: gray;">邮箱</div>
		<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.email}</b></div>
		<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">公司座机</div>
		<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.phone}</b></div>
		<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
		<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
	</div>
	<div style="position: relative; left: 40px; height: 30px; top: 30px;">
		<div style="width: 300px; color: gray;">公司网站</div>
		<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.website}</b></div>
		<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">手机</div>
		<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.mphone}</b></div>
		<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
		<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
	</div>
	<div style="position: relative; left: 40px; height: 30px; top: 40px;">
		<div style="width: 300px; color: gray;">线索状态</div>
		<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.state}</b></div>
		<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">线索来源</div>
		<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${clue.source}</b></div>
		<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
		<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
	</div>
	<div style="position: relative; left: 40px; height: 30px; top: 50px;">
		<div style="width: 300px; color: gray;">创建者</div>
		<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${clue.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${clue.createTime}</small></div>
		<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
	</div>
	<div style="position: relative; left: 40px; height: 30px; top: 60px;">
		<div style="width: 300px; color: gray;">修改者</div>
		<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${clue.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${clue.editTime}</small></div>
		<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
	</div>
	<div style="position: relative; left: 40px; height: 30px; top: 70px;">
		<div style="width: 300px; color: gray;">描述</div>
		<div style="width: 630px;position: relative; left: 200px; top: -20px;">
			<b>
				${clue.description}
			</b>
		</div>
		<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
	</div>
	<div style="position: relative; left: 40px; height: 30px; top: 80px;">
		<div style="width: 300px; color: gray;">联系纪要</div>
		<div style="width: 630px;position: relative; left: 200px; top: -20px;">
			<b>
				${clue.contactSummary}
			</b>
		</div>
		<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
	</div>
	<div style="position: relative; left: 40px; height: 30px; top: 90px;">
		<div style="width: 300px; color: gray;">下次联系时间</div>
		<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${clue.nextContactTime}</b></div>
		<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px; "></div>
	</div>
	<div style="position: relative; left: 40px; height: 30px; top: 100px;">
		<div style="width: 300px; color: gray;">详细地址</div>
		<div style="width: 630px;position: relative; left: 200px; top: -20px;">
			<b>
				${clue.address}
			</b>
		</div>
		<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
	</div>
</div>

<div id="remarkDivList" style="position: relative; top: 30px; left: 40px;">
	<div class="page-header">
		<h4>备注</h4>
	</div>

	<div id="showClueRemark">

	</div>


	<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
		<form role="form" style="position: relative;top: 10px; left: 10px;">
			<textarea id="clueRemark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
			<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
				<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
				<button id="saveCreateClueRemarkBtn" type="button" class="btn btn-primary">保存</button>
			</p>
		</form>
	</div>
</div>

<!-- 市场活动 -->
<div>
	<div style="position: relative; top: 60px; left: 40px;">
		<div class="page-header">
			<h4>市场活动</h4>
		</div>
		<div style="position: relative;top: 0px;">
			<table class="table table-hover" style="width: 900px;">
				<thead>
				<tr style="color: #B3B3B3;">
					<td>名称</td>
					<td>开始日期</td>
					<td>结束日期</td>
					<td>所有者</td>
					<td>操作</td>
				</tr>
				</thead>
				<tbody id="relationTBody">
				<%--<tr>
                    <td>发传单</td>
                    <td>2020-10-10</td>
                    <td>2020-10-20</td>
                    <td>zhangsan</td>
                    <td><a href="javascript:void(0);"  style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>解除关联</a></td>
                </tr>
                <tr>
                    <td>发传单</td>
                    <td>2020-10-10</td>
                    <td>2020-10-20</td>
                    <td>zhangsan</td>
                    <td><a href="javascript:void(0);"  style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>解除关联</a></td>
                </tr>--%>
				</tbody>
			</table>
		</div>

		<div>
			<a id="bindActivityBtn" href="javascript:void(0);" style="text-decoration: none;"><span class="glyphicon glyphicon-plus"></span>关联市场活动</a>
		</div>
	</div>
</div>


<div style="height: 200px;"></div>
</body>
</html>