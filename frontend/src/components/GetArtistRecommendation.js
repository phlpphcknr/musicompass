import RecommendationTagElement from "./RecommendationTagElement";
import {getRecommendation, getRecommendationTagCategories} from "../service/musiComApiService";
import {useEffect, useState} from "react";
import styled from "styled-components/macro";
import {useHistory} from "react-router-dom";

export default function GetArtistRecommendation(){

    const [recommendationTagCategories, setRecommendationTagCategories] = useState();
    const [genderTag, setGenderTag] = useState([]);
    const [rolesTags, setRolesTags] = useState([]);
    const [genreTags, setGenreTags] = useState([]);
    const history = useHistory();

    useEffect(() => {
            getRecommendationTagCategories()
                .then(setRecommendationTagCategories)
                .catch((error) => console.error(error))
        },[]
    );

    function onClick() {
        getRecommendation({genreTags, rolesTags, genderTag})
            .then((response) => history.push(`/artist/${response}`))
            .catch((error) => {
                history.push(`/recommendation/not-available`);})
    };

    if(!recommendationTagCategories){
        return(
            <section>
                Loading
            </section>
        )
    }

    return(
        <ArtistRecommender>
            <RecommendationTags>
                <RecommendationTagElement key={recommendationTagCategories[0].categoryName}
                                          recommendationTagObject={recommendationTagCategories[0]}
                                          getRecommendation={[]}
                                          setRecommendation={setGenreTags}/>
                <RecommendationTagElement key={recommendationTagCategories[1].categoryName}
                                          recommendationTagObject={recommendationTagCategories[1]}
                                          getRecommendation={[]}
                                          setRecommendation={setRolesTags}/>
                <RecommendationTagElement key={recommendationTagCategories[2].categoryName}
                                          recommendationTagObject={recommendationTagCategories[2]}
                                          getRecommendation={[]}
                                          setRecommendation={setGenderTag}/>
            </RecommendationTags>
            <button onClick={onClick} > GET RECOMMENDATION </button>
        </ArtistRecommender>
    )
}

const ArtistRecommender = styled.section`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  margin: 0px 20px;
  background: var(--primary-color);
  box-shadow: 0px 2px 4px #333;
  
  button{
    margin: 20px 0px 0px 0px;
  }
`

const RecommendationTags = styled.section`
  display: flex;
  flex-direction: column;
`