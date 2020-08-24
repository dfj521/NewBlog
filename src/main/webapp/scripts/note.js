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
    $("#can").on("click", ".cancle,.close", closeAlertWindow)
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