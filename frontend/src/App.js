import AppHeader from "./components/AppHeader";
import {Route, Switch} from "react-router-dom";
import Landing from "./pages/Landing";
import Artist from "./pages/Artist";
import styled from 'styled-components/macro'

function App() {
  return (
    <PageLayout>
        <AppHeader/>
        <Switch>
            <Route exact path="/">
                <Landing/>
            </Route>
            <Route exact path="/artist/:artistname">
                <Artist/>
            </Route>
        </Switch>
    </PageLayout>
  );
}

const PageLayout = styled.div`
  height: 100vh;
  display: grid;
  grid-template-rows: auto 1fr;

  background: #eee;
`

export default App;
