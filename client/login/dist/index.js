!(function (e) {
  var t = {};
  function r(n) {
    if (t[n]) return t[n].exports;
    var o = (t[n] = { i: n, l: !1, exports: {} });
    return e[n].call(o.exports, o, o.exports, r), (o.l = !0), o.exports;
  }
  (r.m = e),
    (r.c = t),
    (r.d = function (e, t, n) {
      r.o(e, t) || Object.defineProperty(e, t, { enumerable: !0, get: n });
    }),
    (r.r = function (e) {
      "undefined" != typeof Symbol &&
        Symbol.toStringTag &&
        Object.defineProperty(e, Symbol.toStringTag, { value: "Module" }),
        Object.defineProperty(e, "__esModule", { value: !0 });
    }),
    (r.t = function (e, t) {
      if ((1 & t && (e = r(e)), 8 & t)) return e;
      if (4 & t && "object" == typeof e && e && e.__esModule) return e;
      var n = Object.create(null);
      if (
        (r.r(n),
        Object.defineProperty(n, "default", { enumerable: !0, value: e }),
        2 & t && "string" != typeof e)
      )
        for (var o in e)
          r.d(
            n,
            o,
            function (t) {
              return e[t];
            }.bind(null, o)
          );
      return n;
    }),
    (r.n = function (e) {
      var t =
        e && e.__esModule
          ? function () {
              return e.default;
            }
          : function () {
              return e;
            };
      return r.d(t, "a", t), t;
    }),
    (r.o = function (e, t) {
      return Object.prototype.hasOwnProperty.call(e, t);
    }),
    (r.p = ""),
    r((r.s = 0));
})([
  function (e, t, r) {
    "use strict";
    Object.defineProperty(t, "__esModule", { value: !0 }),
      (t.mainChatUrl = t.mainUrlPrefix = void 0),
      (t.mainUrlPrefix = "http://larryjason.com:8081/api/"),
      (t.mainChatUrl = "http://larryjason.com/chat-app/chat/");
    var n,
      o = function () {
        var e = $("#submit").get(0),
          r = $("#createaccount").get(0),
          n = { userName: $("#username").get(0).value, passWord: $("#password").get(0).value };
        (e.disabled = !0),
          (r.onclick = function () {
            return !1;
          }),
          $.ajax({
            url: t.mainUrlPrefix + "user/checklogin",
            method: "POST",
            timeout: 0,
            headers: { "Content-Type": "application/json" },
            data: JSON.stringify(n),
            success: function (o) {
              if ("login-failed" == o.data) alert("invalid username/password");
              else if (200 != o.status) alert(o.status + ": " + o.error);
              else {
                (i = "userName"),
                  (u = n.userName.valueOf()),
                  (c = new Date()).setTime(c.getTime() + 9e5),
                  (l = "; expires=" + c.toUTCString()),
                  (document.cookie = i + "=" + (u || "") + l + "; path=/"),
                  alert("ok");
                var a = setInterval(function () {
                  (window.location.href = t.mainChatUrl), clearInterval(a);
                }, 800);
              }
              var i, u, l, c;
              (e.disabled = !1), (r.onclick = void 0);
            },
          });
      };
    (n = $("#password").get(0)),
      ($("#showpass").get(0).onclick = function (e) {
        console.log(e.target), e.target.checked ? (n.type = "text") : (n.type = "password");
      }),
      ($("#submit").get(0).onclick = function (e) {
        return (function () {
          for (var e = $("input"), t = 0; t < e.length; t++) if (0 == e[t].value.length) return !1;
          return !0;
        })()
          ? (o(), !0)
          : ($("form").get(0).reportValidity(), !1);
      });
  },
]);
