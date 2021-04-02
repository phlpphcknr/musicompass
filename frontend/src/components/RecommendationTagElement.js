import styled from 'styled-components/macro';
import { Multiselect } from 'multiselect-react-dropdown';
import {useEffect, useState, useRef} from 'react';


export default function RecommendationTagElement ({recommendationTagObject, getRecommendation, setRecommendation}){

    const recommendationValueRef = useRef();

    const selectionLimit =
        recommendationTagObject.categoryName === "Gender" ? 1 :
        recommendationTagObject.categoryName === "Roles" ? 2 : 3;


    return(
        <RecommendationTag>
            <h5>{recommendationTagObject.categoryName}:</h5>
            <Multiselect
                options={recommendationTagObject.categoryValues} // Options to display in the dropdown
                isObject={false}
                ref={recommendationValueRef}
                selectionLimit = {selectionLimit}
                selectedValues={getRecommendation} // Preselected value to persist in dropdown
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
