import styled from 'styled-components/macro'
import { useState } from 'react'
import { useHistory } from 'react-router-dom'

export default function SearchArtist() {
  const [artistSearchTerm, setArtistSearchTerm] = useState('');
  const history = useHistory();

  const handleSubmit = (event) => {
      event.preventDefault();
      history.push(`/artistsearch/${artistSearchTerm}`);
    }

  return (
    <SearchArtistContainer>
      <form onSubmit={handleSubmit}>
        <input
          placeholder="the artist I want to find out about is..."
          type="text"
          value={artistSearchTerm}
          onChange={({ target }) => setArtistSearchTerm(target.value)}
        />
          <button>SEARCH</button>
      </form>
    </SearchArtistContainer>
  )
}

const SearchArtistContainer = styled.section`
  
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
