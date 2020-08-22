//封装了笔记的操作
$(function () {
    //2.给笔记本li元素绑定单机事件(动态绑定)
    //$(selector).on(event,childSelector,data,function)
    $("#book_ul").on("click","li",loadNotes);
});
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
function createNoteLi(noteId, noteTitle) {
    var lis =
        '<li class="online">\n' +
        '   <a  class=\'checked\'>\n' +
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