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
            .catch((error) => {
                console.error(error)
                history.push(`/artist/error-not-found`);
            })
    },[artistName, history]);

    if (artistName === "error-not-found"){
        return (
            <Overview>
                <h4>sorry, no artist was found for your criteria</h4>
            </Overview>
        )
    }

    if (!artist){
        return(
            <Loading>
                ...loading...
            </Loading>
        )
    }

    return (
        <Overview>
            <ArtistHeader key={artist.artistName} artist={artist}/>

            <span>Most popular/wanted album</span>
            {artist.artistAlbums.length === 0
                ? <ReleasePlaceholder releasetype={"albums"}/>
                : <>
                    <ReleaseDescription release={artist.artistAlbums[0]}/>
                    <Link to={`artist/${artistName}/albums`}>
                        <p>see more albums ...</p>
                    </Link>
                </>
            }
            <span>Most popular/wanted single/EP</span>
            {artist.artistSingles.length === 0
                ? <ReleasePlaceholder releasetype={"singles/EPs"}/>
                : <>
                    <ReleaseDescription release={artist.artistSingles[0]}/>
                    <Link to={`artist/${artistName}/singles-eps`}>
                        <p>see more singles/EPs ...</p>
                    </Link>
                </>
            }

            <span>Recommend '{artistName}' to other users</span>
            <ArtistRecommendation artistName={artistName}
                                  currentRecommendationTags={artist.recommendationTags}/>
        </Overview>)

}

const Overview = styled.div`

  display: flex;
  flex-direction: column;
  background-color: var(--secondary-color);
  padding: 20px;
  
  span{
    margin: 10px;
    font-size: 16px;
    color: var(--tertiary-color);
  }
  
  h4{
    margin: 20px;
    color: var(--tertiary-color);
  }
  
  p{
    margin: 0px 20px;
    font-size: 14px;
  }
  
  a{
    text-align: right;
    margin: 10px;
  }

`

const Loading =styled.section`
  
  display: flex;
  justify-content: center;
  align-items: center;
  
`
