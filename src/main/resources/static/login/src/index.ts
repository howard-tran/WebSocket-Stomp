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

const deployUrlPrefix = "http://larryjason.com:8002"; 
const localUrlPrefix = "http://localhost:8002"; 
const mainUrlPrefix = localUrlPrefix; 

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
    url: "http://localhost:8002/api/user/checklogin",
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
        alert("ok");
        window.location.replace("../chat/build/index.html");
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
  User, APIResponse, deployUrlPrefix, localUrlPrefix, mainUrlPrefix
};