import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {getArtistByName} from "../service/musiComApiService";
import styled from "styled-components/macro";
import ArtistHeader from "../components/ArtistHeader";
import ReleaseDescription from "../components/ReleaseDescription";

export default function ArtistReleaseOverview(){

    const {artistName, releaseType} = useParams();
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

    function releaseList() {
        if (releaseType === "albums") {
            return (
                <>
                    {artist.artistAlbums.slice(0, 5).map((release) =>
                        <ReleaseDescription release={release}/>)}
                </>
            )
        } else if (releaseType === "singles-EPs") {
            return (
                <>
                    {artist.artistSingles.slice(0, 5).map((release) =>
                        <ReleaseDescription release={release}/>)}
                </>
            )
        }
    }

    return(
        <ReleaseOverview>
            <ArtistHeader key={artist.artistName} artist={artist}/>
            <h3>Most popular/wanted {releaseType.replace("-", "/")}</h3>
            {releaseList()}
            <Link to={`/artist/${artistName}`}>
                <h5>back to artist page ...</h5>
            </Link>
        </ReleaseOverview>
    )
}


const ReleaseOverview = styled.div`
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
  
  a{
    text-align: right;
    margin: 10px;
  }
`
