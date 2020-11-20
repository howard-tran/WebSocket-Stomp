import { defaultCoreCipherList } from "constants";
import React from "react";
import { Link } from "react-router-dom";


export default function Home() {
    return (
        <div className="hello">
            <h1 className="hello">Hello World</h1>
            <Link className="login-cs" to="/login">Login</Link>
        </div>
    );
} 
  