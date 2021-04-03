import styled from 'styled-components/macro'
import SearchArtist from '../components/SearchArtist'
import GetArtistRecommendation from "../components/GetArtistRecommendation";

export default function Landing() {
  return (
      <Wrapper>
          <SearchArtist/>
          <h3>Set some tags and get an artist recommendation</h3>
          <GetArtistRecommendation/>
      </Wrapper>
  )
}

const Wrapper = styled.div`
  display: grid;
  grid-template-rows: auto auto auto 1fr;
  background-color: var(--secondary-color);

  h3{
    margin: 10px 30px;
    font-size: 0.9em;
  }
`
