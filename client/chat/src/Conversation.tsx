import { timeStamp } from "console";
import React, { Component } from "react";
import { ConversationInstance, mainUrlPrefix, MessageInstance, UserInstance } from "./App";
import ReactDOM from "react-dom";
import axios from "axios";

import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "../node_modules/font-awesome/css/font-awesome.min.css";
import "./CSS/Conversation.css";
import { JsxElement, JsxEmit } from "typescript";
import { resolveSoa } from "dns";

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

  state: ConversationState = {
    conversationIndex: 0,
  };

  constructor(props : any) {
    super(props); 
  }

  componentDidMount() {
    this.intervalId = setInterval(this.loadConversation, 500);
  }

  loadConversation = () => {
    let user = UserInstance.getUserData();

    if (user == null) return;
    else {
      clearInterval(this.intervalId);
    }

    let promise = this.getConversation(user.userName);

    promise.then((res) => {
      let _res = res as APIResponse<any>;

      let conversationList: String = "";  
      let countConversation: number = 0;

      if (_res.status != 200) {
        alert(`${_res.status}: ${_res.message}`);
      } else {
        let arr = res as APIResponse<IConversation[]>;

        arr.data.map((x) => {
          countConversation += 1;

          this.conversationMain.insertBefore(
            this.generateConversationUI(x.receiver),
            this.conversationMain.querySelector("a")
          );
        });
      }

      this.setState({
        conversationIndex: this.state.conversationIndex + countConversation,
      });
    });
  };

  
  loadMoreConversation = (e : React.MouseEvent) => {
    console.log(this);
    this.loadConversation();

    return false;
  }

  public addNewConversation(username: String) {
    if (this.conversationMain.firstChild) {
      this.conversationMain.insertBefore(
        this.generateConversationUI(username),
        this.conversationMain.firstChild);
    } else {
      this.conversationMain.appendChild(
        this.generateConversationUI(username)
      );
    }
    this.setState({
      conversationIndex: this.state.conversationIndex + 1,
    })
  }

  public addNewConversationWithDB(username: String) {
    let conversation: IConversation = {
      id: "",
      sender: UserInstance.getUserData().userName,
      receiver: username,
      unixTime: ""
    };
    let response = axios.post<APIResponse<any>>(
      `${mainUrlPrefix}conversation/add`,
      conversation
    );
    response.then(res => {
      this.addNewConversation(username);
    });
  }

  getConversation = async (username: String) => {
    let response = await axios.get(
      `${mainUrlPrefix}conversation/get?username=${username}&index=${this.state.conversationIndex}`
    );
    return response.data;
  };

  conversationClick = (e : MouseEvent) => {
    // connecto to message
    MessageInstance.loadFromConversation((e.target as HTMLButtonElement).value); 
  }

  public checkAvailableConversation(receiver: String) {
    for (let i = 0; i < this.conversationMain.children.length - 1; i++) {
      let child = this.conversationMain.children[i] as HTMLButtonElement;

      if (child.value == receiver) {
        return false;
      }
    }
    return true;
  }

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
        <a href="#" onClick={this.loadMoreConversation}>load more</a>
      </div>
    );
  }
}
