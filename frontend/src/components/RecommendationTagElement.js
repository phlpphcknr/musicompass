import styled from 'styled-components/macro'
import { Multiselect } from 'multiselect-react-dropdown'


export default function RecommendationTagElement (){



    const values = {
        options: [{name: 'Srigar', id: 1},{name: 'Sam', id: 2}]
    };


    return(
        <RecommendationTags>
            <h5>Gender:</h5>
            <Multiselect
                options={values.options} // Options to display in the dropdown
                //selectedValues={values.selectedValue} // Preselected value to persist in dropdown
                //onSelect={onSelect} // Function will trigger on select event
                //onRemove={onRemove} // Function will trigger on remove event
                displayValue="name" // Property name to display in the dropdown options
            />
            <h5>Role:</h5>
            <Multiselect
                options={values.options} // Options to display in the dropdown
                //selectedValues={values.selectedValue} // Preselected value to persist in dropdown
                //onSelect={onSelect} // Function will trigger on select event
                //onRemove={onRemove} // Function will trigger on remove event
                displayValue="name" // Property name to display in the dropdown options
            />
            <h5>Genre:</h5>
            <Multiselect
                options={values.options} // Options to display in the dropdown
                //selectedValues={values.selectedValue} // Preselected value to persist in dropdown
                //onSelect={onSelect} // Function will trigger on select event
                //onRemove={onRemove} // Function will trigger on remove event
                displayValue="name" // Property name to display in the dropdown options
            />
        </RecommendationTags>
    )

}

const RecommendationTags = styled.section`
  display: flex;
  flex-direction: column;
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
