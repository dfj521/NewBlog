//封装了笔记本的操作
$(function () {
    //1.加载用户笔记本
    loadUserBooks();
    //6.弹出"创建笔记本"对话框dialog模态框
    $("#add_notebook").click(alertAddBookWindow);
    //7.给新建笔记本的创建按钮绑定单击事件
    $("#can").on("click", "#sure_addbook", addBook);
    //10.给笔记本列表添加双击事件
    $("#book_ul").on("dblclick", "li", alertRenameBookWindow);
    //11.给新建笔记按钮添加单击事件
    $("#can").on("click", "#sure_renameBook", renameBook);
});
//加载用户笔记本列表
function loadUserBooks() {
    //1.获取请求参数
    var userId = getCookie("uid");
    //2.参数格式校验
    if (userId == null) {
        window.location.href = "log_in.html";
    } else {
        //3.发送Ajax
        $.ajax({
            url: base_path + "/book/loadbooks.do",
            type: "post",
            data: {"userId": userId},
            dataType: "json",
            success: function (result) {
                //回调处理
                //获取返回的笔记本集合
                var books = result.data;
                //循环生成列表元素
                for (var i = 0; i < books.length; i++) {
                    //获取笔记本id
                    var bookId = books[i].cn_notebook_id;
                    //获取笔记名称
                    var bookName = books[i].cn_notebook_name;
                    //创建笔记本列表li
                    createBookLi(bookId, bookName);
                }
            },
            error: function () {
                alert("查询笔记本异常")
            }
        });
    }
}
//创建笔记本li元素
function createBookLi(bookId, bookName) {
    /*<li class="online">
        <a  class='checked'>
        <i class="fa fa-book" title="online" rel="tooltip-bottom">
    </i> 默认笔记本</a></li>*/
    //构建列表li元素
    var sli =
        '<li class="online">' +
        '    <a>' +
        '        <i class="fa fa-book" title="online" rel="tooltip-bottom"></i>' +
                 bookName +
        '    </a>' +
        '</li>';
    //将JS对象转换成JQuery对象
    var $li = $(sli);
    //将bookId绑定到li元素上
    $li.data("bookId", bookId);
    //将li元素添加到ul列表中
    $("#book_ul").append($li);
}
//创建笔记本
function addBook() {
    var userId = getCookie("uid");
    if (userId == null) {
        window.location.href = "log_in.html";
        return;
    }
    var bookName = $("#input_notebook").val().trim();
    if (bookName == "") {
        $("#notebook_span").html("笔记本名字不能为空");
        return;
    }
    $.ajax({
        url: base_path + "/book/add.do",
        type: "post",
        data: {"bookName": bookName, "userId": userId},
        dataType: "json",
        success: function (result) {
            if (result.status == 0) {
                //关闭新建笔记本对话框
                closeAlertWindow();
                var bookId = result.data;
                createBookLi(bookId, bookName);
                alert(result.msg);
            } else {
                alert(result.msg)
            }
        },
        error: function () {
            alert("创建笔记本异常")
        }
    });
}
//笔记本重命名
function renameBook() {
    //获取用户id
    var userId = getCookie("uid");
    //判断是否登录过时
    if (userId == null) {
        window.location.href = "log_in.html";
        return;
    }
    //获取笔记本id
    var $li = $("#book_ul a.checked").parent();
    var bookId = $li.data("bookId");
    //判断是否选择了笔记本
    if (bookId == null) {
        alert("请选择笔记本");
        return;
    }
    //获取输入的笔记本名称
    var bookName = $("#input_notebook_rename").val().trim();
    //判断输入的名称如果为空
    if (bookName == "") {
        //提示输入为空
        $("#rename_span").html("笔记本名称为空");
        return;
    }
    //发送Ajax
    $.ajax({
        url: base_path + "/book/update.do",
        type: "post",
        data: {"bookId": bookId, "bookName": bookName},
        dataType: "json",
        success: function (result) {
            if (result.status == 0) {
                var li = '<i class="fa fa-book" title="online" rel="tooltip-bottom"></i>' + bookName;
                $li.find("a").html(li);
                alert(result.msg);
            } else {
                alert(result.msg);
            }
        },
        error: function () {
            alert("笔记本重命名异常")
        }
    });
}