import AppHeader from './components/AppHeader'
import { Route, Switch } from 'react-router-dom'
import Landing from './pages/Landing'
import ArtistSearchResult from './pages/ArtistSearchResult'
import ArtistOverview from './pages/ArtistOverview'
import styled from 'styled-components/macro'
import ArtistReleaseOverview from "./pages/ArtistReleaseOverview";

export default function App() {
  return (
      <PageLayout>
          <AppHeader/>
          <main>
              <Switch>
                  <Route exact path="/">
                      <Landing/>
                  </Route>
                  <Route exact path="/artistsearch/:artistSearchTerm">
                      <ArtistSearchResult/>
                  </Route>
                  <Route exact path="/artist/:artistName">
                      <ArtistOverview/>
                  </Route>
                  <Route exact path="/artist/:artistName/:releaseType">
                      <ArtistReleaseOverview/>
                  </Route>
              </Switch>
          </main>
      </PageLayout>
  )
}

const PageLayout = styled.div`
  height: 100vh;
  display: grid;
  overflow-y: scroll;
  grid-template-rows: auto 1fr;
  background: var(--secondary-color);
  
  main{
    overflow-y: scroll;
  }
`
