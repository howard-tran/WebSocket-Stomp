"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.mainUrlPrefix = void 0;
// server http://larryjason.com:8081/api/
// local http://localhost:8002/api/
exports.mainUrlPrefix = "http://larryjason.com:8081/api/";
// server http://larryjason.com/chat-app/login/
// local ../index.html
var signInUrl = "http://larryjason.com/chat-app/login/";
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
var sendData = function () {
    var submitBtn = $("#submit").get(0);
    var backtologin = $("#backtologin").get(0);
    var dataSubmit = {
        userName: $("#username").get(0).value,
        passWord: $("#password").get(0).value,
    };
    submitBtn.disabled = true;
    backtologin.onclick = function () {
        return false;
    };
    $.ajax({
        url: exports.mainUrlPrefix + "/user/add",
        method: "POST",
        timeout: 0,
        headers: {
            "Content-Type": "application/json",
        },
        data: JSON.stringify(dataSubmit),
        success: function (data) {
            //
            if (data.status != 200) {
                alert(data.status + ": " + data.error);
                //
            }
            else {
                alert("ok");
                window.location.replace(signInUrl);
            }
            submitBtn.disabled = false;
            backtologin.onclick = undefined;
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
