import styled from 'styled-components/macro';
import SearchArtist from "../components/SearchArtist";

export default function Landing(){

    return (
        <Wrapper>
            <SearchArtist/>
        </Wrapper>
    )
}

const Wrapper = styled.div`
  display: grid;
  grid-template-rows: auto 1fr;
`