import styled from 'styled-components/macro';
import GlobalStyle from "./GlobalStyle";
import { Multiselect } from 'multiselect-react-dropdown';
import {useEffect, useRef} from 'react';


export default function RecommendationTagElement ({recommendationTagObject, getRecommendation, setRecommendation}){

    const recommendationValueRef = useRef();

    const selectionLimit =
        recommendationTagObject.categoryName === "Gender" ? 1 :
        recommendationTagObject.categoryName === "Roles" ? 2 : 3;

    useEffect(() => {
        setRecommendation(recommendationValueRef.current.getSelectedItems)
    },[])

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
            <h5>{recommendationTagObject.categoryName}:</h5>
            <Multiselect
                options={recommendationTagObject.categoryValues} // Options to display in the dropdown
                isObject={false}
                ref={recommendationValueRef}
                selectionLimit = {selectionLimit}
                selectedValues={getRecommendation} // Preselected value to persist in dropdown
                closeIcon = "cancel"
                style={style}
                //id="css_custom"
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
