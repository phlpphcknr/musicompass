import styled from 'styled-components/macro'
import { useState } from 'react'
import { Link } from 'react-router-dom'

export default function SearchArtist() {
  const [artistSearchTerm, setArtistSearchTerm] = useState('')

  return (
    <SearchArtistContainer>
      <form>
        <input
          placeholder="the artist I want to find out about is..."
          type="text"
          value={artistSearchTerm}
          onChange={({ target }) => setArtistSearchTerm(target.value)}
        />
        <Link to={`/artistsearch/${artistSearchTerm}`}>
          <button>SEARCH</button>
        </Link>
      </form>
    </SearchArtistContainer>
  )
}

const SearchArtistContainer = styled.div`
  form {
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
  }

  input {
    flex-grow: 1;
    text-align: left;
    margin: 8px;
  }

  button {
    padding: 8px;
    margin: 8px;
  }
`
