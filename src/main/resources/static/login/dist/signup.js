"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    Object.defineProperty(o, k2, { enumerable: true, get: function() { return m[k]; } });
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (k !== "default" && Object.prototype.hasOwnProperty.call(mod, k)) __createBinding(result, mod, k);
    __setModuleDefault(result, mod);
    return result;
};
Object.defineProperty(exports, "__esModule", { value: true });
var foo = __importStar(require("./index"));
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
        url: foo.mainUrlPrefix + "/api/user/checklogin",
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
                window.location.replace("./index.html");
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
