import { Route, Switch, BrowserRouter } from "react-router-dom";
import DefaultLayout from "./Pages/DefaultLayout";
import Login from './Pages/Auth/login';

function App() {
  return (
    <BrowserRouter>
        <Switch>
            <Route path="/login" exact component={Login}/>
            <Route path="/"  component={DefaultLayout}/>
        </Switch>
    </BrowserRouter>
  );
}

export default App;
