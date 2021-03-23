import AppHeader from './components/AppHeader'
import { Route, Switch } from 'react-router-dom'
import Landing from './pages/Landing'
import ArtistSearchResult from './pages/ArtistSearchResult'
import styled from 'styled-components/macro'

export default function App() {
  return (
    <PageLayout>
      <AppHeader/>
      <Switch>
        <Route exact path="/">
          <Landing />
        </Route>
        <Route exact path="/artistsearch/:artistSearchTerm">
          <ArtistSearchResult />
        </Route>
          <Route exact path="/artist/:artistName">
              <ArtistOverview/>
          </Route>
      </Switch>
    </PageLayout>
  )
}

const PageLayout = styled.div`
  height: 100vh;
  display: grid;
  overflow-y: scroll;
  grid-template-rows: auto 1fr;
  background: var(--secondary-color);
  
  
  
  
`
