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
          placeholder="type artist name"
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
    flex-direction: column;
    justify-content: space-evenly;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
    margin: 0px 20px 20px 20px;
    background: var(--primary-color);
    box-shadow: 0px 2px 4px #333;
  }

  input {
    width: 90%;
    text-align: left;
    margin: 2px;
    padding: 5px;
    background-color: var(--primary-color);
    border-color: var(--tertiary-color);
    border-width: thin;
    border-radius: 0px;
    font-family: "Courier New", serif;
    font-weight: bold;
    color: var(--tertiary-color);
    
  }

  button {
    padding: 8px;
    margin: 20px 0px 0px 0px;
  }
`
