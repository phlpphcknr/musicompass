import styled from "styled-components/macro";
import {Link, useHistory, useParams} from 'react-router-dom'
import { useEffect, useState} from 'react'
import ArtistHeader from "../components/ArtistHeader";
import {getArtistByName} from "../service/musiComApiService";
import ReleaseDescription from "../components/ReleaseDescription";
import ArtistRecommendation from "../components/ArtistRecommendation";
import ReleasePlaceholder from "../components/ReleasePlaceholder";

export default function ArtistOverview(){
    const {artistName} = useParams();
    const [artist, setArtist] = useState();
    const history = useHistory();

    useEffect(() => {
        getArtistByName(artistName)
            .then(setArtist)
            .catch((error) => {console.error(error)})
    },[artistName, history]);

    if (!artist){
        return(
            <section>
                Loading
            </section>
        )
    }

    return (
        <Overview>
            <ArtistHeader key={artist.artistName} artist={artist}/>

            <h3>Most popular/wanted album</h3>
            {artist.artistAlbums.length === 0
                ? <ReleasePlaceholder releasetype={"albums"}/>
                : <>
                    <ReleaseDescription release={artist.artistAlbums[0]}/>
                    <Link to={`${artistName}/albums`}>
                        <h5>see more albums ...</h5>
                    </Link>
                </>
            }
            <h3>Most popular/wanted single/EP</h3>
            {artist.artistSingles.length === 0
                ? <ReleasePlaceholder releasetype={"singles/EPs"}/>
                : <>
                    <ReleaseDescription release={artist.artistSingles[0]}/>
                    <Link to={`${artistName}/singles-EPs`}>
                        <h5>see more singles/EPs ...</h5>
                    </Link>
                </>
            }

            <h3>Recommend '{artistName}' to other users</h3>
            <ArtistRecommendation artistName={artistName}
                                  currentRecommendationTags={artist.recommendationTags}/>
        </Overview>)

}

const Overview = styled.div`

  display: flex;
  flex-direction: column;
  background-color: var(--secondary-color);
  padding: 20px;
  
  h3{
    margin: 10px;
    font-size: 0.9em;
  }
  
  h4{
    margin: 20px;
  }
  
  h5{
    margin: 0px 20px;
  }
  
  a{
    text-align: right;
    margin: 10px;
  }

`
