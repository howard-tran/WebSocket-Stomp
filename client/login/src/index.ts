export interface User {
  userName: String;
  passWord: String;
}

export interface APIResponse<T> {
  status: number;
  error: string;
  message: string;
  data: T;
}

// server http://larryjason.com:8081/api/
// local http://localhost:8002/api/
export const mainUrlPrefix = "http://larryjason.com:8081/api/";

// server http://larryjason.com/chat-app/chat
// local http://localhost:3000/
export const mainChatUrl = "http://larryjason.com/chat-app/chat";

const showHidePass = () => {
  let passwordbox = $("#password").get(0) as HTMLInputElement;
  $("#showpass").get(0).onclick = function (e: Event) {
    console.log(e.target);

    if ((e.target as HTMLInputElement).checked) {
      passwordbox.type = "text";
    } else {
      passwordbox.type = "password";
    }
  };
};

const checkData = () => {
  let listInput = $("input");
  for (let i = 0; i < listInput.length; i++) {
    let element = <HTMLInputElement>listInput[i];

    if (element.value.length == 0) {
      return false;
    }
  }
  return true;
};

function readCookie(name: String) {
  var nameEQ = name + "=";
  var ca = document.cookie.split(";");
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == " ") c = c.substring(1, c.length);
    if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
  }
  return null;
};

function setCookie(name, value) {
  let expires = "";
  let date = new Date();
  date.setTime(date.getTime() + 15 * 60 * 1000);
  expires = "; expires=" + date.toUTCString();

  if (readCookie("userName") == null) {
    let listUsername = [value];  

    document.cookie = name + "=" + (JSON.stringify({data: listUsername}) || "") + expires + "; path=/";
  } else {
    let listUsername = JSON.parse(readCookie("userName")) as {data: String[]};
    
    let flag = false;
    for (let i = 0; i < listUsername.data.length; i++) {
      if (listUsername.data[i] == value) {
        flag = true;
        break;
      }
    }

    if (flag == false) {
      listUsername.data.push(value); 
    }
    document.cookie = name + "=" + (JSON.stringify({data: listUsername.data}) || "") + expires + "; path=/";
  }
}

const sendData = () => {
  let submitBtn = $("#submit").get(0) as HTMLInputElement;
  let createAccount = $("#createaccount").get(0) as HTMLElement;

  const dataSubmit: User = {
    userName: ($("#username").get(0) as HTMLInputElement).value,
    passWord: ($("#password").get(0) as HTMLInputElement).value,
  };

  submitBtn.disabled = true;
  createAccount.onclick = function () {
    return false;
  };

  $.ajax({
    url: `${mainUrlPrefix}user/checklogin`,
    method: "POST",
    timeout: 0,
    headers: {
      "Content-Type": "application/json",
    },
    data: JSON.stringify(dataSubmit),
    success: (data: APIResponse<String>) => {
      //
      if (data.data == "login-failed") {
        alert("invalid username/password");
        //
      } else if (data.status != 200) {
        alert(`${data.status}: ${data.error}`);
        //
      } else {
        setCookie("userName", dataSubmit.userName.valueOf());

        alert("ok");

        window.location.href = `${mainChatUrl}?username=${dataSubmit.userName.valueOf()}`;
      }
      submitBtn.disabled = false;
      createAccount.onclick = undefined;
    },
  });
};

(function () {
  showHidePass();

  $("#submit").get(0).onclick = function (e: Event) {
    if (!checkData()) {
      ($("form").get(0) as HTMLFormElement).reportValidity();

      return false;
    }
    sendData();

    return true;
  };
})();

export {};
