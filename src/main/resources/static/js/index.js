
/**保存按钮**/
$("#saveBtn").click(function () {
    var name = $("#inputName").val();
    if (null == name || "" == name) {
        alert("请输入用户名！");
        $("#inputName").focus();
        return;
    }
    var id = $("#guestId").attr("value");
    if (id == null || "" == id) {//新增
        addGuest(name);
    } else {//修改
        updateGuest(id, name);
    }
    $(".close").click();
});

$(function () {
    list();

    $("#addBtn").click(function () {
        $("#guestId").attr("value", "");
        $("#inputName").val($(this).attr(""));
    });

    $("#searchBtn").click(function () {
        list();
    });
});

function updateGuest(id, name) {
    $.ajax({
        url: "/guestUpdate",
        type: "post",
        data: {
            "id": id,
            "name": name
        },
        datatype: "json",
        success: function (data) {
            if (data != null) {
                alert("修改成功！");
                list();
            } else {
                alert("修改失败!");
            }
        },
        error: function (data) {
            if (data.status == 500) {
                var errorText  = JSON.parse(data.responseText);
                alert(errorText.message);
            } else {
                alert("网络异常，请稍后操作！");
            }
        }
    });
}

/**新增来宾**/
function addGuest(name) {
    $.ajax({
        url: "/guests",
        type: "post",
        data: {
            "name": name
        },
        datatype: "json",
        success: function (data) {
            if (data != null) {
                alert("添加成功！");
                list();
            } else {
                alert("添加失败!");
            }
        },
        error: function (data) {
            if (data.status == 500) {
                var errorText  = JSON.parse(data.responseText);
                alert(errorText.message);
            } else {
                alert("网络异常，请稍后操作！");
            }
        }
    });
}

function deleteGuest(id) {
    $.ajax({
        url: "/guestDelete",
        type: "post",
        data: {
            "id": id
        },
        datatype: "json",
        success: function (data) {
            if (data == true) {
                alert("删除成功！");
                list();
            } else {
                alert("删除失败!");
            }
        },
        error: function (data) {
            if (data.status == 500) {
                var errorText  = JSON.parse(data.responseText);
                alert(errorText.message);
            } else {
                alert("网络异常，请稍后操作！");
            }
        }
    });
}

function list() {
    $.ajax({
        url: "/guests",
        type: "get",
        datatype: "json",
        success: function (data) {
            if (data != null && data.length > 0) {
                var html = "";
                for (var index = 0; index < data.length; index++) {
                    var guest = data[index];
                    html += "<tr value='"+guest.id+"'>";
                    html += " <td>" + (index + 1) + "</td>";
                    html += " <td>" + guest.name + "</td>";
                    if (guest.status == "1") {
                        var signTime = new Date();
                        signTime.setTime(guest.signTime);
                        html += " <td class='info'>已签到</td>";
                        html += " <td>" + signTime.getFullYear() + "-" + (signTime.getMonth() + 1) + "-" + signTime.getDate() + " " + signTime.getHours() + ":" + signTime.getMinutes() + ":" + signTime.getSeconds() + "</td>";
                    } else {
                        html += " <td>未签到</td>";
                        html += " <td></td>";
                    }

                    html += " <td><div id='img_"+guest.id+"'></div></td>";
                    html += " <td><div class='btn-group'><button type='button' class='btn btn-warning' name='updateBtn' value='" + guest.id + "' valueName='" + guest.name + "' data-toggle='modal' data-target='#myModal'>修改</button>" +
                        "<button type='button' class='btn btn-danger' name='deleteBtn' value='" + guest.id + "'>删除</button></div></td>";
                    html += "</tr>";
                }
                $("#listDiv").html(html);

                $("tbody>tr").each(function(){
                    var id = $(this).attr("value");
                    var content ="www.xxx.com?id="+id;//跳转地址
                    jQuery('#img_'+id).qrcode(utf16to8(content));
                });

                $("button[name=updateBtn]").click(function () {
                    var id = $(this).attr("value");
                    var name = $(this).attr("valueName");
                    if (null == id || "" == id) {
                        alert("数据异常，请刷新页面！");
                        return;
                    }
                    $("#guestId").attr("value", id);
                    $("#inputName").val(name);
                });

                $("button[name=deleteBtn]").click(function () {
                    var id = $(this).attr("value");
                    if (null == id || "" == id) {
                        alert("数据异常，请刷新页面！");
                        return;
                    }
                    if (confirm("确认删除吗？")) {
                        deleteGuest(id);
                    }
                });

            } else {
                $("#listDiv").html("");
            }
        },
        error: function () {
            alert("网络异常，请稍后操作！");
        }
    });

    //二维码
    function utf16to8(str) {
        var out, i, len, c;
        out = "";
        len = str.length;
        for (i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                out += str.charAt(i);
            } else if (c > 0x07FF) {
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            } else {
                out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            }
        }
        return out;
    }
}