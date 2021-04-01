import styled from 'styled-components/macro'

export default function RecommendationTagElement (){

    return(
        <RecommendationTags>
            <Column>
                <h5>Gender:</h5>
                <h5>Role:</h5>
                <h5>Genre:</h5>
            </Column>
            <Column>
                <select>
                    <option value="female">Female</option>
                    <option value="Male">Male</option>
                    <option value="female">Diverse</option>
                </select>
                <select>
                    <option value="female">Singer</option>
                    <option value="Male">Guitarist</option>
                    <option value="female">Pianist</option>
                </select>
                <select>
                    <option value="female">Singer</option>
                    <option value="Male">Guitarist</option>
                    <option value="female">Pianist</option>
                </select>
            </Column>
        </RecommendationTags>
    )

}

const RecommendationTags = styled.section`
  display: flex;
`

const Column = styled.section`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin: 5px;
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
