import styled from 'styled-components/macro';
import { Multiselect } from 'multiselect-react-dropdown';
import {useEffect, useRef} from 'react';


export default function RecommendationTagElement ({recommendationTagObject, getRecommendation, setRecommendation}){

    const recommendationValueRef = useRef();

    const selectionLimit =
        recommendationTagObject.categoryName === "Gender" ? 1
        : recommendationTagObject.categoryName === "Roles" ? 2
        : 3;

    useEffect(() => {
        setRecommendation(recommendationValueRef.current.getSelectedItems)
    },[setRecommendation])

    const style = {

        multiSelectContainer: {

        },

        inputField: {
            //"min-width": "300px",
            "font-style": "Courier New"
        },

        placeholder: {
            "font-style": "Courier New"
        },
        chips: {
            background: "chocolate"
        },
        option: {
            color: "black",
            background: "moccasin",
            "font-size": "14px"
        },
        "#search_input.searchBox": {
            "font-style": "Courier New"
        }
    };

    return(
        <RecommendationTag>
            <Multiselect
                options = {recommendationTagObject.categoryValues}
                isObject = {false}
                ref = {recommendationValueRef}
                selectionLimit = {selectionLimit}
                selectedValues = {getRecommendation}
                onSelect = {setRecommendation}
                onRemove = {setRecommendation}
                closeIcon = "cancel"
                style = {style}
                displayValue="name"
                placeholder = {`select ${recommendationTagObject.categoryName}`}
                hidePlaceholder = "true"
            />
        </RecommendationTag>
    )
}

const RecommendationTag = styled.section`
  display: flex;
  align-items: center;
  font-family: "Courier New";

  #search_input.searchBox{
  "font-style": "Courier New"
  }
  
  
  
`
