import styled from 'styled-components/macro'
import RecommendationTagElement from "./RecommendationTagElement";
import {useEffect, useState} from 'react';
import {getRecommendationTagCategories} from "../service/musiComApiService";

export default function ArtistRecommendation ({onRecommend, artistName}){

    const [recommendationTagObjects, setRecommendationTagObjects] = useState();

    useEffect(() => {
        getRecommendationTagCategories()
            .then(setRecommendationTagObjects)
            .catch((error) => console.error(error))
    },);

    const recommend = () => {};

    if(!recommendationTagObjects){
        return(
            <section>
                Loading
            </section>
        )
    }

    return(
        <ArtistRecommender>
            <RecommendationTags>
                {recommendationTagObjects.map((recommendationTagObject) =>
                    <RecommendationTagElement key={recommendationTagObject.categoryName}
                                              recommendationTagObject={recommendationTagObject}/>)}
            </RecommendationTags>
            <button onClick={recommend}>RECOMMEND</button>
        </ArtistRecommender>
    )
}

const ArtistRecommender = styled.section`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  margin: 0px 20px;
  background: var(--primary-color);
  box-shadow: 0px 2px 4px #333;
`

const RecommendationTags = styled.section`
  display: flex;
  flex-direction: column;
`