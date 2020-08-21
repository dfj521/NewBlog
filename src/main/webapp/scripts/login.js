//log_in.html主处理
//前台工作：1.给DOM对象绑定JS事件 2.发送Ajax 3.处理Ajax回调 4.利用回调给DOM对象进行赋值
$(function () { //页面载入完毕
    //给登录按钮绑定单击事件处理
    $("#login").click(checkLogin);
})
//登录处理
function checkLogin() {
    //发送Ajax的三板斧
    //1.获取请求参数
    //1.1获取用户名
    var name = $("#count").val().trim();
    //1.2获取密码
    var password = $("#password").val().trim();
    //清除提示信息
    $("#count_span").html("");
    $("#password_span").html("");
    //2.检测参数格式
    var flag = true;
    if (name == "") {
        flag = false;
        $("#count_span").html("用户名为空");
    }
    if (password == "") {
        flag = false;
        $("#password_span").html("密码为空");
    }
    //3.发送Ajax
    if (flag) {
        $.ajax({
            url: base_path + "/user/login.do",
            type: "post",
            data: {"name":name, "password":password},
            dataType: "json",
            success: function (result) {
                //成功回调函数 result封装了后台传过来的json数据
                var user = result.data;
                if (result.status == 0) {
                    //写入cookie中
                    addCookie("uid", user.cn_user_id, 2);
                    addCookie("uname", user.cn_user_name, 2);
                    //成功登录后跳转到主界面
                    window.location.href = "edit.html";
                } else if (result.status == 1) { //用户名不存在
                    $("#count_span").html(result.msg);
                } else if (result.status == 2) { //密码错误
                    $("#password_span").html(result.msg);
                }
            },
            error: function () {
                alert("登录系统异常");
            }
        });
    }
}