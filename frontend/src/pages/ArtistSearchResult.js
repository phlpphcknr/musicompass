import styled from 'styled-components/macro'
import { useParams } from 'react-router-dom'
import { useEffect, useState } from 'react'
import ArtistHeader from '../components/ArtistHeader'
import { getArtistSearchResult } from '../service/musiComApiService'
import SearchArtist from "../components/SearchArtist";

export default function ArtistSearchResult() {
    const {artistSearchTerm} = useParams()
    const [artistSearchResultList, setArtistSearchResultList] = useState([])

    useEffect(() => {
        getArtistSearchResult(artistSearchTerm)
            .then(setArtistSearchResultList)
            .catch((error) => console.error(error))
    }, [artistSearchTerm])

    return (
        <SearchResults>
            <SearchArtist/>
            <p>Search results for '{artistSearchTerm}'</p>
            {artistSearchResultList.map((artist) =>
                <ArtistHeader key={artist.artistName} artist={artist}/>)}
        </SearchResults>
    )
}

const SearchResults = styled.div`

  display: flex;
  flex-direction: column;
  background-color: var(--secondary-color);
  padding: 20px;

  p {
    margin: 15px 0px;
    //margin-top: 15px;
    font-size: 1.0em;
    width: 100%;
    text-align: left;
  }
`
