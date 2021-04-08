import styled from 'styled-components/macro'
import SearchArtist from '../components/SearchArtist'
import GetArtistRecommendation from "../components/GetArtistRecommendation";

export default function Landing() {
  return (
      <Wrapper>
          <p>Search for an artist and their releases</p>
          <SearchArtist/>
          <p>Set some tags and get an artist recommendation</p>
          <GetArtistRecommendation/>
      </Wrapper>
  )
}

const Wrapper = styled.div`
  display: grid;
  grid-template-rows: auto auto auto 1fr;
  background-color: var(--secondary-color);
  padding: 20px;

  p{
    margin: 10px 30px;
  }
`
