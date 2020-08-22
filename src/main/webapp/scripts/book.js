//封装了笔记本的操作
$(function () {
    //1.加载用户笔记本
    loadUserBooks();
    //2.给笔记本li元素绑定单机事件
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