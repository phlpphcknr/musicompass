import styled from 'styled-components/macro'
import { useParams } from 'react-router-dom'
import { useEffect, useState } from 'react'
import ArtistHeader from '../components/ArtistHeader'
import { getArtistSearchResult } from '../service/musiComApiService'

export default function ArtistSearchResult() {
    const {artistSearchTerm} = useParams()
    const [artistSearchResult, setArtistSearchResult] = useState('')

    useEffect(() => {
        getArtistSearchResult(artistSearchTerm).then(setArtistSearchResult)
    }, [artistSearchTerm])

    return (
        <SearchResults>
            <p>Search results for '{artistSearchTerm}'</p>
            <ArtistHeader artist={artistSearchResult}/>
        </SearchResults>
    )
}

const SearchResults = styled.div`

  display: flex;
  flex-direction: column;
  background-color: var(--secondary-color);
  padding: 20px;

  p {
    margin-bottom: 15px;
    margin-top: 15px;
    font-size: 0.6em;
    width: 100%;
    text-align: left;
  }
`
