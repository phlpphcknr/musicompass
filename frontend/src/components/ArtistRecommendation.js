import styled from 'styled-components/macro'
import RecommendationTagElement from "./RecommendationTagElement";
import {useEffect, useState} from 'react';
import {getRecommendationTagCategories, postRecommendationTag} from "../service/musiComApiService";

export default function ArtistRecommendation ({currentRecommendationTags, artistName}){

    const [recommendationTagCategories, setRecommendationTagCategories] = useState();
    const [genderTag, setGenderTag] = useState([]);
    const [rolesTags, setRolesTags] = useState([]);
    const [genreTags, setGenreTags] = useState([]);

    const [genderTagInitial, setGenderTagInitial] = useState([]);
    const [rolesTagsInitial, setRolesTagsInitial] = useState([]);
    const [genreTagsInitial, setGenreTagsInitial] = useState([]);
    const [recommended, setRecommended] = useState(currentRecommendationTags.recommended);

    const [noTagSelected, setNoTagSelected] = useState(false);

    useEffect(() => {
        getRecommendationTagCategories()
            .then(setRecommendationTagCategories)
            .catch((error) => console.error(error))
        if(currentRecommendationTags.recommended === true){
            setGenderTagInitial(currentRecommendationTags.gender)
            setRolesTagsInitial(currentRecommendationTags.roles)
            setGenreTagsInitial(currentRecommendationTags.genres)
        }
        },[currentRecommendationTags]
    );

    function setRecommendation() {
        if (genreTags.length === 0 && rolesTags.length === 0 && genderTag.length === 0) {
            setNoTagSelected(true)
        } else {
            postRecommendationTag({artistName, genreTags, rolesTags, genderTag})
                .then((recommendationTags) => {
                    setGenderTagInitial(recommendationTags.gender)
                    setRolesTagsInitial(recommendationTags.roles)
                    setGenreTagsInitial(recommendationTags.genres)
                    setRecommended(true)
                },
            setNoTagSelected(false)
        )
        }
    };

    if (!recommendationTagCategories){
        return(
            <Loading>
                ...loading...
            </Loading>
        )
    }

    return(
        <ArtistRecommender>
            <RecommendationTags>
                <RecommendationTagElement key={recommendationTagCategories[0].categoryName}
                                          recommendationTagObject={recommendationTagCategories[0]}
                                          getRecommendation={genreTagsInitial}
                                          setRecommendation={setGenreTags}/>
                <RecommendationTagElement key={recommendationTagCategories[1].categoryName}
                                          recommendationTagObject={recommendationTagCategories[1]}
                                          getRecommendation={rolesTagsInitial}
                                          setRecommendation={setRolesTags}/>
                <RecommendationTagElement key={recommendationTagCategories[2].categoryName}
                                          recommendationTagObject={recommendationTagCategories[2]}
                                          getRecommendation={genderTagInitial}
                                          setRecommendation={setGenderTag}/>
            </RecommendationTags>
            {noTagSelected &&
            <p class="warning" >Select at least one tag to make an artist recommendation</p>}
            {!recommended &&
            <button onClick={setRecommendation}>RECOMMEND</button>}
            {recommended &&
            <button onClick={setRecommendation}>UPDATE RECOMMENDATION</button>
            }
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

  .warning{
    font-size: 16px;
    text-align: center;
    margin: 15px 30px 0px;
    color: var(--quarternary-color);
  }
`

const RecommendationTags = styled.section`
  display: flex;
  flex-direction: column;
`
const Loading =styled.section`
  
  display: flex;
  justify-content: center;
  align-items: center;
`
