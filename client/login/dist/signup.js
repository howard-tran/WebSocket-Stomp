!function(e){var t={};function r(n){if(t[n])return t[n].exports;var a=t[n]={i:n,l:!1,exports:{}};return e[n].call(a.exports,a,a.exports,r),a.l=!0,a.exports}r.m=e,r.c=t,r.d=function(e,t,n){r.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},r.r=function(e){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},r.t=function(e,t){if(1&t&&(e=r(e)),8&t)return e;if(4&t&&"object"==typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(r.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var a in e)r.d(n,a,function(t){return e[t]}.bind(null,a));return n},r.n=function(e){var t=e&&e.__esModule?function(){return e.default}:function(){return e};return r.d(t,"a",t),t},r.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},r.p="",r(r.s=1)}([function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0}),t.mainChatUrl=t.mainUrlPrefix=void 0,t.mainUrlPrefix="http://larryjason.com:8081/api/",t.mainChatUrl="http://larryjason.com/chat-app/chat";function n(e){for(var t=e+"=",r=document.cookie.split(";"),n=0;n<r.length;n++){for(var a=r[n];" "==a.charAt(0);)a=a.substring(1,a.length);if(0==a.indexOf(t))return a.substring(t.length,a.length)}return null}var a,o=function(){var e=$("#submit").get(0),r=$("#createaccount").get(0),a={userName:$("#username").get(0).value,passWord:$("#password").get(0).value};e.disabled=!0,r.onclick=function(){return!1},$.ajax({url:t.mainUrlPrefix+"user/checklogin",method:"POST",timeout:0,headers:{"Content-Type":"application/json"},data:JSON.stringify(a),success:function(o){"login-failed"==o.data?alert("invalid username/password"):200!=o.status?alert(o.status+": "+o.error):(!function(e,t){var r,a=new Date;if(a.setTime(a.getTime()+9e5),r="; expires="+a.toUTCString(),null==n("userName")){var o=[t];document.cookie=e+"="+(JSON.stringify({data:o})||"")+r+"; path=/"}else{o=JSON.parse(n("userName"));for(var i=!1,u=0;u<o.data.length;u++)if(o.data[u]==t){i=!0;break}0==i&&o.data.push(t),document.cookie=e+"="+(JSON.stringify({data:o.data})||"")+r+"; path=/"}}("userName",a.userName.valueOf()),alert("ok"),window.location.href=t.mainChatUrl+"?username="+a.userName.valueOf()),e.disabled=!1,r.onclick=void 0}})};a=$("#password").get(0),$("#showpass").get(0).onclick=function(e){console.log(e.target),e.target.checked?a.type="text":a.type="password"},$("#submit").get(0).onclick=function(e){return function(){for(var e=$("input"),t=0;t<e.length;t++)if(0==e[t].value.length)return!1;return!0}()?(o(),!0):($("form").get(0).reportValidity(),!1)}},function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n,a=r(0);n=$("#password").get(0),$("#showpass").get(0).onclick=function(e){console.log(e.target),e.target.checked?n.type="text":n.type="password"},$("#submit").get(0).onclick=function(e){return function(){for(var e=$("input"),t=0;t<e.length;t++)if(0==e[t].value.length)return!1;return!0}()?(t=$("#submit").get(0),r=$("#backtologin").get(0),n={userName:$("#username").get(0).value,passWord:$("#password").get(0).value},t.disabled=!0,r.onclick=function(){return!1},$.ajax({url:a.mainUrlPrefix+"/user/add",method:"POST",timeout:0,headers:{"Content-Type":"application/json"},data:JSON.stringify(n),success:function(e){200!=e.status?alert(e.status+": "+e.error):"username-not-available"==e.data?alert(e.status+": "+e.data):(alert("ok"),window.location.replace("http://larryjason.com/chat-app/login/")),t.disabled=!1,r.onclick=void 0}}),!0):($("form").get(0).reportValidity(),!1);var t,r,n}}]);