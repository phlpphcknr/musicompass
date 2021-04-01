import styled from "styled-components/macro";
import {Link, useParams} from 'react-router-dom'
import { useEffect, useState} from 'react'
import ArtistHeader from "../components/ArtistHeader";
import {getArtistByName} from "../service/musiComApiService";
import ReleaseDescription from "../components/ReleaseDescription";
import ArtistRecommendation from "../components/ArtistRecommendation";

export default function ArtistOverview(){
    const {artistName} = useParams();
    const [artist, setArtist] = useState();

    useEffect(() => {
        getArtistByName(artistName)
            .then(setArtist)
            .catch((error) => console.error(error))
    },[artistName]);

    if (!artist){
        return(
            <section>
                Loading
            </section>
        )
    }

    const onRecommend = () =>{};

    return (
        <Overview>
            <ArtistHeader key={artist.artistName} artist={artist}/>
            <h3>Most popular/wanted album</h3>
            <ReleaseDescription release={artist.artistAlbums[0]}/>
            <Link to={`artist/${artistName}/albums`} >
                <h5>see more albums ...</h5>
            </Link>
            <h3>Most popular/wanted single/EP</h3>
            <ReleaseDescription release={artist.artistSingles[0]}/>
            <Link to={`artist/${artistName}/singles-eps`} >
                <h5>see more singles/EPs ...</h5>
            </Link>
            <h3>Recommend '{artistName}' to other users</h3>
            <ArtistRecommendation artistName={artistName}
                                  onRecommend={onRecommend}/>
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
  
  h5{
    margin: 0px 20px;
  }
  
  a{
    text-align: right;
    margin: 10px;
  }

`
