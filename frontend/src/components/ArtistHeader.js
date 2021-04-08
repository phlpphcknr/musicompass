import styled from 'styled-components/macro'
import {Link} from "react-router-dom";

export default function ArtistHeader({artist}) {

    return (
        <Link to={`/artist/${artist.artistName}`} style={{textDecoration: 'none'}}>
            <Header>
                <img src={artist.artistInfo.artistImageUrl} alt="" />
                <h1>{artist.artistName}</h1>
            </Header>
        </Link>
    )
}


const Header = styled.section`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background: var(--primary-color);
  box-shadow: 0px 2px 4px #333;
  
  img{
    max-width: 120px;
    max-height: 120px;
    width: auto;
    height: auto;
    margin: 10px;
  }
  
  h1{
    padding: 10px;
    width: 100%;
    text-align: center;
    font-size: 20px;
    color: var(--tertiary-color);
  }
  
`
