import {useParams} from "react-router-dom";

export default function ArtistSearchResult(){

    const { artistSearchTerm } = useParams();

    return (
        <section>
            Hello Search Result
        </section>
    )
}