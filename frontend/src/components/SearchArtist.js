import styled from 'styled-components/macro';
import {useState} from "react";

export default function SearchArtist(){

    const [artistName, setArtistName] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault()
        if (!artistName) {
            return
        }
        // put in action to be executed on submit ... loginUser(userName, userPassword).then(setToken)
        setArtistName('')
    }

    return (
        <SearchArtistContainer>
            <form onSubmit={handleSubmit}>
                <input
                    placeholder="the artist I want to find out about is..."
                    type="text"
                    value={artistName}
                    onChange={({ target }) => setArtistName(target.value)}
                />
                <button type="submit">SEARCH</button>
            </form>
        </SearchArtistContainer>
    )

}

const SearchArtistContainer = styled.div`
  display:flex;
  flex-direction: row;
  justify-content: space-evenly;

  form {
    display: flex;
    flex-direction: row;
  }

  input {
    flex-grow: 1;
    text-align: center;
    margin: 8px;
  }

  button {
    padding: 8px;
    margin: 8px;
    background-color: chocolate;
  }
`