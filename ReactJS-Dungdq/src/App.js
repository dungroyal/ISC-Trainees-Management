import { Route, Switch } from "react-router-dom";
import DefaultLayout from "./Pages/DefaultLayout";
import Login from './Pages/Auth/login';

function App() {
  return (
    <Switch>
        <Route exact path="/login" component={Login} />
        <Route path="/"  component={DefaultLayout}/>
    </Switch>
  );
}

export default App;
