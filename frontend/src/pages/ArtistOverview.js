import {useParams} from 'react-router-dom';
import {useEffect} from "react";
import {getArtistSearchResult} from "../service/musiComApiService";

export default function ArtistOverview() {
    const {artistName} = useParams()

    useEffect(() => {}, []);

    return (
        <ArtistOverview>
            <p>Welcome {artistName}</p>
        </ArtistOverview>
    )
}
