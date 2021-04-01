import styled from 'styled-components/macro';
import { Multiselect } from 'multiselect-react-dropdown';
import {useEffect, useState, useRef} from 'react';
import {getRecommendationTagCategories} from "../service/musiComApiService";


export default function RecommendationTagElement ({recommendationTagObject}){

    const recommendationValueRef = useRef();

    const selectionLimit =
        recommendationTagObject.categoryName == "Gender" ? 1 :
        recommendationTagObject.categoryName == "Roles" ? 2 : 3


    return(
        <RecommendationTag>
            <h5>{recommendationTagObject.categoryName}:</h5>
            <Multiselect
                options={recommendationTagObject.categoryValues} // Options to display in the dropdown
                isObject={false}
                ref={recommendationValueRef}
                selectionLimit = {selectionLimit}
                //selectedValues={selectedValues} // Preselected value to persist in dropdown
                //onSelect={onSelect} // Function will trigger on select event
                //onRemove={onRemove} // Function will trigger on remove event
                displayValue="name" // Property name to display in the dropdown options
            />
        </RecommendationTag>
    )

}

const RecommendationTag = styled.section`
  display: flex;
  align-items: center;
  
  
  h5{
    width: 90px;
  }
`


/*
return(
    <RecommendationTags>
        <Category>
            <h5>Gender:</h5>
            <select>
                <option value="female">Female</option>
                <option value="Male">Male</option>
                <option value="female">Diverse</option>
            </select>
        </Category>
        <Category>
            <h5>Role:</h5>
            <select>
                <option value="female">Singer</option>
                <option value="Male">Guitarist</option>
                <option value="female">Pianist</option>
            </select>
        </Category>
        <Category>
            <h5>Genre:</h5>
            <select>
                <option value="female">Singer</option>
                <option value="Male">Guitarist</option>
                <option value="female">Pianist</option>
            </select>
        </Category>
    </RecommendationTags>
)

}

const RecommendationTags = styled.section`
  display: flex;
  flex-direction: column;
`

const Category = styled.section`
  display: flex;
  justify-content: space-between;
  margin: 5px;
`*/
