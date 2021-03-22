import styled from 'styled-components/macro'
import {Link} from "react-router-dom";

export default function ArtistHeader({artist}) {

    return (
        <Link to={`/artist/${artist.artistName}`} style={{textDecoration: 'none'}}>
            <Header>
                <img src={artist.artistImageUrl} alt={'Could not be loaded'}/>
                <h3>{artist.artistName}</h3>
            </Header>
        </Link>
    )
}

const Header = styled.section`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background: var(--primary-color);
  box-shadow: 0px 2px 4px #333;
  img {
    width: 50%;
    max-width: 120px;
    margin: 10px;
  }
  h3 {
    padding: 0px;
    margin: 0px;
    width: 100%;
    text-align: center;
    font-size: 1.0em;
    color: #636e72;
  }
`
