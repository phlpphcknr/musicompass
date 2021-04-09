import styled from 'styled-components/macro'
import { useState } from 'react'
import { useHistory } from 'react-router-dom'

export default function SearchArtist() {
  const [artistSearchTerm, setArtistSearchTerm] = useState('');
  const [emptySearchTerm, setEmptySearchTerm] = useState(false);
  const history = useHistory();

  const handleSubmit = (event) => {
      event.preventDefault();
      if(artistSearchTerm === ""){
          setEmptySearchTerm(true)
      } else {
          history.push(`/artistsearch/${artistSearchTerm}`);
      }
    }

  return (
      <SearchArtistContainer>
          <form onSubmit={handleSubmit}>
              <input
                  placeholder="type artist name"
                  type="text"
                  value={artistSearchTerm}
                  onChange={({target}) => setArtistSearchTerm(target.value)}
              />
              {emptySearchTerm &&
              <p class="warning">Enter a search term to look for artists</p>}
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

  .warning{
    font-size: 16px;
    text-align: center;
    margin: 15px 30px 0px 30px;
    color: var(--quarternary-color);
  }

  button {
    padding: 8px;
    margin: 15px 0px 0px 0px;
  }
`
