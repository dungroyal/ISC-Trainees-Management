import React from 'react';
import {Provider} from "react-redux";
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import "@fortawesome/fontawesome-free/css/all.css"
import "bootstrap/dist/css/bootstrap.min.css";
import {BrowserRouter} from "react-router-dom";
import "./Assets/css/custom.css";
import "./Assets/css/style.css";
import "./Assets/js/app";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-datepicker/dist/react-datepicker.css";
import "react-confirm-alert/src/react-confirm-alert.css";
import store from './Store/store';

toast.configure({
  autoClose: 3000,
  draggable: false,
  position: "top-right",
  hideProgressBar: false,
  newestOnTop: true,
  closeOnClick: true,
  rtl: false,
  pauseOnFocusLoss: true,
  pauseOnHover: true,
});

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root')
);