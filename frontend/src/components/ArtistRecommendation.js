import styled from 'styled-components/macro'
import RecommendationTagElement from "./RecommendationTagElement";
import {useEffect, useState} from 'react';
import {getRecommendationTagCategories, postRecommendationTag} from "../service/musiComApiService";

export default function ArtistRecommendation ({currentRecommendationTags, artistName}){

    const [recommendationTagCategories, setRecommendationTagCategories] = useState();
    const [genderTag, setGenderTag] = useState('');
    const [rolesTag, setRolesTag] = useState([]);
    const [genreTag, setGenreTag] = useState([]);

    useEffect(() => {
        getRecommendationTagCategories()
            .then(setRecommendationTagCategories)
            .catch((error) => console.error(error))
        if(currentRecommendationTags.recommended === true){
            setGenderTag(currentRecommendationTags.gender)
            setRolesTag(currentRecommendationTags.roles)
            setGenreTag(currentRecommendationTags.genre)
        }

    },);

    const recommend = () => {
        postRecommendationTag({artistName, })

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
                                          getRecommendation={genreTag}
                                          setRecommendation={setGenreTag}/>
                <RecommendationTagElement key={recommendationTagCategories[1].categoryName}
                                          recommendationTagObject={recommendationTagCategories[1]}
                                          getRecommendation={rolesTag}
                                          setRecommendation={setRolesTag}/>
                <RecommendationTagElement key={recommendationTagCategories[2].categoryName}
                                          recommendationTagObject={recommendationTagCategories[2]}
                                          getRecommendation={genderTag}
                                          setRecommendation={setGenderTag}/>
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


/*
return(
    <ArtistRecommender>
        <RecommendationTags>
            {recommendationTagCategories.map((recommendationTagCategory) =>
                <RecommendationTagElement key={recommendationTagCategory.categoryName}
                                          recommendationTagObject={recommendationTagCategory}
                                          setRecommendation={setRecommendationTag(recommendationTagCategory.categoryName)}/>)}
        </RecommendationTags>
        <button onClick={recommend}>RECOMMEND</button>
    </ArtistRecommender>
)*/
