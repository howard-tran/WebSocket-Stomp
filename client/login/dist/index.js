"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var mainUrlPrefix = "http://larryjason.com:8081/api/";
var mainChatUrl = "http://larryjason.com/chat-app/chat";
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
var setCookie = function (name, value) {
    var expires = "";
    var date = new Date();
    date.setTime(date.getTime() + (5 * 60 * 1000));
    expires = "; expires=" + date.toUTCString();
    document.cookie = name + "=" + (value || "") + expires + "; path=/";
};
var getCookie = function (name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(";");
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == " ")
            c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0) {
            return c.substring(nameEQ.length, c.length);
        }
    }
    return null;
};
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
        url: mainUrlPrefix + "user/checklogin",
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
                setCookie("userName", dataSubmit.userName);
                console.log(getCookie("userName"));
                alert("ok");
                window.location.href = mainChatUrl;
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
