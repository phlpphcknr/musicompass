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

        chips: {
            background: "var(--tertiary-color)",
            "borderRadius": "0px",
            "fontSize": "14px",
            "fontWeight": "bold",
            "color": "var(--secondary-color)"
        },

        searchBox:{
            "borderColor": "var(--tertiary-color)",
            "borderWidth": "thin",
            "borderRadius": "0px",
            margin: "2px"
        },

        option: {
            color: "var(--tertiary-color)",
            background: "var(--secondary-color)",
            "fontSize": "14px"
        }
    };

    return(
        <RecommendationTag>
            <p>{recommendationTagObject.categoryName}:</p>
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
                id="css_custom"
                displayValue="name"
                placeholder = {`set ${recommendationTagObject.categoryName}`}
                hidePlaceholder = "true"
            />
        </RecommendationTag>
    )
}

const RecommendationTag = styled.section`
  display: flex;
  align-items: center;
  
`
