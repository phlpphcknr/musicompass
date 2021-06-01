import {useEffect, useState} from "react";
import {getLatestRecommendations} from "../service/musiComApiService";
import ArtistHeader from './ArtistHeader'
import styled from "styled-components/macro";

export default function LatestArtistRecommendations () {

    const [latestArtistRecommendations, setLatestArtistRecommendations] = useState();

    useEffect(() => {
            getLatestRecommendations()
                .then(setLatestArtistRecommendations)
                .catch((error) => console.error(error))
        }, []
    );

    if (!latestArtistRecommendations){
        return(
            <Loading>
                ...loading...
            </Loading>
        )
    }

    return(
        <ArtistContainer>
            {latestArtistRecommendations.map((artist) =>
            <ArtistHeader key={artist.artistName} artist={artist} />)}
        </ArtistContainer>
    )

}

const ArtistContainer = styled.section`
  margin: 0px 15px;
`

const Loading = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 60px;
`