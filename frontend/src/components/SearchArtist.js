import styled from 'styled-components/macro';
import {useState} from "react";
import {Link, Redirect} from "react-router-dom";

export default function SearchArtist(){

    const [artistSearchTerm, setArtistSearchTerm] = useState('');

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
  }
`