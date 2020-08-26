//封装了笔记的操作
$(function () {
    //2.给笔记本li元素绑定单击事件(动态绑定)
    //$(selector).on(event,childSelector,data,function)
    $("#book_ul").on("click","li",loadNotes);
    //3.给笔记li元素绑定单击事件
    $("#note_ul").on("click","li",loadNote);
    //4.给"保存笔记"按钮绑定单击事件
    $("#save_note").click(updateNote);
    //5.关闭对话框(对所有的对话框都有效)
    $("#can").on("click", ".cancle,.close", closeAlertWindow);
    //8.弹出"创建笔记"对话框dialog模态框
    $("#add_note").click(alertAddNoteWindow);
    //9.给新建笔记的创建按钮绑定单击事件
    $("#can").on("click", "#sure_addnote", addNote);
    //12.弹出笔记菜单
    $("#note_ul").on("click", ".btn_slide_down", popNoteMenu);
    //13.点击页面隐藏笔记菜单
    $("body").click(hideNoteMenu);
    //14.绑定笔记菜单中的删除按钮
    $("#note_ul").on("click", ".btn_delete", alertDeleteNoteWindow);
    //15.给删除笔记按钮绑定单击事件
    $("#can").on("click", "#sure_deletenote", deleteNote);
    //16.绑定笔记菜单中的移动至按钮
    $("#note_ul").on("click", ".btn_move", alertMoveNoteWindow);
    //17.给移动至按钮绑定单击事件
    $("#can").on("click", "#sure_movenote", moveNote);
    //18.给分享按钮绑定单击事件
    $("#note_ul").on("click", ".btn_share", shareNote);
});
//加载用户笔记列表
function loadNotes() {
    //清除选中效果
    $("#book_ul a").removeClass("checked");
    //初始化笔记列表
    $("#note_ul li").remove();
    //1.获取请求参数
    var bookId = $(this).data("bookId");
    //添加选中效果
    $(this).find("a").addClass("checked");
    //2.参数格式校验
    //3.发送Ajax
    $.ajax({
        url: base_path + "/note/loadNotes.do",
        type: "post",
        data: {"bookId": bookId},
        dataType: "json",
        success: function (result) {
            var notes = result.data;
            for (var i = 0; i < notes.length; i++) {
                var noteId = notes[i].cn_note_id;
                var noteTitle = notes[i].cn_note_title;
                createNoteLi(noteId, noteTitle);
            }
        },
        error: function () {
            alert("查询笔记异常")
        }
    });
}
//创建笔记Id加载笔记列表
function createNoteLi(noteId, noteTitle) {
    var lis =
        '<li class="online">\n' +
        '   <a>\n' +
        '       <i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>' + noteTitle + '<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>\n' +
        '   </a>\n' +
        '   <div class="note_menu" tabindex=\'-1\'>\n' +
        '       <dl>\n' +
        '           <dt><button type="button" class="btn btn-default btn-xs btn_move" title=\'移动至...\'><i class="fa fa-random"></i></button></dt>\n' +
        '           <dt><button type="button" class="btn btn-default btn-xs btn_share" title=\'分享\'><i class="fa fa-sitemap"></i></button></dt>\n' +
        '           <dt><button type="button" class="btn btn-default btn-xs btn_delete" title=\'删除\'><i class="fa fa-times"></i></button></dt>\n' +
        '       </dl>\n' +
        '   </div>\n' +
        '</li>';
    var $li = $(lis);
    $li.data("noteId", noteId);
    $("#note_ul").append($li);
}
//加载用户笔记内容
function loadNote() {
    //清除选中效果
    $("#note_ul a").removeClass("checked");
    //添加选中效果
    $(this).find("a").addClass("checked");
    //1.获取请求参数
    var noteId = $(this).data("noteId");
    //2.参数格式校验
    //3.发送Ajax
    $.ajax({
        url: base_path + "/note/loadNote.do",
        type: "post",
        data: {"noteId": noteId},
        dataType: "json",
        success: function (result) {
            if (result.status == 0) {
                var note = result.data;
                //获取标题
                var noteTitle = note.cn_note_title;
                //获取笔记内容
                var noteBody = note.cn_note_body;
                //设置到编辑区域
                $("#input_note_title").val(noteTitle);
                um.setContent(noteBody);
                return false;
            }
        },
        error: function () {
            alert("查询笔记信息异常")
        }
    });
}
//"保存笔记"按钮的处理
function updateNote() {
    //1.获取请求参数
    var $li = $("#note_ul a.checked").parent();
    var noteId = $li.data("noteId");
    var noteTitle = $("#input_note_title").val();
    var noteBody = um.getContent();
    //清空错误信息
    $("#note_title_span").html("");
    //2.参数格式校验
    if ($li.length == 0) {
        alert("请选择要保存的笔记")
        return;
    }
    if (noteTitle == "") {
        $("#note_title_span").html("<font color='red'>标题不能为空</font>")
        return;
    }
    //3.发送Ajax
    $.ajax({
        url: base_path + "/note/update.do",
        type: "post",
        data: {"noteId": noteId, "noteTitle": noteTitle, "noteBody": noteBody},
        dataType: "json",
        success: function (result) {
            if (result.status == 0) {
                //更新列表li中的标题
                var sli = '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>' + noteTitle + '<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
                $li.find("a").html(sli);
                alert(result.msg);
            }
            if (result.status == 1) {
                alert(result.msg)
            }
        },
        error: function () {
            alert("保存笔记异常")
        }
    });
}
//创建笔记
function addNote() {
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
    //获取输入的笔记名称
    var noteTitle = $("#input_note").val().trim();
    //判断输入的名称如果为空
    if (noteTitle == "") {
        //提示输入为空
        $("#note_span").html("笔记名称为空");
        return;
    }
    //发送Ajax
    $.ajax({
        url: base_path + "/note/add.do",
        type: "post",
        data: {"bookId": bookId, "userId": userId, "noteTitle": noteTitle},
        dataType: "json",
        success: function (result) {
            closeAlertWindow();
            if (result.status == 0) {
                var noteId = result.data;
                createNoteLi(noteId, noteTitle);
                alert(result.msg)
            } else {
                alert(result.msg)
            }
        },
        error: function () {
            alert("创建笔记异常")
        }
    });

}
//显示笔记菜单
function popNoteMenu() {
    //隐藏所有笔记菜单
    $("#note_ul div").hide();
    //获取笔记菜单
    var $menus = $(this).parent().next();
    $menus.slideDown(1000);
    //设置点击下拉设置对应的笔记为选中状态
    $("#note_ul a").removeClass("checked");
    $(this).parent().addClass("checked");
    return false;
}
//隐藏笔记菜单
function hideNoteMenu() {
    $("#note_ul div").hide();
}
//删除笔记
function deleteNote() {
    var userId = getCookie("uid");
    if (userId == null) {
        window.location.href = "log_in.html";
        return;
    }
    var $li = $("#note_ul a.checked").parent();
    var noteId = $li.data("noteId");
    if (noteId == null) {
        alert("请选择要删除的笔记");
        return;
    }
    $.ajax({
        url: base_path + "/note/delete.do",
        type: "post",
        data: {"noteId": noteId},
        dataType: "json",
        success: function (result) {
            if (result.status == 0) {
                closeAlertWindow();
                $li.remove();
                $("#input_note_title").val("");
                um.setContent("");
                alert(result.msg)
            } else {
                alert(result.msg)
            }
        },
        error: function () {
            alert("删除笔记异常")
        }
    });
}
//移动笔记
function moveNote() {
    var userId = getCookie("uid");
    if (userId == null) {
        window.location.href = "log_in.html";
        return;
    }
    var bookId = $("#moveSelect").val();
    if (bookId == "" || bookId == "none") {
        $("#moveSelect_span").html("请选择笔记本");
        return;
    }
    var $li = $("#note_ul a.checked").parent();
    var noteId = $li.data("noteId");
    if (noteId == null) {
        $("#moveSelect_span").html("请选择笔记");
        return;
    }
    $.ajax({
        url: base_path + "/note/move.do",
        type: "post",
        data: {"bookId": bookId, "noteId": noteId},
        dataType: "json",
        success: function (result) {
            if (result.status == 0) {
                closeAlertWindow();
                $li.remove();
                $("#input_note_title").val("");
                um.setContent("");
                alert(result.msg);
            }
        },
        error: function () {
            alert("移动笔记异常");
        }
    });
}
//分享笔记
function shareNote() {
    var userId = getCookie("uid");
    if (userId == null) {
        window.location.href = "log_in.html";
        return;
    }
    var $li = $("#note_ul a.checked").parent();
    var noteId = $li.data("noteId");
    if (noteId == null) {
        $("#moveSelect_span").html("请选择笔记");
        return;
    }
    $.ajax({
        url: base_path + "/share/add.do",
        type: "post",
        data: {"noteId": noteId},
        dataType: "json",
        success: function (result) {
            if (result == 0) {
                closeAlertWindow();
                alert(result.msg);
            } else {
                alert(result.msg);
            }
        },
        error: function () {
            alert("分享笔记异常");
        }
    });
}
