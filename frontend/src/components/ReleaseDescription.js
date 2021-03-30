import styled from 'styled-components/macro'
import {Link} from "react-router-dom";

export default function ReleaseDescription({releaseList}){

    return (
        <Release>
            <img src={releaseList(0).artistImageUrl} alt={'Shows the artist'}/>
            <p>Most popular & wanted {format}</p>
            <Link to={`artist/${artist.artistName}/${format}s`}>
                <p>see more {format}s ...</p>
            </Link>
        </Release>
    )
}

const Release = styled.div`

  display: flex;
  flex-direction: column;
  background-color: var(--secondary-color);
  padding: 20px;
  
`