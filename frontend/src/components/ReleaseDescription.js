import styled from 'styled-components/macro'
import {Link, useParams} from "react-router-dom";
import Typography from '@material-ui/core/Typography';

export default function ReleaseDescription({release}){

    const {artistName} = useParams();

    return (
        <Link to={`/artist/${artistName}`} style={{textDecoration: 'none'}}>
            <Release>
                <img src={release.coverImageUrl} alt={'Shows the release cover'}/>
                <section>
                <h3>{release.fullTitle}</h3>
                <h3>{release.releaseYear}</h3>
                </section>
            </Release>
        </Link>
    )
}



const Release = styled.div`

  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: var(--secondary-color);
  padding: 20px;

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