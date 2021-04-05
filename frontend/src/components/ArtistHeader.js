import styled from 'styled-components/macro'
import {Link} from "react-router-dom";

export default function ArtistHeader({artist}) {

    function artistImage () {
        if(artist.artistInfo.artistImageUrl === ""){
            return <img src={"https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png"} alt="" />;
        }
        return <img src={artist.artistInfo.artistImageUrl} alt="" />;
    }

    return (
        <Link to={`/artist/${artist.artistName}`} style={{textDecoration: 'none'}}>
            <Header>
                {artistImage()}
                <h2>{artist.artistName}</h2>
            </Header>
        </Link>
    )
}

const Header = styled.section`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  margin: 10px 0px;
  background: var(--primary-color);
  box-shadow: 0px 2px 4px #333;
  
  h2 {
    padding: 0px;
    margin: 0px;
    width: 100%;
    text-align: center;
    font-size: 1.2em;
    color: #636e72;
  }
`
