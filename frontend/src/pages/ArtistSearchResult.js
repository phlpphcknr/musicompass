import styled from 'styled-components/macro'
import { useParams } from 'react-router-dom'
import { useEffect, useState } from 'react'
import ArtistHeader from '../components/ArtistHeader'
import { getArtistSearchResult } from '../service/musiComApiService'

export default function ArtistSearchResult() {
  const { artistSearchTerm } = useParams()
  const [artistSearchResult, setArtistSearchResult] = useState('')

  useEffect(() => {
    getArtistSearchResult(artistSearchTerm).then(setArtistSearchResult)
  }, [])

  return (
    <SearchResults>
      <h3>Search results for '{artistSearchTerm}'</h3>
      <ArtistHeader artist={artistSearchResult} />
    </SearchResults>
  )
}

const SearchResults = styled.div`
  display: block;
  align-items: center;
  //padding: 20px;
  background-color: moccasin;
  h3 {
    margin-bottom: 15px;
    font-family: 'Courier New', monospace;
    font-size: 0.8em;
  }
  ArtistHeader {
  }
`
