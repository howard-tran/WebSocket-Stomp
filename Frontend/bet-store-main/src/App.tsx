import React from 'react';
import Switch from 'react-bootstrap/esm/Switch';
import { Route } from 'react-router';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "../src/resource/font-awesome/css/font-awesome.min.css"
//import Login from './components/Login';
import "./App.css"
import Login from './components/Login';
import { BrowserRouter, Link } from 'react-router-dom';
import Header from './components/header/Header';
import Footer from './components/footer/Footer';
import CategoryView from './components/CategoryView'
import Home from './screen/home';



function App() {
  
  return (
    <div className="container">
        <BrowserRouter>
          <Switch>
            <Route path="/login" exact component = {Login}/>
            <Route path = "/" exact component = {Home}/>
          </Switch>
        </BrowserRouter>
    </div>
  );
}

export default App;
