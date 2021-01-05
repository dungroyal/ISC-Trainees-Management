import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import "@fortawesome/fontawesome-free/css/all.css"
import "bootstrap/dist/css/bootstrap.min.css";
import {BrowserRouter} from "react-router-dom";
import "./Plugin/css/custom.css";
import "./Plugin/css/style.css";
import "./Plugin/js/app";


ReactDOM.render(
    <BrowserRouter>
      <App />
    </BrowserRouter>,
  document.getElementById('root')
);