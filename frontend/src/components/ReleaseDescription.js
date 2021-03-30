import styled from 'styled-components/macro'
import {Link, useParams} from "react-router-dom";
import Typography from '@material-ui/core/Typography';

export default function ReleaseDescription({artist, index}){

    useParams();

    return (
        <Release>
            <img src={artist.artistAlbums[index]?.coverImageUrl} alt={'Shows the release cover'}/>
            {/*<Typography variant="h5" component="h2">
                {releaseList(0).fullTitle}
            </Typography>*/}
            <p>Most popular & wanted {artist.artistAlbums[index]?.format}</p>
        </Release>
    )
}



const Release = styled.div`

  display: flex;
  flex-direction: column;
  background-color: var(--secondary-color);
  padding: 20px;
  
`