"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.mainChatUrl = exports.mainUrlPrefix = void 0;
// server http://larryjason.com:8081/api/s
// local http://localhost:8002/api/
exports.mainUrlPrefix = "http://larryjason.com:8081/api/";
// server http://larryjason.com/chat-app/chat
// local http://localhost:3000/
exports.mainChatUrl = "http://larryjason.com/chat-app/chat/";
var showHidePass = function () {
    var passwordbox = $("#password").get(0);
    $("#showpass").get(0).onclick = function (e) {
        console.log(e.target);
        if (e.target.checked) {
            passwordbox.type = "text";
        }
        else {
            passwordbox.type = "password";
        }
    };
};
var checkData = function () {
    var listInput = $("input");
    for (var i = 0; i < listInput.length; i++) {
        var element = listInput[i];
        if (element.value.length == 0) {
            return false;
        }
    }
    return true;
};
function setCookie(name, value) {
    var expires = "";
    var date = new Date();
    date.setTime(date.getTime() + 15 * 60 * 1000);
    expires = "; expires=" + date.toUTCString();
    document.cookie = name + "=" + (value || "") + expires + "; path=/";
}
var sendData = function () {
    var submitBtn = $("#submit").get(0);
    var createAccount = $("#createaccount").get(0);
    var dataSubmit = {
        userName: $("#username").get(0).value,
        passWord: $("#password").get(0).value,
    };
    submitBtn.disabled = true;
    createAccount.onclick = function () {
        return false;
    };
    $.ajax({
        url: exports.mainUrlPrefix + "user/checklogin",
        method: "POST",
        timeout: 0,
        headers: {
            "Content-Type": "application/json",
        },
        data: JSON.stringify(dataSubmit),
        success: function (data) {
            //
            if (data.data == "login-failed") {
                alert("invalid username/password");
                //
            }
            else if (data.status != 200) {
                alert(data.status + ": " + data.error);
                //
            }
            else {
                setCookie("userName", dataSubmit.userName.valueOf());
                alert("ok");
                window.location.href = exports.mainChatUrl;
            }
            submitBtn.disabled = false;
            createAccount.onclick = undefined;
        },
    });
};
(function () {
    showHidePass();
    $("#submit").get(0).onclick = function (e) {
        if (!checkData()) {
            $("form").get(0).reportValidity();
            return false;
        }
        sendData();
        return true;
    };
})();
