import { timeStamp } from "console";
import React, { Component } from "react";
import {
  ConversationInstance,
  mainUrlPrefix,
  MessageInstance,
  messageSocketPrefix,
  UserInstance,
} from "./App";
import ReactDOM from "react-dom";
import axios from "axios";

import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "../node_modules/font-awesome/css/font-awesome.min.css";
import "./CSS/Conversation.css";
import { JsxElement, JsxEmit } from "typescript";
import { resolveSoa } from "dns";
import { Button } from "bootstrap";
import { CompatClient, Frame, IFrame, IMessage, Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { frame } from "websocket";
import userEvent from "@testing-library/user-event";

interface IConversation {
  id: String;
  sender: String;
  receiver: String;
  unixTime: String;
}

interface APIResponse<T> {
  status: Number;
  error: String;
  message: String;
  data: T;
}

interface ConversationState {
  conversationIndex: number;
}

export class Conversation extends Component<{}, ConversationState> {
  private conversationMain: HTMLDivElement;
  private intervalId: NodeJS.Timeout;

  private stompClient: CompatClient;

  state: ConversationState = {
    conversationIndex: 0,
  };

  constructor(props: any) {
    super(props);
  }

  componentDidMount() {
    this.intervalId = setInterval(this.loadConversation, 500);
  }

  connectAndSubscribe = () => {
    let userData = UserInstance.getUserData();

    let socket = new SockJS(`${messageSocketPrefix}socket-service`);
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, (frame: IFrame) => {
      this.stompClient.subscribe(`/conversation/${userData.userName}`, this.receiveNewConversation);
    });
  };

  receiveNewConversation = (message: IMessage) => {
    let messageBody = JSON.parse(message.body) as APIResponse<any>;

    console.log(messageBody);

    if (messageBody.status != 200) {
      return;
    }
    if ((messageBody.data as IConversation).sender == UserInstance.getUserData().userName) {
      this.addNewConversationUI((messageBody.data as IConversation).receiver);
    }
  };

  public notifyNewConveresation = (username: String) => {
    let conversation: IConversation = {
      id: "",
      sender: UserInstance.getUserData().userName,
      receiver: username,
      unixTime: "",
    };
    this.stompClient.send("/service/notify-conversation", {}, JSON.stringify(conversation));
  };

  getConversation = async (username: String) => {
    let response = await axios.get(
      `${mainUrlPrefix}conversation/get?username=${username}&index=${this.state.conversationIndex}`
    );
    return response.data;
  };

  loadConversation = () => {
    let user = UserInstance.getUserData();

    if (user == null) return;
    else {
      clearInterval(this.intervalId);
    }
    this.connectAndSubscribe();

    let promise = this.getConversation(user.userName);

    promise.then((res) => {
      let _res = res as APIResponse<any>;

      let conversationList: String = "";
      let countConversation: number = 0;

      if (_res.status != 200) {
        alert(`${_res.status}: ${_res.message}`);
      } else {
        let arr = res as APIResponse<IConversation[]>;
        let aTagChild = this.conversationMain.querySelector("a");

        arr.data.map((x) => {
          countConversation += 1;

          this.conversationMain.insertBefore(this.generateConversationUI(x.receiver), aTagChild);
        });
      }
      if (this.conversationMain.childNodes.length > 1) {
        (this.conversationMain.firstChild as HTMLButtonElement).click();
      }
      this.setState({
        conversationIndex: this.state.conversationIndex + countConversation,
      });
    });
  };

  loadMoreConversation = (e: React.MouseEvent) => {
    console.log(this);
    this.loadConversation();

    return false;
  };

  private addNewConversationUI(username: String) {
    if (this.conversationMain.firstChild) {
      this.conversationMain.insertBefore(
        this.generateConversationUI(username),
        this.conversationMain.firstChild
      );
    } else {
      this.conversationMain.appendChild(this.generateConversationUI(username));
    }
    this.setState({
      conversationIndex: this.state.conversationIndex + 1,
    });
  }

  private addConversation(username: String) {
    let conversation: IConversation = {
      id: "",
      sender: UserInstance.getUserData().userName,
      receiver: username,
      unixTime: "",
    };
    let response = axios.post<APIResponse<any>>(`${mainUrlPrefix}conversation/check`, conversation);

    response.then((res) => {
      let _res = res.data as APIResponse<any>;

      if (_res.status != 200) {
        alert(`${_res.status}: ${_res.message}`);
      } else if ((_res.data as String) == "conversation not available") {
        alert(`${_res.status}: ${_res.data}`);
      } else {
        alert("ok");

        this.notifyNewConveresation(username);
      }
    });
  }

  public postNewConversation(username: String) {
    this.addConversation(username);
  }

  conversationClick = (e: MouseEvent) => {
    // connecto to message
    MessageInstance.loadFromConversation((e.target as HTMLButtonElement).value);
  };

  generateConversationUI = (username: String): Node => {
    let res = document.createElement("button");
    res.type = "button";
    res.className = "btn btn-outline-secondary";
    res.value = username.valueOf();
    res.innerHTML = `👥 ${username}`;
    res.onclick = this.conversationClick;

    return res;
  };

  render() {
    return (
      <div
        className="conversation-main"
        ref={(ins) => {
          this.conversationMain = ins;
        }}
      >
        <a href="#" onClick={this.loadMoreConversation}>
          load more
        </a>
      </div>
    );
  }
}
