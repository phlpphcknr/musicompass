import styled from 'styled-components/macro';
import { Multiselect } from 'multiselect-react-dropdown';
import {useEffect, useRef} from 'react';


export default function RecommendationTagElement ({recommendationTagObject, getRecommendation, setRecommendation}){

    const recommendationValueRef = useRef();

    const selectionLimit =
        recommendationTagObject.categoryName === "Gender" ? 1 :
        recommendationTagObject.categoryName === "Roles" ? 2 : 3;

    useEffect(() => {
        setRecommendation(recommendationValueRef.current.getSelectedItems)
    },[setRecommendation])

    const style = {
        chips: {
            background: "chocolate"
        },
        option: {
            color: "black",
            background: "moccasin"
        }
    };

    return(
        <RecommendationTag>
            <p>{recommendationTagObject.categoryName}:</p>
            <Multiselect
                options={recommendationTagObject.categoryValues}
                isObject={false}
                ref={recommendationValueRef}
                selectionLimit = {selectionLimit}
                selectedValues={getRecommendation}
                closeIcon = "cancel"
                style={style}
                displayValue="name"
            />
        </RecommendationTag>
    )
}

const RecommendationTag = styled.section`
  display: flex;
  align-items: center;
  
  
  p{
    width: 90px;
  }
  
`
