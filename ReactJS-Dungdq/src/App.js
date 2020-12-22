import { Route, Switch } from "react-router-dom";
import DefaultLayout from "./Pages/DefaultLayout";

function App() {
  return (
        <Switch>
            <Route path="/"  component={DefaultLayout}/>
        </Switch>
  );
}

export default App;
