import styled from 'styled-components/macro'
import {Link} from "react-router-dom";

export default function ArtistHeader({artist}) {

    function artistImage () {
        if(artist.artistInfo.artistImageUrl === ""){
            return <img src={"https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png"} alt="Shows placeholder" />;
        }
        return <img src={artist.artistInfo.artistImageUrl} alt="Shows artist" />;
    }

    return (
        <Link to={`/artist/${artist.artistName}`} style={{textDecoration: 'none'}}>
            <Header>
                {artistImage()}
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
  margin: 5px;
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
