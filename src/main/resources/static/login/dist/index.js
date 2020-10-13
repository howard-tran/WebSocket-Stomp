"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.mainUrlPrefix = exports.localUrlPrefix = exports.deployUrlPrefix = void 0;
var deployUrlPrefix = "http://larryjason.com:8002";
exports.deployUrlPrefix = deployUrlPrefix;
var localUrlPrefix = "http://localhost:8002";
exports.localUrlPrefix = localUrlPrefix;
var mainUrlPrefix = localUrlPrefix;
exports.mainUrlPrefix = mainUrlPrefix;
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
        url: "http://localhost:8002/api/user/checklogin",
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
                alert("ok");
                window.location.replace("../chat/build/index.html");
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
