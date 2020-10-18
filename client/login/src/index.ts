interface User {
  userName: String;
  passWord: String;
}

interface APIResponse<T> {
  status: number;
  error: string;
  message: string;
  data: T;
}

const deployUrlPrefix = "http://larryjason.com:8002/api/"; 
const localUrlPrefix = "http://localhost:8002/api/"; 
const mainUrlPrefix = localUrlPrefix; 

const chatUrlLocal = "http://localhost:3000"; 
const chatUrlDeploy = "http://larryjason:3000"; 
const mainChatUrl = chatUrlLocal; 


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
}

const checkData = () => {
  let listInput = $("input"); 
  for (let i = 0; i < listInput.length; i++) {
    let element = <HTMLInputElement>listInput[i];

    if (element.value.length == 0) {
      return false;
    }
  }
  return true;
}

const setCookie = (name,value) => {
  let expires = "";

  var date = new Date();

  date.setTime(date.getTime() + (5*60*1000));
  expires = "; expires=" + date.toUTCString();

  document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}

const getCookie = (name: String) => {
  var nameEQ = name + "=";
  var ca = document.cookie.split(";");

  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == " ") c = c.substring(1, c.length);

    if (c.indexOf(nameEQ) == 0) {
      return c.substring(nameEQ.length, c.length);
    }
  }
  return null;
};

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
        setCookie("userName", dataSubmit.userName); 
        
        console.log(getCookie("userName"))

        alert("ok");
        window.location.href = mainChatUrl;
      }
      submitBtn.disabled = false;
      createAccount.onclick = undefined;
    },
  });
}

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

export {
}
