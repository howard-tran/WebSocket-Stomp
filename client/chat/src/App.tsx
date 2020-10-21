import React from "react";
import logo from "./Resource/logo.svg";
import "./CSS/App.css";

import { User } from "./User";
import { SearchBar } from "./SearchBar";
import { JsxElement } from "typescript";
import { Conversation } from "./Conversation";
import { Message } from "./Message";

// server http://larryjason.com:8081/api/
// local http://localhost:8002/api/
export const mainUrlPrefix = "http://larryjason.com:8081/api/";

// server http://larryjason.com:8081/
// local http://localhost:8002/
export const messageSocketPrefix = "http://larryjason.com:8081/";

// server http://larryjason.com/chat-app/login/
// local http://localhost:3001/
export const loginPageUrl = "http://larryjason.com/chat-app/login/";

export let UserInstance: User;
export let ConversationInstance: Conversation;
export let MessageInstance: Message;

function App() {
  return (
    <div className="mainContainer">
      <div className="message-grid">
        <div className="message-welcome">
          <User
            ref={(ins) => {
              UserInstance = ins;
            }}
          />
        </div>
        <div className="message-searchBar">
          <SearchBar />
        </div>
        <div className="message-conversation">
          <Conversation
            ref={(ins) => {
              ConversationInstance = ins;
            }}
          />
        </div>
        <div className="message-chat">
          <Message
            ref={(ins) => {
              MessageInstance = ins;
            }}
          />
        </div>
      </div>
    </div>
  );
}
export default App;
