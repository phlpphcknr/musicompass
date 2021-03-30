import styled from "styled-components/macro";
import { useParams } from 'react-router-dom'
import { useEffect, useState} from 'react'
import ArtistHeader from "../components/ArtistHeader";
import {getArtistByName} from "../service/musiComApiService";
import ReleaseListLink from "../components/ReleaseListLink";
import ReleaseDescription from "../components/ReleaseDescription";

export default function ArtistOverview(){

    const {artistName} = useParams();
    const [artist, setArtist] = useState([]);

    useEffect(() => {
        getArtistByName(artistName)
            .then(setArtist)
            .catch((error) => console.error(error))
    },[artistName]);


    return (
        <Overview>
            <ArtistHeader key={artist.artistName} artist={artist}/>
            <section>
                <ReleaseListLink format={"album"} artist={artist}/>
                <ReleaseDescription/>
            </section>
            <section>
                <ReleaseListLink format={"single/EP"} artist={artist}/>
                <ReleaseDescription/>
            </section>

        </Overview>)

}

const Overview = styled.div`

  display: flex;
  flex-direction: column;
  background-color: var(--secondary-color);
  padding: 20px;

`
