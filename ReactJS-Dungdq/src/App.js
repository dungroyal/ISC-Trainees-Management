import { Route, Switch } from "react-router-dom";
import DefaultLayout from "./Pages/DefaultLayout";
import login from "./Pages/Login/login";

function App() {
  return (
        <Switch>
            <Route path="/login" exact component={login}/>
            <Route path="/"  component={DefaultLayout}/>
        </Switch>
        
  );
}

export default App;
