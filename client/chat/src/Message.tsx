import { timeStamp } from "console";
import React, { Component, createRef } from "react";
import { ConversationInstance, mainUrlPrefix, messageSocketPrefix, UserInstance } from "./App";
import ReactDOM from "react-dom";
import axios from "axios";

import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "../node_modules/font-awesome/css/font-awesome.min.css";
import "./CSS/Message.css";
import { JsxElement, JsxEmit, textChangeRangeIsUnchanged } from "typescript";
import { CompatClient, Frame, IMessage, Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";

interface IMessage_t {
  id: String;
  sender: String;
  receiver: String;
  content: String;
  unixTime: String;
}

interface APIResponse<T> {
  status: Number;
  error: String;
  message: String;
  data: T;
}

interface MessageState {  
  messageCount: number;
  messageIndex: number;
  messageReciever: String; 
  isLoading: boolean;
}

export class Message extends Component<{}, MessageState> {
  state : MessageState = {
    messageCount: 0,
    messageIndex: 0,
    messageReciever: null,
    isLoading: false
  }

  private stompClient: CompatClient;

  private messageTxtIns : HTMLTextAreaElement; 
  private messageAreaIns : HTMLDivElement;

  private intervalId : NodeJS.Timeout;

  private : String;

  constructor(props : any) {
    super(props); 
  }

  componentDidMount() {
    this.intervalId = setInterval(this.connectAndSubscribe, 500); 
  }

  connectAndSubscribe = () => {
    if (!UserInstance.getUserData()) {
      return;
    } else {
      clearInterval(this.intervalId);
    }

    let userData = UserInstance.getUserData();

    let socket = new SockJS(`${messageSocketPrefix}socket-service`);
    this.stompClient = Stomp.over(socket); 

    this.stompClient.connect({}, (frame: Frame) => {
      this.stompClient.subscribe(`/room/${userData.userName}`, this.receiveMessageHandler); 
    });
  }

  sendMessageHandler(content: String) {
    if (this.state.messageReciever == null || this.state.messageReciever == "") {
      alert("please choose one conversation or create one"); 
      return;
    }
    let message : IMessage_t = {
      id: "",
      sender: UserInstance.getUserData().userName,
      receiver: this.state.messageReciever,
      content: content,
      unixTime: ""
    }
    this.stompClient.send("/service/chat", {}, JSON.stringify(message)); 
  }

  loadMessasgeFromConversation = (receiver : String) => {
    let user = UserInstance.getUserData();
    let promise = this.getMessage(user.userName, receiver);

    this.setState({isLoading: true}); 

    promise.then((res : any) => {
      let _res = res as APIResponse<any>;

      let conversationList: String = "";
      let countMessage: number = 0;

      while (this.messageAreaIns.firstChild) {
        this.messageAreaIns.removeChild(this.messageAreaIns.firstChild); 
      }

      if (_res.status != 200) {
        alert(`${_res.status}: ${_res.message}`);
      } else {
        let arr = res as APIResponse<IMessage_t[]>;

        arr.data.reverse();
        arr.data.map((x) => {
          countMessage += 1;

          if (x.sender == user.userName) {
            this.messageAreaIns.appendChild(this.generateMyMessage(x.content)); 
          } else {
            this.messageAreaIns.appendChild(this.generateOtherMessage(x.content)); 
          }
        });
      }

      this.setState({
        messageIndex: this.state.messageIndex + countMessage,
        isLoading: false
      });
    });
  }

  loadPrevMessage = () => {
    if (!this.messageAreaIns.firstChild) {
      return;
    }
    
    let user = UserInstance.getUserData();

    let promise = this.getMessage(user.userName, this.state.messageReciever);

    promise.then((res : any) => {
      let _res = res as APIResponse<any>;

      let conversationList: String = "";
      let countMessage: number = 0;

      console.log(res);

      if (_res.status != 200) {
        alert(`${_res.status}: ${_res.message}`);
      } else {
        let arr = res as APIResponse<IMessage_t[]>;

        arr.data.map((x) => {
          countMessage += 1;

          if (x.sender == user.userName) {
            this.messageAreaIns.insertBefore(this.generateMyMessage(x.content), 
              this.messageAreaIns.firstChild); 
            
            
          } else {
            this.messageAreaIns.insertBefore(this.generateOtherMessage(x.content), 
              this.messageAreaIns.firstChild); 
          }
        });
      }

      this.setState({
        messageIndex: this.state.messageIndex + countMessage,
      });
    });
  }

  messageAreaScrollUp = (e : React.UIEvent) => {
    if ((e.target as HTMLElement).scrollTop == 0) {
      this.loadPrevMessage();
      (e.target as HTMLElement).scrollTop = 1;
    }
  }

  getMessage = async (sender: String, receiver: String) => {
    let response = await axios.get(
      `${mainUrlPrefix}message/get?sender=${sender}&receiver=${receiver}&index=${this.state.messageIndex}`
    );
    return response.data;
  }

  public loadFromConversation(receiver: String) {

    this.setState({
      messageReciever: receiver,
      messageIndex: 0,
      messageCount: 0,
    });
    
    this.displayReceiver();

    this.loadMessasgeFromConversation(receiver); 
  }


  displayReceiver() {
    if (this.state.messageReciever == null || this.state.messageReciever == "") {
      return;
    }
    (this.messageAreaIns.previousSibling.firstChild as HTMLElement).innerHTML = '🗣️ ' + this.state.messageReciever;
  }
  
  generateMyMessage = (content: String) : Node => {
    let element = document.createElement("div");
    element.className="message-mess-MyMessage"; 

    let child = document.createElement("div"); 
    child.innerHTML = content.valueOf().replace(/\n/g, '<br>');

    element.appendChild(child); 
    return element;
  }

  generateOtherMessage = (content : String) : Node => {
    let element = document.createElement("div");
    element.className="message-mess-OtherMessage"; 

    let child = document.createElement("div"); 
    child.innerHTML = content.valueOf().replace(/\n/g, '<br>');

    element.appendChild(child); 
    return element;
  }

  submitMessage = (e : React.MouseEvent) => {
    if (this.state.messageReciever == null || this.state.messageReciever == "") {
      alert("please choose on conversation or create one");
      return;
    }
    if (this.messageTxtIns.value == "" || this.state.isLoading) {
      return;
    }

    let messageContent = this.messageTxtIns.value;

    this.sendMessageHandler(messageContent); 
    this.messageAreaIns.appendChild(this.generateMyMessage(messageContent)); 
    this.messageTxtIns.value = ""; 

    this.messageAreaIns.scrollTop = this.messageAreaIns.scrollHeight; 

    this.setState({
      messageIndex: this.state.messageIndex + 1,
      messageCount: this.state.messageCount + 1
    }); 
  }

  receiveMessageHandler = (message : IMessage) => {
    let messageBody = JSON.parse(message.body) as APIResponse<any>;

    if ((messageBody.data as String) == "") {
      return;
    }

    if ((messageBody.data as IMessage_t).sender == this.state.messageReciever) {
      this.messageAreaIns.appendChild(this.generateOtherMessage((messageBody.data as IMessage_t).content));  
      this.messageAreaIns.scrollTop = this.messageAreaIns.scrollHeight; 

      this.setState({
        messageIndex: this.state.messageIndex + 1,
        messageCount: this.state.messageCount + 1
      }); 
    }
    else {
      if (ConversationInstance.checkAvailableConversation((messageBody.data as IMessage_t).sender)) {
        ConversationInstance.addNewConversationWithDB((messageBody.data as IMessage_t).sender); 
      }
    }
  }

  textAreaHandle = (e: React.KeyboardEvent) => {
    if (e.key == "Enter" && !e.shiftKey) {
      this.submitMessage(null); 
      e.preventDefault();
    }
  }
  render() {
    return (
      <div className="message-main">
        <div className="message-receiver-area">
          <h2> None </h2>
        </div>
        <div className="message-mess-area" ref={ins => {this.messageAreaIns = ins}}
          onScroll={this.messageAreaScrollUp}>
        </div>
        <div className="message-chat-area">
          <textarea rows={2} ref={ins => {this.messageTxtIns = ins}}
              onKeyDown={this.textAreaHandle}></textarea>
          <button type="button" className="btn btn-sm btn-primary" 
              onClick={this.submitMessage}>
            Submit
          </button>
        </div>
      </div>
    );
  }
}
