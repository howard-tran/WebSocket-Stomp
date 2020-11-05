import { timeStamp } from "console";
import React, { Component } from "react";
import { loginPageUrl, mainUrlPrefix } from "./App";
import ReactDOM from "react-dom";
import axios from "axios";
import "./CSS/User.css";

interface IUser {
  id: String;
  userName: String;
  passWord: String;
}

interface APIResponse<T> {
  status: Number;
  error: String;
  message: String;
  data: T;
}

export class User extends Component<{}, IUser> {
  //

  componentDidMount() {
    this.loadUserData();
  }

  public getUserData() {
    if (this.state == null) {
      return null;
    }

    let obj: IUser = {
      id: this.state.id,
      userName: this.state.userName,
      passWord: this.state.passWord,
    };
    return obj;
  }

  private readCookie = (name: String) => {
    var nameEQ = name + "=";
    var ca = document.cookie.split(";");
    for (var i = 0; i < ca.length; i++) {
      var c = ca[i];
      while (c.charAt(0) == " ") c = c.substring(1, c.length);
      if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
  };

  private checkUsername = (username: String) => {
    let cookie = JSON.parse(this.readCookie("userName")) as {data: String[]}; 
    if (cookie == null) {
      return false;
    }

    for (let i = 0; i < cookie.data.length; i++) {
      if (cookie.data[i] == username) {
        return true;
      }
    }
    return false;
  }

  private loadUserData = async () => {
    let userName = window.location.search.slice(1).split("&")[0].split("=")[1];

    if (this.checkUsername(userName) == false) {
      window.location.href = loginPageUrl;
    } else {
      const response = await axios.get<APIResponse<IUser>>(
        `${mainUrlPrefix}user/get?username=${userName}`
      );
      console.log(response);
      this.setState({
        id: response.data.data.id,
        userName: response.data.data.userName,
        passWord: response.data.data.passWord,
      });
    }
  };

  private getUserName = () => {
    if (this.state == null) {
      console.log("no state");
      return "waitting...";
    } else {
      return this.state.userName;
    }
  };

  render() {
    return (
      <div className="user-main">
        <div className="user-container">
          <h1 className="user-h1">Welcome: </h1>
          <h2 className="user-h2">{this.getUserName()}</h2>
        </div>
      </div>
    );
  }
}
