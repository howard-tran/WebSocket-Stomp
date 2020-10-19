import { timeStamp } from "console";
import React, { Component } from "react";
import { mainUrlPrefix, UserInstance, ConversationInstance } from "./App";
import ReactDOM from "react-dom";
import axios from "axios";

import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "../node_modules/font-awesome/css/font-awesome.min.css";
import "./CSS/SearchBar.css";
import { JsxElement, JsxEmit } from "typescript";

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

interface SearchBarState {
  waittingForDropDown: boolean;
}

export class SearchBar extends Component<{}, SearchBarState> {
  state = {
    waittingForDropDown: false,
  };

  private inputIns: HTMLInputElement;

  constructor(props: any) {
    super(props);
  }

  selectIcon() {
    if (this.state.waittingForDropDown) {
      return <i className="search-bar-statusIcon search-bar-loading fa fa-2x fa-spinner"></i>;
    } else {
      return <i className="search-bar-statusIcon fa fa-2x fa-check-circle"></i>;
    }
  }

  getListUserName = async (keyword: String) => {
    let response = await axios.get<APIResponse<any>>(
      `${mainUrlPrefix}user/find?searchkey=${keyword}`
    );
    return response.data.data;
  };

  postConversation = async (conversation: IConversation) => {
    let response = await axios.post<APIResponse<any>>(
      `${mainUrlPrefix}conversation/add`,
      conversation
    );
    return response.data;
  };

  searchBarInputHandle = (e: React.FormEvent) => {
    this.setState({
      waittingForDropDown: true,
    });

    let promise = this.getListUserName((e.target as HTMLInputElement).value);

    promise.then((res) => {
      let dataListNode: Node = this.inputIns.nextSibling;
      while (dataListNode.firstChild) {
        dataListNode.removeChild(dataListNode.firstChild);
      }

      let optionList: String = "";

      (res as Array<String>).map((x) => {
        optionList += `<option>${x}</option>\n`;
      });

      (dataListNode as HTMLElement).innerHTML = optionList.valueOf();

      this.setState({
        waittingForDropDown: false,
      });
    });
  };

  startConversationClick = () => {
    this.setState({
      waittingForDropDown: true,
    });

    ConversationInstance.postNewConversation(this.inputIns.value);

    this.setState({
      waittingForDropDown: false,
    });
  };

  render() {
    return (
      <div className="search-bar-main">
        <div className="search-bar-container">
          <button
            type="button"
            className="btn btn-outline-secondary btn-lg"
            onClick={this.startConversationClick}
          >
            Start Conversation
          </button>
          <div className="search-bar-text-container">
            <input
              type="text"
              name="searchBar"
              list="searchList"
              placeholder="Search Username"
              onInput={this.searchBarInputHandle}
              ref={(ins) => {
                this.inputIns = ins;
              }}
            ></input>
            <datalist id="searchList"></datalist>
          </div>
          {this.selectIcon()}
        </div>
      </div>
    );
  }
}
